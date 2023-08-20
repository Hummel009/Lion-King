package lionking.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class LKEntityPoisonedSpear extends LKEntitySpear {
	public LKEntityPoisonedSpear(World world) {
		super(world);
	}

	public LKEntityPoisonedSpear(World world, double d, double d1, double d2, int damage) {
		super(world, d, d1, d2, damage);
	}

	public LKEntityPoisonedSpear(World world, EntityLivingBase entityliving, float f, int damage) {
		super(world, entityliving, f, damage);
	}

	public boolean isPoisoned() {
		return true;
	}
}
