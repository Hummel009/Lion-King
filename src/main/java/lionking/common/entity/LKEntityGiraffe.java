package lionking.common.entity;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKEntityGiraffe extends EntityAnimal {
	public LKEntityGiraffe(World world) {
		super(world);
		setSize(1.7F, 2.8F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.7D));
		tasks.addTask(2, new EntityAIControlledByPlayer(this, 0.8F));
		tasks.addTask(3, new EntityAIMate(this, 1.0D));
		tasks.addTask(4, new EntityAITempt(this, 1.4D, LKMod.leaves.blockID, false));
		tasks.addTask(5, new EntityAIFollowParent(this, 1.4D));
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(18.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.2D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, (byte) -1);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Saddled", getSaddled());
		nbt.setByte("Tie", (byte) getTie());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSaddled(nbt.getBoolean("Saddled"));
		setTie(nbt.getByte("Tie"));
	}

	@Override
	public boolean canBeSteered() {
		return true;
	}

	@Override
	public double getMountedYOffset() {
		return height * 0.93D;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack.itemID == LKMod.leaves.blockID;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return new LKEntityGiraffe(worldObj);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (damagesource.getEntity() == riddenByEntity) {
			return false;
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (super.interact(entityplayer)) {
			return true;
		} else if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().itemID == LKMod.tie.itemID && getSaddled() && getTie() == -1) {
			return false;
		} else if (getSaddled() && !worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer)) {
			entityplayer.mountEntity(this);
			if (riddenByEntity == entityplayer) {
				entityplayer.triggerAchievement(LKAchievementList.rideGiraffe);
			}
			return true;
		} else {
			return false;
		}
	}

	public int getTie() {
		return dataWatcher.getWatchableObjectByte(17);
	}

	public void setTie(int i) {
		dataWatcher.updateObject(17, (byte) i);
	}

	public boolean getSaddled() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setSaddled(boolean flag) {
		if (flag) {
			dataWatcher.updateObject(16, (byte) 1);
		} else {
			dataWatcher.updateObject(16, (byte) 0);
		}
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote) {
			if (getSaddled()) {
				dropItem(LKMod.giraffeSaddle.itemID, 1);
			}
			if (getTie() > -1) {
				entityDropItem(new ItemStack(LKMod.tie, 1, getTie()), 0.0F);
			}
		}
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LKMod.spawnEgg, 1, LKEntities.getEntityID(this));
	}
}
