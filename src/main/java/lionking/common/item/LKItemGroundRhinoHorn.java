package lionking.common.item;

import lionking.common.LKAchievementList;
import lionking.common.LKCreativeTabs;
import lionking.common.entity.LKAngerable;
import lionking.common.entity.ai.LKEntityAIAngerableMate;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITaskEntry;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class LKItemGroundRhinoHorn extends LKItem {
	public LKItemGroundRhinoHorn(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabMaterials);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entityliving) {
		if (entityliving.getHealth() <= 0) {
			return false;
		}
		try {
			if (entityliving instanceof LKAngerable && entityliving instanceof EntityAnimal) {
				EntityAnimal animal = (EntityAnimal) entityliving;
				List tasks = animal.tasks.taskEntries;
				LKEntityAIAngerableMate matingAI = null;
				for (Object obj : tasks) {
					if (obj instanceof EntityAITaskEntry) {
						EntityAIBase AI = ((EntityAITaskEntry) obj).action;
						if (AI instanceof LKEntityAIAngerableMate) {
							matingAI = (LKEntityAIAngerableMate) AI;
						}
					}
				}
				if (matingAI != null && matingAI.shouldExecute() && matingAI.continueExecuting()) {
					EntityAnimal targetMate = matingAI.targetMate;
					if (targetMate != null) {
						if (itemRand.nextInt(3) == 0) {
							double d = itemRand.nextGaussian() * 0.02D;
							double d1 = itemRand.nextGaussian() * 0.02D;
							double d2 = itemRand.nextGaussian() * 0.02D;
							entityliving.worldObj.spawnParticle("smoke", entityliving.posX + (itemRand.nextFloat() * entityliving.width * 2.0F) - entityliving.width, entityliving.posY + 0.5D + (itemRand.nextFloat() * entityliving.height), entityliving.posZ + (itemRand.nextFloat() * entityliving.width * 2.0F) - entityliving.width, d, d1, d2);
							itemstack.stackSize--;
							return false;
						}
						procreate(animal);
						itemstack.stackSize--;
						entityplayer.triggerAchievement(LKAchievementList.rhinoHorn);
						return true;
					}
				}
			}
		} catch (SecurityException exception) {
			exception.printStackTrace();
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	private void procreate(EntityAnimal theAnimal) {
		World theWorld = theAnimal.worldObj;
		EntityAgeable babyAnimal = theAnimal.createChild(theAnimal);
		if (babyAnimal != null) {
			babyAnimal.setGrowingAge(-24000);
			babyAnimal.setLocationAndAngles(theAnimal.posX, theAnimal.posY, theAnimal.posZ, 0.0F, 0.0F);
			if (!theWorld.isRemote) {
				theWorld.spawnEntityInWorld(babyAnimal);
			}
			for (int i = 0; i < 7; i++) {
				double d = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				theWorld.spawnParticle("heart", theAnimal.posX + (itemRand.nextFloat() * theAnimal.width * 2.0F) - theAnimal.width, theAnimal.posY + 0.5D + (itemRand.nextFloat() * theAnimal.height), theAnimal.posZ + (itemRand.nextFloat() * theAnimal.width * 2.0F) - theAnimal.width, d, d1, d2);
			}
		}
	}
}
