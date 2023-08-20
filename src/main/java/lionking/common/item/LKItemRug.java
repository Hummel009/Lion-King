package lionking.common.item;

import lionking.common.tileentity.LKTileEntityFurRug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class LKItemRug extends LKItemMetadata {
	public LKItemRug(int i) {
		super(i);
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ, int metadata) {
		boolean flag = super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, hitX, hitY, hitZ, metadata);
		if (flag && world.getBlockId(i, j, k) == getBlockID()) {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (tileentity instanceof LKTileEntityFurRug) {
				ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
				((LKTileEntityFurRug) world.getBlockTileEntity(i, j, k)).direction = (byte) dir.ordinal();
			}
		}
		return flag;
	}
}
