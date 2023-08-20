package lionking.common.entity;

import lionking.common.LKMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKEntityBlueDart extends LKEntityDart {
	public LKEntityBlueDart(World world) {
		super(world);
	}

	public LKEntityBlueDart(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public LKEntityBlueDart(World world, EntityLivingBase entityliving, float f, boolean flag) {
		super(world, entityliving, f, flag);
	}

	public ItemStack getDartItem() {
		return new ItemStack(LKMod.dartBlue);
	}

	public int getDamage() {
		return 7;
	}

	public void onHitEntity(Entity hitEntity) {
	}

	public void spawnParticles() {
	}

	public float getSpeedReduction() {
		return 0.05F;
	}
}
