package lionking.common.entity;

import lionking.common.LKLevelData;
import lionking.common.entity.ai.LKEntityAIAngerableAttackableTarget;
import lionking.common.entity.ai.LKEntityAILionAttack;
import lionking.common.LKMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class LKEntityOutlander extends EntityMob implements LKAngerable {
	public int randomSoundDelay;
	public boolean inMound;
	protected int angerLevel;

	public LKEntityOutlander(World world) {
		super(world);
		angerLevel = 0;
		randomSoundDelay = 0;
		setSize(1.3F, 1.6F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LKEntityAILionAttack(this, EntityPlayer.class, 1.3D, false));
		tasks.addTask(2, new EntityAIWander(this, 1.0D));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new LKEntityAIAngerableAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(4.0D);
	}

	public boolean isHostile() {
		if (inMound) {
			return LKLevelData.outlandersHostile == 1 || angerLevel > 0;
		} else {
			return angerLevel > 0;
		}
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer) {
		return inMound ? 0 : super.getExperiencePoints(entityplayer);
	}

	@Override
	public void onLivingUpdate() {
		if (!worldObj.isRemote && LKLevelData.ziraStage == 19 && inMound) {
			setDead();
		}

		if (randomSoundDelay > 0 && --randomSoundDelay == 0) {
			worldObj.playSoundAtEntity(this, "lionking:lionangry", getSoundVolume() * 2.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		}
		if (!inMound && getRNG().nextInt(4000) == 0) {
			becomeAngry();
		}
		super.onLivingUpdate();
	}

	@Override
	public boolean getCanSpawnHere() {
		return worldObj.difficultySetting > 0 && worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setShort("Anger", (short) angerLevel);
		nbt.setBoolean("InMound", inMound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		angerLevel = nbt.getShort("Anger");
		inMound = nbt.getBoolean("InMound");
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (damagesource.getEntity() instanceof EntityPlayer) {
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(24.0D, 24.0D, 24.0D));
			for (Object o : list) {
				Entity entity = (Entity) o;
				if (!inMound && entity instanceof LKEntityOutlander) {
					LKEntityOutlander outlander = (LKEntityOutlander) entity;
					if (!outlander.inMound) {
						outlander.becomeAngry();
					}
				}
			}

			becomeAngry();
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	protected void attackEntity(Entity entity, float f) {
		if (attackTime <= 0 && f < 2.4F && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY) {
			attackTime = 20;
			attackEntityAsMob(entity);
		}
	}

	private void becomeAngry() {
		angerLevel = 400 + getRNG().nextInt(400);
		randomSoundDelay = getRNG().nextInt(40);
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		if (inMound) {
			return;
		}

		int j = getRNG().nextInt(2) + 1 + getRNG().nextInt(1 + i);
		for (int k = 0; k < j; k++) {
			if (getRNG().nextBoolean()) {
				dropItem(LKMod.outlanderFur.itemID, 1);
			}
		}
		j = getRNG().nextInt(2) + 1 + getRNG().nextInt(1 + i);
		for (int l = 0; l < j; l++) {
			if (getRNG().nextInt(5) < 2) {
				if (isBurning()) {
					dropItem(LKMod.lionCooked.itemID, 1);
				} else {
					dropItem(LKMod.outlanderMeat.itemID, 1);
				}
			}
		}
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (inMound) {
			if (LKLevelData.ziraStage < 14) {
				if (worldObj.getBlockId(i, j, k) == LKMod.outlandsPool.blockID) {
					return -999999.0F;
				} else if (worldObj.getBlockId(i, j - 1, k) == LKMod.outlandsPortalFrame.blockID && worldObj.getBlockMetadata(i, j - 1, k) == 1 && worldObj.isAirBlock(i, j, k)) {
					return 10.0F;
				} else {
					return -999999.0F;
				}
			}
			if (LKLevelData.ziraStage < 19) {
				return worldObj.getBlockId(i, j - 1, k) == LKMod.rafikiWood.blockID && worldObj.isAirBlock(i, j, k) ? 10.0F : -999999.0F;
			}
		}
		return super.getBlockPathWeight(i, j, k);
	}

	@Override
	protected boolean canDespawn() {
		return !inMound;
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
		return "lionking:lionangry";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LKMod.spawnEgg, 1, LKEntities.getEntityID(this));
	}
}
