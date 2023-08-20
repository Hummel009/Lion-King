package lionking.client.effect;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import java.awt.*;

public class LKEntityPassionFX extends EntityFX {
	public LKEntityPassionFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, 0, 0, 0);
		int i = Color.HSBtoRGB(world.rand.nextFloat(), 1.0F, 1.0F);
		Color c = Color.decode(Integer.valueOf(String.valueOf(i), 16).toString());
		particleRed = c.getRed() / 255.0F;
		particleGreen = c.getGreen() / 255.0F;
		particleBlue = c.getBlue() / 255.0F;
		particleMaxAge = 32;
		motionX = d3;
		motionY = d4;
		motionZ = d5;
	}

	@Override
	public int getBrightnessForRender(float f) {
		float f1 = (particleAge + f) / particleMaxAge;

		if (f1 < 0.0F) {
		}

		int i = super.getBrightnessForRender(f);
		int j = i & 255;
		int k = i >> 16 & 255;

		return k | k << 16;
	}

	@Override
	public float getBrightness(float f) {
		float f1 = (particleAge + f) / particleMaxAge;

		if (f1 < 0.0F) {
			f1 = 0.0F;
		}

		if (f1 > 1.0F) {
			f1 = 1.0F;
		}

		float f2 = super.getBrightness(f);
		return f2 * f1 + (1.0F - f1);
	}
}
