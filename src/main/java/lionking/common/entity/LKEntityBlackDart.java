package lionking.common.entity;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LKEntityBlackDart extends LKEntityDart {
	public LKEntityBlackDart(World world) {
		super(world);
	}

	public LKEntityBlackDart(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public LKEntityBlackDart(World world, EntityLivingBase entityliving, float f, boolean flag) {
		super(world, entityliving, f, flag);
	}

	public ItemStack getDartItem() {
		return new ItemStack(LKMod.dartBlack);
	}

	public int getDamage() {
		return 7;
	}

	public void onHitEntity(Entity hitEntity) {
		if (hitEntity instanceof LKEntityTermite && shootingEntity != null && shootingEntity instanceof EntityPlayer) {
			((EntityPlayer) shootingEntity).triggerAchievement(LKAchievementList.termite);
		}
		if (!worldObj.isRemote) {
			worldObj.createExplosion(null, hitEntity.posX, hitEntity.posY, hitEntity.posZ, silverFired ? 4.0F : 3.0F, true);
		}
		setDead();
	}

	public void spawnParticles() {
		LKMod.proxy.spawnParticle("outlandsPortal", posX, posY - 0.8D, posZ, -motionX * 0.1, -motionY * 0.1, -motionZ * 0.1);
	}

	public float getSpeedReduction() {
		return 0.02F;
	}

	public void inGroundEvent() {
		if (!worldObj.isRemote) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			worldObj.createExplosion(null, i, j, k, silverFired ? 4.0F : 3.0F, true);
		}
		setDead();
	}
}
