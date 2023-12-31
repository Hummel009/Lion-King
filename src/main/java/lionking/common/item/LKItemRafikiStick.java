package lionking.common.item;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKIngame;
import lionking.common.LKMod;
import lionking.common.block.LKBlockAridGrass;
import lionking.common.block.LKBlockLeaves;
import lionking.common.block.LKBlockSapling;
import lionking.common.entity.LKEntityLightning;
import lionking.common.entity.LKEntityScar;
import lionking.common.world.biome.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.IShearable;

public class LKItemRafikiStick extends LKItem {
	private int lastThunderUseTick;
	@SideOnly(Side.CLIENT)
	private Icon[] thunderIcons;

	public LKItemRafikiStick(int i) {
		super(i);
		setMaxStackSize(1);
		setMaxDamage(850);
		setNoRepair();
		lastThunderUseTick = 0;
		setCreativeTab(LKCreativeTabs.tabQuest);
		setFull3D();
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		if (world.isRemote || !LKIngame.isLKWorld(world.provider.dimensionId) || !entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}

		int i1 = world.getBlockId(i, j, k);
		if (i1 == LKMod.sapling.blockID) {
			damageRafikiStick(itemstack, 4, entityplayer);
			((LKBlockSapling) LKMod.sapling).growTree(world, i, j, k, world.rand);
			return true;
		}

		if (i1 == LKMod.forestSapling.blockID) {
			damageRafikiStick(itemstack, 4, entityplayer);
			((LKBlockSapling) LKMod.forestSapling).growTree(world, i, j, k, world.rand);
			return true;
		}

		if (i1 == LKMod.mangoSapling.blockID) {
			damageRafikiStick(itemstack, 8, entityplayer);
			((LKBlockSapling) LKMod.mangoSapling).growTree(world, i, j, k, world.rand);
			return true;
		}

		if (i1 == LKMod.passionSapling.blockID) {
			damageRafikiStick(itemstack, 10, entityplayer);
			((LKBlockSapling) LKMod.passionSapling).growTree(world, i, j, k, world.rand);
			return true;
		}

		if (i1 == LKMod.bananaSapling.blockID) {
			damageRafikiStick(itemstack, 8, entityplayer);
			((LKBlockSapling) LKMod.bananaSapling).growTree(world, i, j, k, world.rand);
			return true;
		}

		if ((i1 == Block.crops.blockID || i1 == LKMod.yamCrops.blockID) && world.getBlockMetadata(i, j, k) < 7) {
			world.setBlockMetadataWithNotify(i, j, k, 7, 3);
			damageRafikiStick(itemstack, 6, entityplayer);
			return true;
		}

		if (i1 == LKMod.kiwanoStem.blockID && world.getBlockMetadata(i, j, k) < 7) {
			world.setBlockMetadataWithNotify(i, j, k, 7, 3);
			damageRafikiStick(itemstack, 6, entityplayer);
			return true;
		}

		if (i1 == Block.grass.blockID) {
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
			world.notifyBlockChange(i, j, k, world.getBlockId(i, j, k));
			label0:
			for (int l1 = 0; l1 < 128; ++l1) {
				int i2 = i;
				int j2 = j + 1;
				int k2 = k;
				for (int l2 = 0; l2 < l1 / 16; ++l2) {
					i2 += itemRand.nextInt(3) - 1;
					j2 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
					k2 += itemRand.nextInt(3) - 1;
					if (world.getBlockId(i2, j2 - 1, k2) != Block.grass.blockID || world.isBlockNormalCube(i2, j2, k2)) {
						continue label0;
					}
				}
				if (world.isAirBlock(i2, j2, k2)) {
					if (itemRand.nextInt(7) != 0 && Block.tallGrass.canBlockStay(world, i2, j2, k2)) {
						int j3 = 1;
						if ((biome instanceof LKBiomeGenRainforest || biome instanceof LKBiomeGenUpendi) && itemRand.nextInt(5) == 0) {
							j3 = 2;
						}
						world.setBlock(i2, j2, k2, Block.tallGrass.blockID, j3, 3);
					} else {
						if (biome instanceof LKBiomeGenUpendi) {
							int i3 = itemRand.nextInt(6);
							if (i3 == 0 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 1, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 1, 3);
							} else if (i3 > 3 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 0, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 0, 3);
							} else if (LKMod.whiteFlower.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.whiteFlower.blockID, 0, 3);
							}
						}
						if (biome instanceof LKBiomeGenMountains && LKMod.blueFlower.canBlockStay(world, i2, j2, k2)) {
							world.setBlock(i2, j2, k2, LKMod.blueFlower.blockID, 0, 3);
						}
						if (biome instanceof LKBiomeGenRainforest) {
							int i3 = itemRand.nextInt(5);
							if (i3 < 2 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 0, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 0, 3);
							} else if (LKMod.whiteFlower.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.whiteFlower.blockID, 0, 3);
							}
						}
						if (biome instanceof LKBiomeGenSavannahBase || biome instanceof LKBiomeGenRiver || biome instanceof LKBiomeGenAridSavannah) {
							int i3 = itemRand.nextInt(5);
							if (i3 == 0 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 0, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 0, 3);
							} else if (LKMod.whiteFlower.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.whiteFlower.blockID, 0, 3);
							}
						}
					}
				}
			}
			damageRafikiStick(itemstack, 3, entityplayer);
			return true;
		}

		if (i1 == Block.sand.blockID) {
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
			if (biome instanceof LKBiomeGenAridSavannah || biome instanceof LKBiomeGenDesert) {
				world.notifyBlockChange(i, j, k, world.getBlockId(i, j, k));
				label0:
				for (int l1 = 0; l1 < 128; ++l1) {
					int i2 = i;
					int j2 = j + 1;
					int k2 = k;
					for (int l2 = 0; l2 < l1 / 16; ++l2) {
						i2 += itemRand.nextInt(3) - 1;
						j2 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
						k2 += itemRand.nextInt(3) - 1;
						if (world.getBlockId(i2, j2 - 1, k2) != Block.sand.blockID || world.isBlockNormalCube(i2, j2, k2)) {
							continue label0;
						}
					}
					if (world.isAirBlock(i2, j2, k2)) {
						if (world.rand.nextInt(16) == 0 && Block.deadBush.canBlockStay(world, i2, j2, k2)) {
							world.setBlock(i2, j2, k2, Block.deadBush.blockID, 0, 3);
						} else if (LKMod.aridGrass.canBlockStay(world, i2, j2, k2)) {
							world.setBlock(i2, j2, k2, LKMod.aridGrass.blockID, 0, 3);
						}
					}
				}
				damageRafikiStick(itemstack, 2, entityplayer);
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		int level = EnchantmentHelper.getEnchantmentLevel(LKMod.rafikiStickThunder.effectId, itemstack);
		if (level > 0 && lastThunderUseTick == 0) {
			MovingObjectPosition m = entityplayer.rayTrace(5, 1.0F);
			if (m != null) {
				return itemstack;
			}
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int l) {
		int level = EnchantmentHelper.getEnchantmentLevel(LKMod.rafikiStickThunder.effectId, itemstack);
		if (level > 0) {
			MovingObjectPosition m = entityplayer.rayTrace(2 + Math.pow(4, level + 1), 1.0F);
			MovingObjectPosition n = entityplayer.rayTrace(5, 1.0F);
			if (m != null && m.typeOfHit == EnumMovingObjectType.TILE && n == null) {
				int i = m.blockX;
				int j = m.blockY;
				int k = m.blockZ;
				entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
				if (world.isBlockOpaqueCube(i, j, k) && world.isAirBlock(i, j + 1, k)) {
					LKEntityLightning bolt = new LKEntityLightning(entityplayer, world, i, j, k, level);
					if (!world.isRemote) {
						world.spawnEntityInWorld(bolt);
					}
					damageRafikiStick(itemstack, 10, entityplayer);
					lastThunderUseTick = 12;
					return;
				}
			}
			for (int i = 0; i < 7; i++) {
				double d = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				world.spawnParticle("smoke", (entityplayer.posX + (itemRand.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width,
						entityplayer.posY - 0.5D + (itemRand.nextFloat() * (entityplayer.height / 2)),
						(entityplayer.posZ + (itemRand.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width, d, d1, d2);
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining) {
		if (lastThunderUseTick > 0) {
			lastThunderUseTick--;
		}
		if (usingItem != null && usingItem == itemstack) {
			return thunderIcons[itemRand.nextInt(6)];
		}
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		super.registerIcons(iconregister);
		thunderIcons = new Icon[6];
		for (int i = 0; i < 6; i++) {
			thunderIcons[i] = iconregister.registerIcon("lionking:rafikiStick_glow_" + i);
		}
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
		if (!(entityliving instanceof LKEntityScar)) {
			damageRafikiStick(itemstack, 2, entityliving1);
		}
		return true;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 5.0D, 0));
		return multimap;
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block) {
		if (block instanceof LKBlockLeaves || block instanceof BlockTallGrass || block instanceof BlockDeadBush || block instanceof LKBlockAridGrass) {
			return 15.0F;
		} else {
			return super.getStrVsBlock(itemstack, block);
		}
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int i, int j, int k, int l, EntityLivingBase entityliving) {
		if (Block.blocksList[i] instanceof LKBlockLeaves) {
			damageRafikiStick(itemstack, 1, entityliving);
			return true;
		} else {
			return super.onBlockDestroyed(itemstack, world, i, j, k, l, entityliving);
		}
	}

	private void damageRafikiStick(ItemStack itemstack, int i, EntityLivingBase entityliving) {
		if (itemstack.isItemStackDamageable()) {
			if (i > 0 && entityliving instanceof EntityPlayer) {
				int j = EnchantmentHelper.getEnchantmentLevel(LKMod.rafikiStickDurability.effectId, itemstack);
				if (j > 0 && entityliving.worldObj.rand.nextInt(j + 1) > 0) {
					return;
				}
			}
			int currentDamage = itemstack.getItemDamage();
			itemstack.setItemDamage(currentDamage + i);
			if (itemstack.getItemDamage() > itemstack.getMaxDamage()) {
				entityliving.renderBrokenItemStack(itemstack);
				if (entityliving instanceof EntityPlayer) {
					((EntityPlayer) entityliving).addStat(StatList.objectBreakStats[itemstack.itemID], 1);
				}
				--itemstack.stackSize;
				if (itemstack.stackSize < 0) {
					itemstack.stackSize = 0;
				}
				itemstack.setItemDamage(0);
			}
		}
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		int id = player.worldObj.getBlockId(x, y, z);
		if (Block.blocksList[id] != null && Block.blocksList[id] instanceof IShearable) {
			IShearable target = (IShearable) Block.blocksList[id];
			if (target.isShearable(itemstack, player.worldObj, x, y, z)) {
				Iterable<ItemStack> drops = target.onSheared(itemstack, player.worldObj, x, y, z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
				for (ItemStack stack : drops) {
					float f = 0.7F;
					double d = (player.getRNG().nextFloat() * f) + (1.0F - f) * 0.5D;
					double d1 = (player.getRNG().nextFloat() * f) + (1.0F - f) * 0.5D;
					double d2 = (player.getRNG().nextFloat() * f) + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(player.worldObj, x + d, y + d1, z + d2, stack);
					entityitem.delayBeforeCanPickup = 10;
					player.worldObj.spawnEntityInWorld(entityitem);
				}
				damageRafikiStick(itemstack, 1, player);
				player.addStat(StatList.mineBlockStatArray[id], 1);
			}
		} else if (id == Block.deadBush.blockID) {
			ItemStack stack = new ItemStack(Block.deadBush);
			float f = 0.7F;
			double d = (player.getRNG().nextFloat() * f) + (1.0F - f) * 0.5D;
			double d1 = (player.getRNG().nextFloat() * f) + (1.0F - f) * 0.5D;
			double d2 = (player.getRNG().nextFloat() * f) + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(player.worldObj, x + d, y + d1, z + d2, stack);
			entityitem.delayBeforeCanPickup = 10;
			player.worldObj.spawnEntityInWorld(entityitem);
			damageRafikiStick(itemstack, 1, player);
			player.addStat(StatList.mineBlockStatArray[id], 1);
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.uncommon;
	}
}
