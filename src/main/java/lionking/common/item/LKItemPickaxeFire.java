package lionking.common.item;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class LKItemPickaxeFire extends LKItemPickaxe {
	public LKItemPickaxeFire(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			return false;
		}

		World world = entityplayer.worldObj;
		if (ForgeHooks.isToolEffective(itemstack, Block.blocksList[world.getBlockId(i, j, k)], world.getBlockMetadata(i, j, k))) {
			ItemStack smeltingResult = FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(world.getBlockId(i, j, k), 1, world.getBlockMetadata(i, j, k)));
			if (smeltingResult != null) {
				if (!world.isRemote) {
					entityplayer.addStat(StatList.mineBlockStatArray[world.getBlockId(i, j, k)], 1);
					entityplayer.addExhaustion(0.025F);
					for (int l = 0; l < Block.blocksList[world.getBlockId(i, j, k)].quantityDropped(itemRand); l++) {
						LKMod.dropItemsFromBlock(world, i, j, k, new ItemStack(smeltingResult.itemID, 1, smeltingResult.getItemDamage()));
					}
					world.playAuxSFX(2001, i, j, k, world.getBlockId(i, j, k) + (world.getBlockMetadata(i, j, k) << 12));
					itemstack.damageItem(1, entityplayer);
					world.setBlockToAir(i, j, k);
				}
				for (int l = 0; l < 6; l++) {
					double d = (i + itemRand.nextFloat());
					double d1 = (j + itemRand.nextFloat());
					double d2 = (k + itemRand.nextFloat());
					world.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
				}
				entityplayer.triggerAchievement(LKAchievementList.fireTool);
				return true;
			}
		}
		return false;
	}
}
