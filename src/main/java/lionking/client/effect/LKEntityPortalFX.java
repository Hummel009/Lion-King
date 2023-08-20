package lionking.client.effect;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

public class LKEntityPortalFX extends EntityPortalFX {
	public LKEntityPortalFX(World world, double d, double d1, double d2, double d3, double d4, double d5, boolean isPrideLands) {
		super(world, d, d1, d2, d3, d4, d5);
		float f = world.rand.nextFloat() * 0.6F + 0.4F;
		particleRed = particleGreen = particleBlue = f;

		if (isPrideLands) {
			particleRed *= 1.0F;
			particleGreen *= 0.7F;
			particleBlue *= 0.1F;
		} else {
			particleRed *= 1.0F;
			particleGreen *= 0.2F;
			particleBlue *= 0.0F;
		}
	}
}
