package lionking.common.quest;

import lionking.common.LKMod;
import net.minecraft.item.ItemStack;

public class LKQuestOutlands extends LKQuestBase {
	public LKQuestOutlands(int i) {
		super(i);
	}

	public boolean canStart() {
		return LKQuestBase.rafikiQuest.isComplete();
	}

	public String[] getRequirements() {
		return new String[]{"Complete Rafiki's Quest"};
	}

	public int getNumStages() {
		return 10;
	}

	public ItemStack getIcon() {
		return new ItemStack(LKMod.outlandsFeather);
	}

	public String getObjectiveByStage(int i) {
		switch (i) {
			case 0:
				return "Use a Rafiki Stick to open the large mound in the Outlands";
			case 1:
				return "Speak with Zira";
			case 2:
				return "Bring Zira five kivulite and two silver ingots";
			case 3:
				return "Throw the ingots into the Outwater";
			case 4:
				return "Bring Zira three Wayward Feathers";
			case 5:
				return "Follow the Outlanders back to the Pride Lands";
			case 6:
				return "Speak to Timon and Pumbaa";
			case 7:
				return "Obtain the ingredients for Pumbaa";
			case 8:
				return "Expel the Outlanders from Rafiki's Tree";
			case 9:
				return "Defeat Zira";
			case 10:
				return "Quest complete!";
			default:
				return "";
		}
	}
}
