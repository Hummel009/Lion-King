package lionking.common.entity;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKEntityZebra extends LKEntityQuestAnimal {
	public LKEntityZebra(World world) {
		super(world);
		setSize(1.1F, 1.4F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.2D, Item.wheat.itemID, false));
		tasks.addTask(3, new EntityAITempt(this, 1.2D, LKMod.corn.itemID, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.2D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(12.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack.itemID == Item.wheat.itemID || itemstack.itemID == LKMod.corn.itemID;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return new LKEntityZebra(worldObj);
	}

	@Override
	protected int getDropItemId() {
		return LKMod.zebraHide.itemID;
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		int j = getRNG().nextInt(2) + getRNG().nextInt(1 + i);
		for (int k = 0; k < j; k++) {
			dropItem(LKMod.zebraHide.itemID, 1);
		}
		j = getRNG().nextInt(2) + 1 + getRNG().nextInt(1 + i);
		for (int l = 0; l < j; l++) {
			if (isBurning()) {
				dropItem(LKMod.zebraCooked.itemID, 1);
			} else {
				dropItem(LKMod.zebraRaw.itemID, 1);
			}
		}
	}

	@Override
	protected String getLivingSound() {
		return "lionking:zebra";
	}

	@Override
	protected String getHurtSound() {
		return "lionking:zebrahurt";
	}

	@Override
	protected String getDeathSound() {
		return "lionking:zebradeath";
	}


	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.itemID == LKMod.jar.itemID && !isChild()) {
			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(LKMod.jarMilk));
			return true;
		}
		return super.interact(entityplayer);
	}

	public LKCharacterSpeech getCharacterSpeech() {
		return LKCharacterSpeech.ZEBRA;
	}

	public LKCharacterSpeech getChildCharacterSpeech() {
		return LKCharacterSpeech.ZEBRA_FOAL;
	}

	public String getAnimalName() {
		return "Zebra";
	}

	public ItemStack getQuestItem() {
		int i = getRNG().nextInt(5);
		switch (i) {
			case 1:
				return new ItemStack(LKMod.kiwano, 6 + getRNG().nextInt(15));
			case 2:
				return new ItemStack(LKMod.mangoLeaves, 4 + getRNG().nextInt(4));
			case 3:
				return new ItemStack(Item.stick, 40 + getRNG().nextInt(25));
			case 4:
				return new ItemStack(Block.tallGrass, 3 + getRNG().nextInt(8), 2);
			default:
				return new ItemStack(LKMod.mango, 6 + getRNG().nextInt(9));
		}
	}
}
