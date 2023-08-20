package lionking.common.entity;

import lionking.common.LKMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LKEntityYellowDart extends LKEntityDart {
	public LKEntityYellowDart(World world) {
		super(world);
	}

	public LKEntityYellowDart(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public LKEntityYellowDart(World world, EntityLivingBase entityliving, float f, boolean flag) {
		super(world, entityliving, f, flag);
	}

	public ItemStack getDartItem() {
		return new ItemStack(LKMod.dartYellow);
	}

	public int getDamage() {
		return 8;
	}

	public void onHitEntity(Entity hitEntity) {
		if (shootingEntity != null) {
			hitEntity.addVelocity(-MathHelper.sin((shootingEntity.rotationYaw * 3.141593F) / 180.0F) * 0.45F, 0.10000000000000001D, MathHelper.cos((shootingEntity.rotationYaw * 3.141593F) / 180.0F) * 0.45F);
		}
	}

	public void spawnParticles() {
		worldObj.spawnParticle("smoke", posX, posY, posZ, -motionX * 0.1, -motionY * 0.1, -motionZ * 0.1);
	}

	public float getSpeedReduction() {
		return 0.02F;
	}
}
