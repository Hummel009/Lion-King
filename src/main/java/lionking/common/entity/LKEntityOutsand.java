package lionking.common.entity;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LKEntityOutsand extends Entity {
	public int blockID;
	public int fallTime;

	public LKEntityOutsand(World world) {
		super(world);
		fallTime = 0;
	}

	public LKEntityOutsand(World world, double d, double d1, double d2, int i) {
		super(world);
		fallTime = 0;
		blockID = i;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
		setPosition(d, d1, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public void onUpdate() {
		if (blockID == 0) {
			setDead();
			return;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		fallTime++;
		motionY -= 0.039999999105930328D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.98000001907348633D;
		motionY *= 0.98000001907348633D;
		motionZ *= 0.98000001907348633D;
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);
		if (fallTime == 1 && worldObj.getBlockId(i, j, k) == blockID) {
			worldObj.setBlock(i, j, k, 0, 0, 3);
		} else if (!worldObj.isRemote && fallTime == 1) {
			setDead();
		}
		if (onGround) {
			motionX *= 0.69999998807907104D;
			motionZ *= 0.69999998807907104D;
			motionY *= -0.5D;
			if (worldObj.getBlockId(i, j, k) != Block.pistonMoving.blockID) {
				setDead();
				if ((!worldObj.canPlaceEntityOnSide(blockID, i, j, k, true, 1, null, null) || BlockSand.canFallBelow(worldObj, i, j - 1, k) || !worldObj.setBlock(i, j, k, blockID, 0, 3)) && !worldObj.isRemote) {
					dropItem(blockID, 1);
				}
			}
		} else if (fallTime > 100 && !worldObj.isRemote) {
			dropItem(blockID, 1);
			setDead();
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Tile", blockID);
		nbt.setInteger("Time", fallTime);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		blockID = nbt.getInteger("Tile");
		fallTime = nbt.getInteger("Time");
		if (blockID == 0) {
			blockID = LKMod.outsand.blockID;
		}
	}

	@Override
	public float getShadowSize() {
		return 0.0F;
	}

	public World getWorld() {
		return worldObj;
	}
}
