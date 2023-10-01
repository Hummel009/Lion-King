package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKIngame;
import lionking.common.LKMod;
import lionking.common.entity.LKCharacterSpeech;
import lionking.common.entity.LKEntityRafiki;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

public class LKItemVase extends LKItem {
	@SideOnly(Side.CLIENT)
	private Icon[] vaseIcons;
	private String[] vaseTypes = {"flowerWhite", "flowerBlue", "flowerPurple", "flowerRed", "acacia", "rainforest", "mango", "outshroom", "outshroomGlowing", "passion", "banana"};

	public LKItemVase(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		if (i >= vaseTypes.length) {
			i = 0;
		}
		return vaseIcons[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		vaseIcons = new Icon[vaseTypes.length];
		for (int i = 0; i < vaseTypes.length; i++) {
			vaseIcons[i] = iconregister.registerIcon("lionking:vase_" + vaseTypes[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tab, List list) {
		for (int j = 0; j <= 10; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		int i1 = world.getBlockId(i, j, k);
		if (i1 == Block.snow.blockID) {
			l = 1;
		} else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID && (Block.blocksList[i1] != null && !Block.blocksList[i1].isBlockReplaceable(world, i, j, k))) {
			if (l == 0) {
				--j;
			}
			if (l == 1) {
				++j;
			}
			if (l == 2) {
				--k;
			}
			if (l == 3) {
				++k;
			}
			if (l == 4) {
				--i;
			}
			if (l == 5) {
				++i;
			}
		}
		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}
		Block block = LKMod.flowerVase;
		if (block.canPlaceBlockAt(world, i, j, k)) {
			if (!world.isRemote) {
				world.setBlock(i, j, k, LKMod.flowerVase.blockID, itemstack.getItemDamage(), 3);
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
				List list = world.getEntitiesWithinAABB(LKEntityRafiki.class, entityplayer.boundingBox.addCoord(16.0F, 5.0F, 16.0F).addCoord(-16.0F, -5.0F, -16.0F));
				if (!list.isEmpty() && itemstack.getItemDamage() < 4) {
					LKIngame.sendMessageToAllPlayers(LKCharacterSpeech.giveSpeech(LKCharacterSpeech.FLOWERS));
					for (Object obj : list) {
						((LKEntityRafiki) obj).addHeartFX();
					}
				}
				return true;
			}
		}
		return false;
	}
}