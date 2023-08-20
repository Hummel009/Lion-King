package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.entity.LKEntityOutlander;
import lionking.common.entity.LKEntityZira;
import lionking.common.tileentity.LKTileEntityOutlandsPool;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockOutlandsPool extends BlockContainer {
	public LKBlockOutlandsPool(int i) {
		super(i, Material.rock);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.875F, 1.0F);
		setLightValue(0.875F);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new LKTileEntityOutlandsPool();
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int i, int j, int k) {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess world, int i, int j, int k) {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int l) {
		if (world.getBlockId(i, j, k) == blockID) {
			return false;
		}
		if (l == 1) {
			return true;
		}
		return super.shouldSideBeRendered(world, i, j, k, l);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getMobilityFlag() {
		return 2;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextBoolean()) {
			world.spawnParticle("smoke", (i + random.nextFloat()), (j + 0.8F), (k + random.nextFloat()), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (!(entity instanceof LKEntityOutlander) && !(entity instanceof LKEntityZira)) {
				entity.attackEntityFrom(DamageSource.inFire, 2.0F);
			}
			world.playSoundAtEntity(entity, "random.fizz", 0.7F, 1.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return 0;
	}
}
