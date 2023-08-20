package lionking.common.block;

import lionking.common.LKCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.world.World;

public class LKBlockStairs extends BlockStairs {
	private Block theBlock;

	public LKBlockStairs(int i, Block block, int j) {
		super(i, block, j);
		theBlock = block;
		setCreativeTab(LKCreativeTabs.tabBlock);
	}

	@Override
	public float getBlockHardness(World world, int i, int j, int k) {
		return theBlock.getBlockHardness(world, i, j, k);
	}
}