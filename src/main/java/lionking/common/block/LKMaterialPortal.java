package lionking.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class LKMaterialPortal extends Material {
	public static Material material = new LKMaterialPortal().setImmovableMobility();

	private LKMaterialPortal() {
		super(MapColor.airColor);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean getCanBlockGrass() {
		return false;
	}

	@Override
	public boolean blocksMovement() {
		return true;
	}
}
