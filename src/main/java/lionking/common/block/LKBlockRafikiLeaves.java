package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKIngame;
import lionking.common.LKLevelData;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockRafikiLeaves extends BlockLeavesBase {
	private int leafMode;
	private Random textureRand = new Random();
	@SideOnly(Side.CLIENT)
	private Icon[] leafIcons;
	@SideOnly(Side.CLIENT)
	private Icon[] corruptLeafIcons;
	@SideOnly(Side.CLIENT)
	private Icon[][] christmasLeafIcons;
	private String[] leafModes = {"fast", "fancy"};

	public LKBlockRafikiLeaves(int i) {
		super(i, Material.leaves, false);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	public boolean isOpaqueCube() {
		return !graphicsLevel;
	}

	public void setGraphicsLevel(boolean flag) {
		graphicsLevel = flag;
		leafMode = flag ? 1 : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		leafIcons = new Icon[2];
		corruptLeafIcons = new Icon[2];
		christmasLeafIcons = new Icon[2][4];
		for (int i = 0; i < 2; i++) {
			leafIcons[i] = iconregister.registerIcon("lionking:rafikiLeaves_" + leafModes[i]);
			corruptLeafIcons[i] = iconregister.registerIcon("lionking:rafikiLeaves_" + leafModes[i] + "_corrupt");
			for (int j = 0; j < 4; j++) {
				christmasLeafIcons[i][j] = iconregister.registerIcon("lionking:rafikiLeaves_" + leafModes[i] + "_christmas_" + j);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (LKLevelData.ziraStage >= 14 && LKLevelData.ziraStage < 19) {
			return corruptLeafIcons[leafMode];
		}
		return leafIcons[leafMode];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int i, int j, int k, int side) {
		if (LKLevelData.ziraStage >= 14 && LKLevelData.ziraStage < 19) {
			return corruptLeafIcons[leafMode];
		}
		if (LKIngame.isChristmas() && side > 1) {
			textureRand.setSeed((i * 3129871L) ^ k * 116129781L ^ j ^ side);
			int l = textureRand.nextInt(10);
			if (l < 4) {
				return christmasLeafIcons[leafMode][l];
			} else {
				return leafIcons[leafMode];
			}
		}
		return leafIcons[leafMode];
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		if (LKIngame.isChristmas()) {
			return 13;
		}
		return super.getLightValue(world, i, j, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.canLightningStrikeAt(i, j + 1, k) && !world.doesBlockHaveSolidTopSurface(i, j - 1, k) && random.nextInt(15) == 1) {
			double d = (i + random.nextFloat());
			double d1 = j - 0.05D;
			double d2 = (k + random.nextFloat());
			world.spawnParticle("dripWater", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}

		if (LKIngame.isChristmas() && random.nextInt(4) == 0) {
			double d = (i - 0.5F + (2.0F * random.nextFloat()));
			double d1 = j + 0.5D;
			double d2 = (k - 0.5F + (2.0F * random.nextFloat()));
			world.spawnParticle("snowshovel", d, d1, d2, 0.0D, -0.05D, 0.0D);
		}
	}

	@Override
	public int getMobilityFlag() {
		return 2;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int i, int j, int k) {
		return false;
	}
}
