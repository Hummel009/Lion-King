package lionking.common.item;

import lionking.common.LKMod;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class LKItemBanana extends LKItemFood {
	public LKItemBanana(int i, int j, float f, boolean flag) {
		super(i, j, f, flag);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ) {
		int id = world.getBlockId(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);

		if (id == LKMod.prideWood2.blockID && BlockLog.limitToValidMetadata(meta) == 0) {
			if (side == 0 || side == 1) {
				return false;
			}
			if (side == 2) {
				k--;
			}
			if (side == 3) {
				k++;
			}
			if (side == 4) {
				i--;
			}
			if (side == 5) {
				i++;
			}

			if (world.isAirBlock(i, j, k)) {
				int bananaMetadata = ForgeDirection.getOrientation(side - 2).getOpposite().ordinal();
				world.setBlock(i, j, k, LKMod.hangingBanana.blockID, bananaMetadata, 3);

				if (!entityplayer.capabilities.isCreativeMode) {
					itemstack.stackSize--;
				}
			}
			return true;
		}
		return false;
	}
}
