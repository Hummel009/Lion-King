package lionking.common.quest;

import lionking.common.LKMod;
import net.minecraft.item.ItemStack;

public class LKQuestRafiki extends LKQuestBase {
	public LKQuestRafiki(int i) {
		super(i);
	}

	public boolean canStart() {
		return true;
	}

	public String[] getRequirements() {
		return null;
	}

	public int getNumStages() {
		return 7;
	}

	public ItemStack getIcon() {
		return new ItemStack(LKMod.rafikiStick);
	}

	public String getObjectiveByStage(int i) {
		switch (i) {
			case 0:
				return "Speak to Rafiki in his tree at the centre of the world";
			case 1:
				return "Bring Rafiki a full stack of hyena bones";
			case 2:
				return "Find and defeat Scar";
			case 3:
				return "Return to Rafiki";
			case 4:
				return "Bring Rafiki four ground termites";
			case 5:
				return "Bring Rafiki four ground mangoes";
			case 6:
				return "Build a Star Altar and use the Rafiki Dust on it";
			case 7:
				return "Quest complete!";
			default:
				return "";
		}
	}
}
