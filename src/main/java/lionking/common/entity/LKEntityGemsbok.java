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

public class LKEntityGemsbok extends LKEntityQuestAnimal {
	public LKEntityGemsbok(World world) {
		super(world);
		setSize(0.9F, 1.4F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.3D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.2D, Item.wheat.itemID, false));
		tasks.addTask(3, new EntityAITempt(this, 1.2D, LKMod.corn.itemID, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
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
	protected int getDropItemId() {
		return LKMod.gemsbokHide.itemID;
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		int j = getRNG().nextInt(2) + getRNG().nextInt(1 + i) + 1;
		for (int k = 0; k < j; k++) {
			dropItem(LKMod.gemsbokHide.itemID, 1);
		}
		if (getRNG().nextBoolean()) {
			dropItem(LKMod.gemsbokHorn.itemID, 1);
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return new LKEntityGemsbok(worldObj);
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

	public LKCharacterSpeech getCharacterSpeech() {
		return LKCharacterSpeech.GEMSBOK;
	}

	public LKCharacterSpeech getChildCharacterSpeech() {
		return LKCharacterSpeech.GEMSBOK_CALF;
	}

	public String getAnimalName() {
		return "Gemsbok";
	}

	public ItemStack getQuestItem() {
		int i = getRNG().nextInt(5);
		switch (i) {
			case 1:
				return new ItemStack(LKMod.kiwano, 6 + getRNG().nextInt(17));
			case 2:
				return new ItemStack(Block.tallGrass, 10 + getRNG().nextInt(6), 1);
			case 3:
				return new ItemStack(LKMod.prideWood, 8 + getRNG().nextInt(16));
			case 4:
				return new ItemStack(Block.tallGrass, 3 + getRNG().nextInt(7), 2);
			default:
				return new ItemStack(LKMod.mango, 5 + getRNG().nextInt(10));
		}
	}
}
