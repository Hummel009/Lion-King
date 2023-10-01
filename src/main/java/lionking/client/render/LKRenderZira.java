package lionking.client.render;

import lionking.client.LKTickHandlerClient;
import lionking.client.model.LKModelLion;
import lionking.common.LKLevelData;
import lionking.common.entity.LKEntityZira;
import lionking.common.quest.LKQuestBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class LKRenderZira extends RenderLiving {
	private static ResourceLocation texture = new ResourceLocation("lionking:mob/zira.png");

	public LKRenderZira() {
		super(new LKModelLion(true), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	public void doRenderLiving(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		super.doRenderLiving(entity, d, d1, d2, f, f1);
		if (renderName()) {
			renderLivingLabel(entity, "Zira", d, d1, d2, 64);
		}

		if (LKLevelData.ziraStage > 25) {
			LKTickHandlerClient.ziraBoss = (LKEntityZira) entity;
		}
	}

	private boolean renderName() {
		EntityLivingBase player = renderManager.livingPlayer;
		if (player instanceof EntityPlayer && ((EntityPlayer) player).capabilities.isCreativeMode) {
			return true;
		} else if (LKQuestBase.outlandsQuest.getQuestStage() > 1) {
			return true;
		} else return LKQuestBase.outlandsQuest.getQuestStage() == 1 && !LKQuestBase.outlandsQuest.isDelayed();
	}
}
