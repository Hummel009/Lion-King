package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockBed extends BlockBed {
	@SideOnly(Side.CLIENT)
	private Icon[] bedEndIcons;
	@SideOnly(Side.CLIENT)
	private Icon[] bedSideIcons;
	@SideOnly(Side.CLIENT)
	private Icon[] bedTopIcons;

	public LKBlockBed(int i) {
		super(i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (i == 0) {
			return LKMod.prideWood.getIcon(2, 0);
		} else {
			int k = getDirection(j);
			int l = Direction.bedDirection[k][i];
			int i1 = isBlockHeadOfBed(j) ? 1 : 0;
			return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? bedTopIcons[i1] : bedSideIcons[i1]) : bedEndIcons[i1];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		bedTopIcons = new Icon[]{iconregister.registerIcon("lionking:bed_feet_top"), iconregister.registerIcon("lionking:bed_head_top")};
		bedEndIcons = new Icon[]{iconregister.registerIcon("lionking:bed_feet_end"), iconregister.registerIcon("lionking:bed_head_end")};
		bedSideIcons = new Icon[]{iconregister.registerIcon("lionking:bed_feet_side"), iconregister.registerIcon("lionking:bed_head_side")};
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return isBlockHeadOfBed(i) ? 0 : LKMod.bed.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return LKMod.bed.itemID;
	}

	@Override
	public boolean isBed(World world, int x, int y, int z, EntityLivingBase player) {
		return true;
	}
}
