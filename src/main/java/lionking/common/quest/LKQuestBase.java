package lionking.common.quest;

import cpw.mods.fml.common.network.PacketDispatcher;
import lionking.common.LKLevelData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class LKQuestBase {
	public static LKQuestBase[] allQuests = new LKQuestBase[16];

	public static LKQuestBase rafikiQuest = new LKQuestRafiki(0).setName("Rafiki's Quest");
	public static LKQuestBase outlandsQuest = new LKQuestOutlands(1).setName("An Outlandish Scheme");
	public static ArrayList orderedQuests;

	static {
		orderedQuests = new ArrayList();
		orderedQuests.add(rafikiQuest);
		orderedQuests.add(outlandsQuest);
	}

	public int stagesDelayed;
	public int currentStage;
	public int[] stagesCompleted = new int[getNumStages() + 1];
	public int checked;
	public int questIndex;
	private String questName;

	protected LKQuestBase(int i) {
		allQuests[i] = this;
		questIndex = i;
	}

	public static boolean anyUncheckedQuests() {
		for (LKQuestBase quest : allQuests) {
			if (quest == null) {
				continue;
			}
			if (quest.canStart() && !quest.isChecked()) {
				return true;
			}
		}
		return false;
	}

	public static void updateAllQuests() {
		for (LKQuestBase quest : allQuests) {
			if (quest == null) {
				continue;
			}
			if (quest.stagesDelayed == 0 && quest.stagesCompleted[quest.currentStage] == 1 && quest.currentStage < quest.getNumStages()) {
				quest.currentStage++;

				byte[] data = new byte[2];
				data[0] = (byte) quest.questIndex;
				data[1] = (byte) quest.currentStage;
				Packet250CustomPayload packet = new Packet250CustomPayload("lk.questStage", data);
				PacketDispatcher.sendPacketToAllPlayers(packet);
				LKLevelData.needsSave = true;
			}
		}
	}

	public static void writeAllQuestsToNBT(NBTTagCompound nbt) {
		for (int i = 0; i < allQuests.length; i++) {
			LKQuestBase quest = allQuests[i];
			if (quest == null) {
				continue;
			}
			nbt.setInteger("Quest_" + i + "_Stage", quest.currentStage);
			nbt.setInteger("Quest_" + i + "_Delayed", quest.stagesDelayed);
			for (int j = 0; j < quest.stagesCompleted.length; j++) {
				nbt.setInteger("Quest_" + i + "_CompletedStage_" + j, quest.stagesCompleted[j]);
			}
			nbt.setInteger("Quest_" + i + "_Checked", quest.checked);
		}
	}

	public static void readAllQuestsFromNBT(NBTTagCompound nbt) {
		for (int i = 0; i < allQuests.length; i++) {
			LKQuestBase quest = allQuests[i];
			if (quest == null) {
				continue;
			}
			quest.currentStage = nbt.getInteger("Quest_" + i + "_Stage");
			quest.stagesDelayed = nbt.getInteger("Quest_" + i + "_Delayed");
			for (int j = 0; j < quest.stagesCompleted.length; j++) {
				quest.stagesCompleted[j] = nbt.getInteger("Quest_" + i + "_CompletedStage_" + j);
			}
			quest.checked = nbt.getInteger("Quest_" + i + "_Checked");
		}
	}

	public String getName() {
		return questName;
	}

	public LKQuestBase setName(String name) {
		questName = name;
		return this;
	}

	public boolean isComplete() {
		return currentStage == getNumStages();
	}

	public abstract boolean canStart();

	public abstract String[] getRequirements();

	public abstract int getNumStages();

	public abstract ItemStack getIcon();

	public abstract String getObjectiveByStage(int i);

	public void progress(int stage) {
		if (stage != currentStage + 1) {
			return;
		}
		if (currentStage > 0) {
			for (int i = 0; i < currentStage; i++) {
				if (stagesCompleted[i] == 0) {
					return;
				}
			}
		}
		stagesCompleted[currentStage] = 1;

		byte[] data = new byte[2];
		data[0] = (byte) questIndex;
		data[1] = (byte) currentStage;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.questDoStage", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		LKLevelData.needsSave = true;
	}

	public int getQuestStage() {
		return currentStage;
	}

	public boolean isDelayed() {
		return stagesDelayed == 1;
	}

	public void setDelayed(boolean flag) {
		stagesDelayed = flag ? 1 : 0;
		byte[] data = new byte[2];
		data[0] = (byte) questIndex;
		data[1] = (byte) stagesDelayed;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.questDelay", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		LKLevelData.needsSave = true;
	}

	public boolean isStageComplete(int i) {
		return stagesCompleted[i] == 1;
	}

	public boolean isChecked() {
		return checked == 1;
	}

	public void setChecked(boolean flag) {
		checked = flag ? 1 : 0;
		byte[] data = new byte[2];
		data[0] = (byte) questIndex;
		data[1] = (byte) checked;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.questCheck", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		LKLevelData.needsSave = true;
	}

	public void resetProgress() {
		currentStage = 0;
		stagesDelayed = 0;
		Arrays.fill(stagesCompleted, 0);
		checked = 0;
	}
}
