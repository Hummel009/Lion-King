package lionking.common.entity;

import lionking.common.entity.ai.LKEntityAIAngerableAttackableTarget;
import lionking.common.entity.ai.LKEntityAIAngerableMate;
import lionking.common.entity.ai.LKEntityAIAngerablePanic;
import lionking.common.entity.ai.LKEntityAILionAttack;
import lionking.common.LKMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public abstract class LKEntityLionBase extends LKEntityQuestAnimal implements LKAngerable {
	private int hostileTick;

	protected LKEntityLionBase(World world) {
		super(world);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LKEntityAILionAttack(this, EntityPlayer.class, 1.5D, false));
		tasks.addTask(2, new LKEntityAIAngerablePanic(this, 1.5D));
		tasks.addTask(3, new LKEntityAIAngerableMate(this, 1.0D));
		tasks.addTask(4, new EntityAITempt(this, 1.4D, LKMod.hyenaBone.itemID, false));
		tasks.addTask(5, new EntityAIFollowParent(this, 1.4D));
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new LKEntityAIAngerableAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(18.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.2F);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isHostile() {
		return hostileTick > 0;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer) {
		return 2 + worldObj.rand.nextInt(3);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (hostileTick > 0 && getAttackTarget() == null) {
			hostileTick--;
		}
	}

	@Override
	protected int getDropItemId() {
		if (isBurning()) {
			return LKMod.lionCooked.itemID;
		} else {
			return LKMod.lionRaw.itemID;
		}
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		int j = getRNG().nextInt(2) + 1 + getRNG().nextInt(1 + i);
		for (int k = 0; k < j; k++) {
			if (getRNG().nextInt(4) != 0) {
				dropItem(LKMod.fur.itemID, 1);
			}
		}
		j = getRNG().nextInt(2) + 1 + getRNG().nextInt(1 + i);
		for (int l = 0; l < j; l++) {
			if (isBurning()) {
				dropItem(LKMod.lionCooked.itemID, 1);
			} else {
				dropItem(LKMod.lionRaw.itemID, 1);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		int i = 3;

		if (isPotionActive(Potion.damageBoost)) {
			i += 3 << getActivePotionEffect(Potion.damageBoost).getAmplifier();
		}

		if (isPotionActive(Potion.weakness)) {
			i -= 2 << getActivePotionEffect(Potion.weakness).getAmplifier();
		}

		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return getRNG().nextBoolean() ? new LKEntityLion(worldObj) : new LKEntityLioness(worldObj);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity attacker = damagesource.getEntity();
		if (isChild()) {
			fleeingTick = 60;
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12.0D, 12.0D, 12.0D));
			for (Object o : list) {
				Entity entity = (Entity) o;
				if (entity instanceof LKEntityLionBase) {
					LKEntityLionBase lion = (LKEntityLionBase) entity;
					if (!lion.isChild() && attacker instanceof EntityLivingBase) {
						lion.becomeAngryAt((EntityLivingBase) attacker);
					}
				}
			}
		}
		if (attacker instanceof EntityLivingBase && !isChild()) {
			becomeAngryAt((EntityLivingBase) attacker);
		}
		return super.attackEntityFrom(damagesource, f);
	}

	private void becomeAngryAt(EntityLivingBase entity) {
		setAttackTarget(entity);
		hostileTick = 6000;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Angry", hostileTick);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		hostileTick = nbt.getInteger("Angry");
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack.itemID == LKMod.hyenaBone.itemID;
	}

	@Override
	public boolean canMateWith(EntityAnimal mate) {
		if (mate == this || !isInLove() || !mate.isInLove()) {
			return false;
		}
		if (this instanceof LKEntityLion) {
			return mate instanceof LKEntityLioness;
		} else {
			return mate instanceof LKEntityLion;
		}
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.itemID == LKMod.jarMilk.itemID && !isChild() && isHostile()) {
			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(LKMod.jar));
			hostileTick = 0;
			for (int i = 0; i < 7; i++) {
				double d = getRNG().nextGaussian() * 0.02D;
				double d1 = getRNG().nextGaussian() * 0.02D;
				double d2 = getRNG().nextGaussian() * 0.02D;
				worldObj.spawnParticle("smoke", (posX + (getRNG().nextFloat() * width * 2.0F)) - width, posY + 0.5D + (getRNG().nextFloat() * height), (posZ + (getRNG().nextFloat() * width * 2.0F)) - width, d, d1, d2);
			}
			return true;
		} else if (isHostile()) {
			return false;
		}
		return super.interact(entityplayer);
	}

	@Override
	protected String getLivingSound() {
		return "lionking:lion";
	}

	@Override
	protected String getHurtSound() {
		return "lionking:lionroar";
	}

	@Override
	protected String getDeathSound() {
		return "lionking:liondeath";
	}

	public ItemStack getQuestItem() {
		int i = getRNG().nextInt(5);
		switch (i) {
			case 1:
				return new ItemStack(Item.fishRaw, 3 + getRNG().nextInt(6));
			case 2:
				return new ItemStack(LKMod.hyenaBoneShard, 6 + getRNG().nextInt(9));
			case 3:
				return new ItemStack(LKMod.crocodileMeat, 3 + getRNG().nextInt(11));
			case 4:
				return new ItemStack(LKMod.hyenaHeadItem, 1, getRNG().nextInt(2));
			default:
				return new ItemStack(LKMod.zebraRaw, 5 + getRNG().nextInt(7));
		}
	}
}
