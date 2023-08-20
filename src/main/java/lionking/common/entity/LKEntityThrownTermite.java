package lionking.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKEntityThrownTermite extends EntityThrowable {
	public LKEntityThrownTermite(World world) {
		super(world);
	}

	public LKEntityThrownTermite(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	public LKEntityThrownTermite(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		if (movingobjectposition.entityHit != null) {
			movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
		}
		if (!worldObj.isRemote) {
			worldObj.createExplosion(this, posX, posY, posZ, 1.8F, true);
		}
		for (int i = 0; i < 8; i++) {
			worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}
		if (!worldObj.isRemote) {
			setDead();
		}
	}
}
