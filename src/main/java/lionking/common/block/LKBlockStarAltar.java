package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockStarAltar extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] altarIcons;

	public LKBlockStarAltar(int i) {
		super(i, Material.iron);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		setLightValue(0.5F);
		setTickRandomly(true);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		return i == 1 ? altarIcons[1] : altarIcons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		altarIcons = new Icon[2];
		altarIcons[0] = iconregister.registerIcon("lionking:starAltar_side");
		altarIcons[1] = iconregister.registerIcon("lionking:starAltar_top");
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return canBlockStay(world, i, j, k) && super.canPlaceBlockAt(world, i, j, k);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		checkAltarChange(world, i, j, k);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		checkAltarChange(world, i, j, k);
	}

	protected final void checkAltarChange(World world, int i, int j, int k) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return (world.isBlockNormalCube(i, j - 1, k) || world.doesBlockHaveSolidTopSurface(i, j - 1, k)) && world.canBlockSeeTheSky(i, j + 1, k) && isRoom(world, i, j, k) && world.provider.dimensionId == LKMod.idPrideLands;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return LKMod.proxy.getStarAltarRenderID();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		double d = i + random.nextFloat();
		double d1 = j + random.nextFloat();
		double d2 = k + random.nextFloat();
		double d3;
		double d4;
		double d5;
		int i1 = random.nextInt(2) * 2 - 1;
		d3 = (random.nextFloat() - 0.5D) * 0.5D;
		d4 = (random.nextFloat() - 0.5D) * 0.5D;
		d5 = (random.nextFloat() - 0.5D) * 0.5D;
		d = i + 0.5D + 0.25D * i1;
		d3 = random.nextFloat() * 2.0F * i1;
		world.spawnParticle("portal", d, d1, d2, d3, d4, d5);
	}

	private boolean isRoom(IBlockAccess world, int i, int j, int k) {
		for (int i1 = i - 1; i1 <= i + 1; i1++) {
			for (int j1 = j + 1; j1 <= j + 2; j1++) {
				for (int k1 = k - 1; k1 <= k + 1; k1++) {
					if (!world.isAirBlock(i1, j1, k1)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}
}
