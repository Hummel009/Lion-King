package lionking.common.entity;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKEntityScarRug extends Entity {
	private int talkTick = 40;

	public LKEntityScarRug(World world) {
		super(world);
		setSize(1.2F, 0.2F);
	}

	public LKEntityScarRug(World world, int i) {
		this(world);
		setType(i);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (talkTick < 40) {
			talkTick++;
		}

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;

		pushOutOfBlocks(posX, (boundingBox.minY + boundingBox.maxY) / 2.0D, posZ);
		moveEntity(motionX, motionY, motionZ);
		float f = 0.98F;

		if (onGround) {
			f = 0.58800006F;
			int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));

			if (i > 0) {
				f = Block.blocksList[i].slipperiness * 0.98F;
			}
		}

		motionX *= f;
		motionY *= 0.9800000190734863D;
		motionZ *= f;

		if (onGround) {
			motionY *= -0.5D;
		}
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(16, 0);
	}

	public int getType() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	public void setType(int i) {
		dataWatcher.updateObject(16, i);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Type", getType());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		setType(nbt.getInteger("Type"));
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public void dropAsItem() {
		worldObj.playSoundAtEntity(this, "lionking:lionangry", 1.0F, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);
		if (!worldObj.isRemote) {
			entityDropItem(getRugItem(), 0.0F);
		}
		setDead();
	}

	public boolean interact(EntityPlayer entityplayer) {
		if (talkTick == 40) {
			worldObj.playSoundAtEntity(this, "lionking:lionroar", 1.0F, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);
			if (!worldObj.isRemote) {
				entityplayer.addChatMessage(LKCharacterSpeech.giveSpeech(getType() == 0 ? LKCharacterSpeech.RUG_SCAR : LKCharacterSpeech.RUG_ZIRA));
			}
			talkTick = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damage, float f) {
		return false;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return getRugItem();
	}

	private ItemStack getRugItem() {
		return new ItemStack(getType() == 0 ? LKMod.scarRug : LKMod.ziraRug);
	}
}
