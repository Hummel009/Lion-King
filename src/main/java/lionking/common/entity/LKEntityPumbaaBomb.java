package lionking.common.entity;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class LKEntityPumbaaBomb extends EntityThrowable {
	public LKEntityPumbaaBomb(World world) {
		super(world);
	}

	public LKEntityPumbaaBomb(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	public LKEntityPumbaaBomb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		if (movingobjectposition.entityHit != null) {
			movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
		}
		if (!worldObj.isRemote) {
			explode();
		}

		worldObj.playSoundEffect(posX, posY, posZ, "lionking:flatulence", 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		worldObj.spawnParticle("hugeexplosion", posX, posY, posZ, 0.0D, 0.0D, 0.0D);

		if (!worldObj.isRemote) {
			setDead();
		}
	}

	private void explode() {
		float explosionSize = 14.0F + (worldObj.rand.nextFloat() * 5.0F);
		int l = MathHelper.floor_double(posX - explosionSize - 1.0D);
		int i1 = MathHelper.floor_double(posX + explosionSize + 1.0D);
		int k1 = MathHelper.floor_double(posY - explosionSize - 1.0D);
		int l1 = MathHelper.floor_double(posY + explosionSize + 1.0D);
		int i2 = MathHelper.floor_double(posZ - explosionSize - 1.0D);
		int j2 = MathHelper.floor_double(posZ + explosionSize + 1.0D);
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(l, k1, i2, i1, l1, j2));
		Vec3 vec = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
		for (Object o : list) {
			Entity entity = (Entity) o;
			double d4 = entity.getDistance(posX, posY, posZ) / explosionSize;
			if (d4 <= 1.0D) {
				double d6 = entity.posX - posX;
				double d8 = entity.posY - posY;
				double d10 = entity.posZ - posZ;
				double d11 = MathHelper.sqrt_double(d6 * d6 + d8 * d8 + d10 * d10);
				d6 /= d11;
				d8 /= d11;
				d10 /= d11;
				double d12 = worldObj.getBlockDensity(vec, entity.boundingBox);
				double d13 = (1.0D - d4) * d12;
				if (entity instanceof EntityLivingBase && !(entity instanceof LKCharacter) && !(entity instanceof EntityPlayer)) {
					int damage = 17 + worldObj.rand.nextInt(6);
					entity.attackEntityFrom(DamageSource.setExplosionSource(null), damage);
					if (entity instanceof IMob && getThrower() != null && getThrower() instanceof EntityPlayer) {
						((EntityPlayer) getThrower()).triggerAchievement(LKAchievementList.fartBomb);
					}
				}
				entity.motionX += d6 * d13;
				entity.motionY += d8 * d13;
				entity.motionZ += d10 * d13;
			}
		}

		for (int i = l; i <= i1; i++) {
			for (int j = k1; j <= l1; j++) {
				for (int k = i2; k <= j2; k++) {
					ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
					if (worldObj.rand.nextInt(3) > 0 && Math.sqrt(coords.getDistanceSquared(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) / (explosionSize * 0.7F) <= 1.0D) {
						int id = worldObj.getBlockId(i, j, k);
						if (id > 0) {
							int metadata = worldObj.getBlockMetadata(i, j, k);
							if (id == LKMod.prideBrick.blockID && metadata == 0) {
								worldObj.setBlock(i, j, k, LKMod.prideBrickMossy.blockID, 1, 3);
							} else if (id == Block.grass.blockID) {
								worldObj.setBlock(i, j, k, Block.dirt.blockID, 0, 3);
							} else if (Block.blocksList[id] instanceof BlockFlower || Block.blocksList[id].blockMaterial == Material.plants) {
								Block.blocksList[id].dropBlockAsItemWithChance(worldObj, i, j, k, metadata, 0.4F, 0);
								worldObj.setBlockToAir(i, j, k);
							}
						}
					}
				}
			}
		}
	}
}
