package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockOutlandsAltar extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] altarIcons;

	public LKBlockOutlandsAltar(int i) {
		super(i, Material.iron);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		setLightValue(0.75F);
		setCreativeTab(null);
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
		altarIcons[0] = iconregister.registerIcon("lionking:poolFocus_side");
		altarIcons[1] = iconregister.registerIcon("lionking:poolFocus_top");
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
		LKMod.proxy.spawnParticle("outlandsPortal", d, d1, d2, d3, d4, d5);
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
	public int getMobilityFlag() {
		return 2;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
		return true;
	}
}