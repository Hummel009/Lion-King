package lionking.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.nio.ByteBuffer;

public class LKTileEntityMountedShooter extends TileEntity {
	public int dartID;
	public int dartStackSize;
	public int fireCounter;
	private int shooterType;

	@Override
	public void updateEntity() {
		if (fireCounter > 0) {
			fireCounter--;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("ShooterType", (byte) shooterType);
		nbt.setInteger("DartID", dartID);
		nbt.setInteger("DartStackSize", dartStackSize);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		shooterType = nbt.getByte("ShooterType");
		dartID = nbt.getInteger("DartID");
		dartStackSize = nbt.getInteger("DartStackSize");
	}

	public int getShooterType() {
		return shooterType;
	}

	public void setShooterType(int i) {
		shooterType = i;
	}

	@Override
	public Packet getDescriptionPacket() {
		byte[] posX = ByteBuffer.allocate(4).putInt(xCoord).array();
		byte[] posY = ByteBuffer.allocate(4).putInt(yCoord).array();
		byte[] posZ = ByteBuffer.allocate(4).putInt(zCoord).array();
		byte[] data = new byte[13];
		for (int i = 0; i < 4; i++) {
			data[i] = posX[i];
			data[i + 4] = posY[i];
			data[i + 8] = posZ[i];
		}
		data[12] = (byte) shooterType;
		return new Packet250CustomPayload("lk.tileEntity", data);
	}
}
