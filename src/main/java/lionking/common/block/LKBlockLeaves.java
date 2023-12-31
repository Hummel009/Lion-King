package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

public class LKBlockLeaves extends BlockLeavesBase implements IShearable {
	@SideOnly(Side.CLIENT)
	private Icon[] leafIcons;
	private int[] adjacentTreeBlocks;
	private int leafMode;

	public LKBlockLeaves(int i) {
		super(i, Material.leaves, false);
		setTickRandomly(true);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	public boolean isOpaqueCube() {
		return !graphicsLevel;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int i3, int j3) {
		int l = 1;
		int i1 = l + 1;
		if (world.checkChunksExist(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
			for (int j1 = -l; j1 <= l; j1++) {
				for (int k1 = -l; k1 <= l; k1++) {
					for (int l1 = -l; l1 <= l; l1++) {
						int i2 = world.getBlockId(i + j1, j + k1, k + l1);
						if (i2 == blockID) {
							int j2 = world.getBlockMetadata(i + j1, j + k1, k + l1);
							world.setBlockMetadataWithNotify(i + j1, j + k1, k + l1, j2 | 8, 4);
						}
					}

				}

			}

		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (world.isRemote) {
			return;
		}
		int l = world.getBlockMetadata(i, j, k);
		if ((l & 8) != 0 && (l & 4) == 0) {
			byte byte0 = 4;
			int i1 = byte0 + 1;
			byte byte1 = 32;
			int j1 = byte1 * byte1;
			int k1 = byte1 / 2;
			if (adjacentTreeBlocks == null) {
				adjacentTreeBlocks = new int[byte1 * byte1 * byte1];
			}
			if (world.checkChunksExist(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
				for (int l1 = -byte0; l1 <= byte0; l1++) {
					for (int k2 = -byte0; k2 <= byte0; k2++) {
						for (int i3 = -byte0; i3 <= byte0; i3++) {
							int k3 = world.getBlockId(i + l1, j + k2, k + i3);
							if (k3 == LKMod.prideWood.blockID || k3 == LKMod.prideWood2.blockID) {
								adjacentTreeBlocks[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = 0;
								continue;
							}
							if (k3 == blockID) {
								adjacentTreeBlocks[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = -2;
							} else {
								adjacentTreeBlocks[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = -1;
							}
						}

					}

				}

				for (int i2 = 1; i2 <= 4; i2++) {
					for (int l2 = -byte0; l2 <= byte0; l2++) {
						for (int j3 = -byte0; j3 <= byte0; j3++) {
							for (int l3 = -byte0; l3 <= byte0; l3++) {
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] != i2 - 1) {
									continue;
								}
								if (adjacentTreeBlocks[((l2 + k1) - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[((l2 + k1) - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[(l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + ((j3 + k1) - 1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + ((j3 + k1) - 1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + ((l3 + k1) - 1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + ((l3 + k1) - 1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1)] = i2;
								}
							}

						}

					}

				}

			}
			int j2 = adjacentTreeBlocks[k1 * j1 + k1 * byte1 + k1];
			if (j2 >= 0) {
				world.setBlockMetadataWithNotify(i, j, k, l & -9, 4);
			} else {
				removeLeaves(world, i, j, k);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.canLightningStrikeAt(i, j + 1, k) && !world.doesBlockHaveSolidTopSurface(i, j - 1, k) && random.nextInt(15) == 1) {
			double d = (i + random.nextFloat());
			double d1 = j - 0.05D;
			double d2 = (k + random.nextFloat());
			world.spawnParticle("dripWater", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}

		if (blockID == LKMod.passionLeaves.blockID) {
			for (int l = 0; l < 4; l++) {
				double d = i + random.nextFloat();
				double d1 = j + random.nextFloat();
				double d2 = k + random.nextFloat();
				double d3 = (-0.5F + random.nextFloat()) * 0.01F;
				double d4 = random.nextFloat() * 0.01F;
				double d5 = (-0.5F + random.nextFloat()) * 0.01F;
				LKMod.proxy.spawnParticle("passion", d, d1, d2, d3, d4, d5);
			}
		}
	}

	private void removeLeaves(World world, int i, int j, int k) {
		dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
		world.setBlockToAir(i, j, k);
	}

	@Override
	public int quantityDropped(Random random) {
		if (blockID == LKMod.mangoLeaves.blockID || blockID == LKMod.passionLeaves.blockID) {
			return random.nextInt(12) == 0 ? 1 : 0;
		}
		if (blockID == LKMod.bananaLeaves.blockID) {
			return random.nextInt(9) == 0 ? 1 : 0;
		}
		return random.nextInt(22) == 0 ? 1 : 0;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		if (blockID == LKMod.forestLeaves.blockID) {
			return LKMod.forestSapling.blockID;
		}
		if (blockID == LKMod.mangoLeaves.blockID) {
			if (random.nextInt(5) > 1) {
				return LKMod.mango.itemID;
			} else {
				return LKMod.mangoSapling.blockID;
			}
		}
		if (blockID == LKMod.passionLeaves.blockID) {
			if (random.nextBoolean()) {
				return LKMod.passionFruit.itemID;
			} else {
				return LKMod.passionSapling.blockID;
			}
		}
		if (blockID == LKMod.bananaLeaves.blockID) {
			return LKMod.bananaSapling.blockID;
		}
		return LKMod.sapling.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		return leafIcons[leafMode];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		leafIcons = new Icon[2];
		leafIcons[0] = iconregister.registerIcon(getTextureName() + "_fast");
		leafIcons[1] = iconregister.registerIcon(getTextureName() + "_fancy");
	}

	public void setGraphicsLevel(boolean flag) {
		graphicsLevel = flag;
		leafMode = flag ? 1 : 0;
	}

	@Override
	public boolean isShearable(ItemStack item, World world, int x, int y, int z) {
		return true;
	}

	@Override
	public ArrayList onSheared(ItemStack item, World world, int x, int y, int z, int fortune) {
		ArrayList ret = new ArrayList();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
		return ret;
	}
}
