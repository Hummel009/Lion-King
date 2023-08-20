package lionking.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.nio.ByteBuffer;

public class LKTileEntityHyenaHead extends TileEntity {
	private int hyenaType;
	private int rotation;

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("HyenaType", (byte) hyenaType);
		nbt.setByte("Rotation", (byte) rotation);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		hyenaType = nbt.getByte("HyenaType");
		rotation = nbt.getByte("Rotation");
	}

	public int getHyenaType() {
		return hyenaType;
	}

	public void setHyenaType(int i) {
		hyenaType = i;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int i) {
		rotation = i;
	}

	@Override
	public Packet getDescriptionPacket() {
		byte[] posX = ByteBuffer.allocate(4).putInt(xCoord).array();
		byte[] posY = ByteBuffer.allocate(4).putInt(yCoord).array();
		byte[] posZ = ByteBuffer.allocate(4).putInt(zCoord).array();
		byte[] data = new byte[14];
		for (int i = 0; i < 4; i++) {
			data[i] = posX[i];
			data[i + 4] = posY[i];
			data[i + 8] = posZ[i];
		}
		data[12] = (byte) hyenaType;
		data[13] = (byte) rotation;
		return new Packet250CustomPayload("lk.tileEntity", data);
	}
}
