package lionking.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class LKEntityGemsbokSpear extends LKEntitySpear {
	public LKEntityGemsbokSpear(World world) {
		super(world);
	}

	public LKEntityGemsbokSpear(World world, double d, double d1, double d2, int damage) {
		super(world, d, d1, d2, damage);
	}

	public LKEntityGemsbokSpear(World world, EntityLivingBase entityliving, float f, int damage) {
		super(world, entityliving, f, damage);
	}

	public boolean isPoisoned() {
		return false;
	}
}
