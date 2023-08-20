package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.entity.LKEntityOutsand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

public class LKBlockOutsand extends LKBlock {
	public LKBlockOutsand(int i) {
		super(i, Material.sand);
	}

	public static boolean canFallBelow(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockId(i, j, k);
		if (l == 0) {
			return true;
		}
		if (l == Block.fire.blockID) {
			return true;
		}
		Material material = Block.blocksList[l].blockMaterial;
		if (material == Material.water) {
			return true;
		}
		return material == Material.lava;
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, int metadata, ForgeDirection side) {
		return side == ForgeDirection.UP;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		tryToFall(world, i, j, k);
	}

	private void tryToFall(World world, int i, int j, int k) {
		int i1 = j;
		if (canFallBelow(world, i, i1 - 1, k) && i1 >= 0) {
			byte byte0 = 32;
			if (BlockSand.fallInstantly || !world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
				world.setBlockToAir(i, j, k);
				for (; canFallBelow(world, i, j - 1, k) && j > 0; j--) {
				}
				if (j > 0) {
					world.setBlock(i, j, k, blockID, 0, 3);
				}
			} else if (!world.isRemote) {
				LKEntityOutsand entity = new LKEntityOutsand(world, i + 0.5F, j + 0.5F, k + 0.5F, blockID);
				world.spawnEntityInWorld(entity);
			}
		}
	}

	@Override
	public int tickRate(World world) {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		double d = i + random.nextFloat();
		double d1 = j + random.nextFloat();
		double d2 = k + random.nextFloat();
		double d3;
		double d4;
		double d5;
		int i1 = random.nextInt(2) * 2 - 1;
		d3 = (random.nextFloat() - 0.5D) * 0.5D;
		d4 = (random.nextFloat() - 0.5D) * 0.5D;
		d5 = (random.nextFloat() - 0.5D) * 0.5D;
		d = i + 0.5D + 0.25D * i1;
		d3 = random.nextFloat() * 2.0F * i1;
		world.spawnParticle("smoke", d, d1, d2, d3, d4, d5);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityLivingBase && !entity.isImmuneToFire() && world.rand.nextBoolean()) {
			entity.attackEntityFrom(DamageSource.inFire, 2.0F);
		}
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityLivingBase && !entity.isImmuneToFire() && world.rand.nextBoolean()) {
			entity.attackEntityFrom(DamageSource.inFire, 2.0F);
		}
	}
}
