package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import lionking.common.quest.LKQuestBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class LKBlockPortalFrame extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon gateIcon;

	public LKBlockPortalFrame(int i) {
		super(i, Material.rock);
		setBlockUnbreakable();
		setResistance(6000000.0F);
	}

	@Override
	public int getMobilityFlag() {
		return 2;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int i, int j, int k) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (blockID == LKMod.outlandsPortalFrame.blockID) {
			if (j == 1) {
				return LKMod.termite.getIcon(0, 0);
			}
			if (j == 2) {
				return gateIcon;
			}
		}
		return super.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		super.registerIcons(iconregister);
		gateIcon = iconregister.registerIcon("lionking:ziraMoundGate");
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (LKQuestBase.outlandsQuest.canStart() && itemstack != null && itemstack.itemID == LKMod.rafikiStick.itemID && blockID == LKMod.outlandsPortalFrame.blockID && world.getBlockMetadata(i, j, k) == 2) {
			world.playAuxSFX(2001, i, j, k, world.getBlockId(i, j, k) + (world.getBlockMetadata(i, j, k) << 12));
			world.setBlockToAir(i, j, k);
			if (world.getBlockId(i - 1, j, k) == blockID && world.getBlockMetadata(i - 1, j, k) == 2) {
				onBlockActivated(world, i - 1, j, k, entityplayer, l, f, f1, f2);
			}
			if (world.getBlockId(i + 1, j, k) == blockID && world.getBlockMetadata(i + 1, j, k) == 2) {
				onBlockActivated(world, i + 1, j, k, entityplayer, l, f, f1, f2);
			}
			if (world.getBlockId(i, j - 1, k) == blockID && world.getBlockMetadata(i, j - 1, k) == 2) {
				onBlockActivated(world, i, j - 1, k, entityplayer, l, f, f1, f2);
			}
			if (world.getBlockId(i, j + 1, k) == blockID && world.getBlockMetadata(i, j + 1, k) == 2) {
				onBlockActivated(world, i, j + 1, k, entityplayer, l, f, f1, f2);
			}
			if (world.getBlockId(i, j, k - 1) == blockID && world.getBlockMetadata(i, j, k - 1) == 2) {
				onBlockActivated(world, i, j, k - 1, entityplayer, l, f, f1, f2);
			}
			if (world.getBlockId(i, j, k + 1) == blockID && world.getBlockMetadata(i, j, k + 1) == 2) {
				onBlockActivated(world, i, j, k + 1, entityplayer, l, f, f1, f2);
			}
			LKQuestBase.outlandsQuest.setDelayed(true);
			LKQuestBase.outlandsQuest.progress(1);
			return true;
		}
		return false;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}
}
