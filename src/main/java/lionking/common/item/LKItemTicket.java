package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.block.LKBlockOutlandsPortal;
import lionking.common.block.LKBlockPortal;
import lionking.common.LKMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class LKItemTicket extends LKItem {
	@SideOnly(Side.CLIENT)
	private Icon crackerIcon;

	public LKItemTicket(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		return i == 1 ? crackerIcon : itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		super.registerIcons(iconregister);
		crackerIcon = iconregister.registerIcon("lionking:cracker");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (itemstack.getItemDamage() == 1) {
			itemstack.stackSize--;
			entityplayer.inventory.addItemStackToInventory(new ItemStack(LKMod.ticket));
			world.playSoundAtEntity(entityplayer, "lionking:pop", 1.0F, 1.0F);
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		if (itemstack.getItemDamage() == 0) {
			if (world.getBlockId(i, j, k) == LKMod.lionPortalFrame.blockID && world.isAirBlock(i, j + 1, k)) {
				if (entityplayer.dimension == 0 || entityplayer.dimension == LKMod.idPrideLands) {
					if (LKBlockPortal.tryToCreatePortal(world, i, j + 1, k)) {
						itemstack.stackSize--;
						return true;
					}
				}
			}
			if (world.getBlockId(i, j, k) == LKMod.outlandsPortalFrame.blockID && world.isAirBlock(i, j + 1, k)) {
				if (entityplayer.dimension == LKMod.idOutlands || entityplayer.dimension == LKMod.idPrideLands) {
					if (LKBlockOutlandsPortal.tryToCreatePortal(world, i, j + 1, k)) {
						itemstack.stackSize--;
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.uncommon;
	}
}
