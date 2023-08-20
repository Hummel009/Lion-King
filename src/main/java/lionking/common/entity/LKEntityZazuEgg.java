package lionking.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKEntityZazuEgg extends EntityThrowable {
	public LKEntityZazuEgg(World world) {
		super(world);
	}

	public LKEntityZazuEgg(World world, EntityLivingBase entityliiving) {
		super(world, entityliiving);
	}

	public LKEntityZazuEgg(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@Override
	protected void onImpact(MovingObjectPosition object) {
		if (object.entityHit != null) {
			object.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
		}

		if (!worldObj.isRemote && worldObj.rand.nextBoolean()) {
			LKEntityZazu zazu = new LKEntityZazu(worldObj);
			zazu.setGrowingAge(-24000);
			zazu.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(zazu);
		}

		for (int i = 0; i < 8; ++i) {
			worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!worldObj.isRemote) {
			setDead();
		}
	}
}
