package lionking.common.entity;

import lionking.common.entity.ai.LKEntityAIAmbientAvoid;
import lionking.common.entity.ai.LKEntityAIAmbientPanic;
import lionking.common.entity.ai.LKEntityAIAmbientWander;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKEntityDikdik extends EntityAmbientCreature {
	public LKEntityDikdik(World world) {
		super(world);
		setSize(0.6F, 1.0F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LKEntityAIAmbientAvoid(this, LKEntityHyena.class, 8.0F, 1.0D, 1.5D));
		tasks.addTask(2, new LKEntityAIAmbientPanic(this, 1.5D));
		tasks.addTask(3, new LKEntityAIAmbientWander(this, 1.2D));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 4.0F));
		tasks.addTask(5, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(8.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, (byte) getRNG().nextInt(3));
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public byte getDikdikType() {
		return dataWatcher.getWatchableObjectByte(20);
	}

	public void setDikdikType(byte b) {
		dataWatcher.updateObject(20, b);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("DikdikType", getDikdikType());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setDikdikType(nbt.getByte("DikdikType"));
	}

	@Override
	public boolean getCanSpawnHere() {
		if (worldObj.provider.dimensionId != LKMod.idUpendi && getRNG().nextInt(3) > 0) {
			return false;
		}

		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return (worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID || worldObj.getBlockId(i, j - 1, k) == Block.sand.blockID) && worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LKMod.spawnEgg, 1, LKEntities.getEntityID(this));
	}
}
