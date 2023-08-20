package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockKiwano extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] kiwanoIcons;

	public LKBlockKiwano(int i) {
		super(i, Material.pumpkin);
		setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.8125F, 0.875F);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (j == -1) {
			return kiwanoIcons[1];
		}
		return kiwanoIcons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		kiwanoIcons = new Icon[3];
		kiwanoIcons[0] = iconregister.registerIcon("lionking:kiwano");
		kiwanoIcons[1] = iconregister.registerIcon("lionking:kiwano_spikes");
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return LKMod.kiwano.itemID;
	}

	@Override
	public int quantityDropped(Random random) {
		return 2 + random.nextInt(5);
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		int j = quantityDropped(random) + random.nextInt(1 + i);

		if (j > 9) {
			j = 9;
		}

		return j;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if (!world.doesBlockHaveSolidTopSurface(i, j - 1, k)) {
			world.setBlockToAir(i, j, k);
			dropBlockAsItem_do(world, i, j, k, new ItemStack(this));
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return world.doesBlockHaveSolidTopSurface(i, j - 1, k) && super.canPlaceBlockAt(world, i, j, k);
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
		return LKMod.proxy.getKiwanoBlockRenderID();
	}
}
