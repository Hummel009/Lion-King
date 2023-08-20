package lionking.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class LKEntityLightning extends EntityWeatherEffect {
	public long boltVertex;
	private int lightningState;
	private int boltLivingTime;
	private int power;
	private EntityPlayer thePlayer;

	public LKEntityLightning(EntityPlayer player, World world, double d, double d1, double d2, int level) {
		super(world);
		thePlayer = player;
		power = level;
		setLocationAndAngles(d, d1, d2, 0.0F, 0.0F);
		lightningState = 2;
		boltVertex = world.rand.nextLong();
		boltLivingTime = world.rand.nextInt(3) + 1;
		if (power > 0) {
			if (world.doChunksNearChunkExist(MathHelper.floor_double(d), MathHelper.floor_double(d1), MathHelper.floor_double(d2), 10)) {
				int var8 = MathHelper.floor_double(d);
				int var9 = MathHelper.floor_double(d1);
				int var10 = MathHelper.floor_double(d2);
				if (world.getBlockId(var8, var9, var10) == 0 && Block.fire.canPlaceBlockAt(world, var8, var9, var10)) {
					world.setBlock(var8, var9, var10, Block.fire.blockID, 0, 3);
				}
				for (var8 = 0; var8 < power * 3; ++var8) {
					var9 = MathHelper.floor_double(d) + world.rand.nextInt(3) - 1;
					var10 = MathHelper.floor_double(d1) + world.rand.nextInt(3) - 1;
					int var11 = MathHelper.floor_double(d2) + world.rand.nextInt(3) - 1;
					if (world.getBlockId(var9, var10, var11) == 0 && Block.fire.canPlaceBlockAt(world, var9, var10, var11)) {
						world.setBlock(var9, var10, var11, Block.fire.blockID, 0, 3);
					}
				}
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (lightningState == 2) {
			worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
			worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 2.0F, 0.5F + rand.nextFloat() * 0.2F);
		}

		--lightningState;

		if (lightningState < 0) {
			if (boltLivingTime == 0) {
				setDead();
			} else if (lightningState < -rand.nextInt(10)) {
				--boltLivingTime;
				lightningState = 1;
				boltVertex = rand.nextLong();
			}
		}

		if (lightningState >= 0) {
			if (power > 0) {
				double var6 = 3.0D;
				List var7 = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(posX - var6, posY - var6, posZ - var6, posX + var6, posY + 6.0D + var6, posZ + var6));
				for (Object o : var7) {
					Entity var5 = (Entity) o;
					if ((thePlayer != null && var5 == thePlayer) || var5 instanceof LKCharacter || var5.isImmuneToFire()) {
						continue;
					}
					var5.attackEntityFrom(thePlayer != null ? DamageSource.causePlayerDamage(thePlayer) : DamageSource.inFire, (power * 3) + (rand.nextInt(3) * 2));
					var5.setFire(power + rand.nextInt(4));
				}
			}
			worldObj.lastLightningBolt = 2;
		}
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
	}

	@Override
	public boolean isInRangeToRenderVec3D(Vec3 par1Vec3) {
		return lightningState >= 0;
	}
}
