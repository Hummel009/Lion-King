package lionking.common.entity;

import lionking.common.LKMod;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKEntitySkeletalHyenaHead extends EntityLiving implements IMob {
	private float field_70813_a;
	private float field_70811_b;
	private int headJumpDelay;

	public LKEntitySkeletalHyenaHead(World world) {
		super(world);
		setSize(0.6F, 0.6F);
		experienceValue = 2;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(15.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(1.2D);
	}

	@Override
	public void onUpdate() {
		if (!worldObj.isRemote && worldObj.difficultySetting == 0) {
			setDead();
		}

		field_70811_b += (field_70813_a - field_70811_b) * 0.5F;
		float field_70812_c = field_70811_b;
		boolean flag = onGround;
		super.onUpdate();

		if (onGround && !flag) {
			worldObj.playSoundAtEntity(this, getHurtSound(), 0.4F, ((getRNG().nextFloat() - getRNG().nextFloat()) * 0.2F + 1.0F) / 0.8F);

			field_70813_a = -0.5F;
		} else if (!onGround && flag) {
			field_70813_a = 1.0F;
		}

		field_70813_a *= 0.6F;
	}

	@Override
	protected void updateEntityActionState() {
		despawnEntity();
		EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);

		if (entityplayer != null) {
			faceEntity(entityplayer, 10.0F, 20.0F);
		}

		if (onGround && headJumpDelay-- <= 0) {
			headJumpDelay = getRNG().nextInt(20) + 10;

			if (entityplayer != null) {
				headJumpDelay /= 3;
			}

			isJumping = true;

			worldObj.playSoundAtEntity(this, getHurtSound(), 0.4F, ((getRNG().nextFloat() - getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);

			moveStrafing = 1.0F - getRNG().nextFloat() * 2.0F;
			moveForward = 1.0F;
		} else {
			isJumping = false;

			if (onGround) {
				moveStrafing = moveForward = 0.0F;
			}
		}
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		if (canEntityBeSeen(entityplayer) && getDistanceSqToEntity(entityplayer) < 1.0D && entityplayer.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F)) {
			worldObj.playSoundAtEntity(this, "mob.attack", 1.0F, (getRNG().nextFloat() - getRNG().nextFloat()) * 0.2F + 1.0F);
		}
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && getRNG().nextInt(40) == 0) {
			entityDropItem(new ItemStack(LKMod.hyenaHeadItem.itemID, 1, 3), 0.0F);
		}
	}

	@Override
	protected String getHurtSound() {
		return "mob.skeleton.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.skeleton.death";
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LKMod.spawnEgg, 1, LKEntities.getEntityID(this));
	}
}
