package lionking.common.entity;

import lionking.common.LKAchievementList;
import lionking.common.LKIngame;
import lionking.common.LKMod;
import lionking.common.quest.LKAnimalQuest;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LKEntityQuestAnimal extends EntityAnimal {
	public LKAnimalQuest quest;
	private int talkTick = 40;

	protected LKEntityQuestAnimal(World world) {
		super(world);
	}

	public abstract LKCharacterSpeech getCharacterSpeech();

	public abstract LKCharacterSpeech getChildCharacterSpeech();

	public abstract String getAnimalName();

	public abstract ItemStack getQuestItem();

	public double getQuestItemRenderOffset() {
		return 0.25D;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		quest = new LKAnimalQuest();
		quest.init(dataWatcher);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (talkTick < 40) {
			talkTick++;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		quest.writeToNBT(nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		quest.readFromNBT(nbt);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (!super.interact(entityplayer)) {
			if (LKIngame.hasAmulet(entityplayer) && talkTick == 40) {
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if (isChild()) {
					if (!worldObj.isRemote) {
						LKIngame.sendMessageToAllPlayers(LKCharacterSpeech.giveSpeech(getChildCharacterSpeech()));
					}
					talkTick = 0;
					return true;
				} else {
					if (quest.hasQuest() && itemstack != null && quest.isRequiredItem(itemstack)) {
						int i = itemstack.stackSize - quest.getRequiredItem().stackSize;
						if (i == 0) {
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
						} else {
							itemstack.stackSize = i;
						}
						if (!worldObj.isRemote) {
							LKIngame.sendMessageToAllPlayers(LKAnimalQuest.endQuest(getAnimalName()));
							for (int j = 0; j < 5; j++) {
								worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, 11 + getRNG().nextInt(5)));
							}
						}
						quest.setHasQuest(false);
						entityplayer.triggerAchievement(LKAchievementList.animalQuest);
						talkTick = 0;
						return true;
					} else {
						if (!quest.hasQuest() && getRNG().nextInt(5) == 0) {
							if (!worldObj.isRemote) {
								ItemStack questItem = getQuestItem();
								quest.setQuest(questItem);
								LKIngame.sendMessageToAllPlayers(LKAnimalQuest.startQuest(getAnimalName(), questItem));
							}
							talkTick = 0;
							return true;
						} else {
							if (!worldObj.isRemote) {
								LKIngame.sendMessageToAllPlayers(LKCharacterSpeech.giveSpeech(getCharacterSpeech()));
							}
							talkTick = 0;
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LKMod.spawnEgg, 1, LKEntities.getEntityID(this));
	}
}