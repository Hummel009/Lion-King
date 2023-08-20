package lionking.client.gui;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.AchievementPage;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LKGuiAchievements extends GuiScreen {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/achievements.png");

	private static final int guiMapTop = LKAchievementList.minDisplayColumn * 24 - 112;
	private static final int guiMapLeft = LKAchievementList.minDisplayRow * 24 - 112;
	private static final int guiMapBottom = LKAchievementList.maxDisplayColumn * 24 - 77;
	private static final int guiMapRight = LKAchievementList.maxDisplayRow * 24 - 77;
	public int currentPage;
	protected int achievementsPaneWidth = 256;
	protected int achievementsPaneHeight = 202;
	protected int mouseX;
	protected int mouseY;
	protected double field_27116_m;
	protected double field_27115_n;
	protected double guiMapX;
	protected double guiMapY;
	protected double field_27112_q;
	protected double field_27111_r;
	private int isMouseButtonDown;
	private StatFileWriter statFileWriter;
	private GuiSmallButton button;
	private LinkedList<Achievement> minecraftAchievements = new LinkedList<Achievement>();

	public LKGuiAchievements(StatFileWriter par1StatFileWriter, int page) {
		statFileWriter = par1StatFileWriter;
		currentPage = page;
		field_27116_m = guiMapX = field_27112_q = LKAchievementList.enterPrideLands.displayColumn * 24 - (double) 141 / 2 - 12;
		field_27115_n = guiMapY = field_27111_r = LKAchievementList.enterPrideLands.displayRow * 24 - (double) 141 / 2;
		for (Object achievement : AchievementList.achievementList) {
			if (!AchievementPage.isAchievementInPages((Achievement) achievement)) {
				minecraftAchievements.add((Achievement) achievement);
			}
		}
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiSmallButton(1, width / 2 + 24, height / 2 + 74, 80, 20, StatCollector.translateToLocal("gui.done")));
		button = new GuiSmallButton(2, (width - achievementsPaneWidth) / 2 + 24, height / 2 + 74, 125, 20, AchievementPage.getTitle(currentPage));
		buttonList.add(button);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 1) {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		}

		if (par1GuiButton.id == 2) {
			currentPage++;
			if (currentPage >= AchievementPage.getAchievementPages().size()) {
				currentPage = -1;
			}
			button.displayString = AchievementPage.getTitle(currentPage);
		}

		super.actionPerformed(par1GuiButton);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == mc.gameSettings.keyBindInventory.keyCode) {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} else {
			super.keyTyped(par1, par2);
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if (Mouse.isButtonDown(0)) {
			int var4 = (width - achievementsPaneWidth) / 2;
			int var5 = (height - achievementsPaneHeight) / 2;
			int var6 = var4 + 8;
			int var7 = var5 + 17;

			if ((isMouseButtonDown == 0 || isMouseButtonDown == 1) && par1 >= var6 && par1 < var6 + 224 && par2 >= var7 && par2 < var7 + 155) {
				if (isMouseButtonDown == 0) {
					isMouseButtonDown = 1;
				} else {
					guiMapX -= (par1 - mouseX);
					guiMapY -= (par2 - mouseY);
					field_27112_q = field_27116_m = guiMapX;
					field_27111_r = field_27115_n = guiMapY;
				}

				mouseX = par1;
				mouseY = par2;
			}

			if (field_27112_q < guiMapTop) {
				field_27112_q = guiMapTop;
			}

			if (field_27111_r < guiMapLeft) {
				field_27111_r = guiMapLeft;
			}

			if (field_27112_q >= guiMapBottom) {
				field_27112_q = (guiMapBottom - 1);
			}

			if (field_27111_r >= guiMapRight) {
				field_27111_r = (guiMapRight - 1);
			}
		} else {
			isMouseButtonDown = 0;
		}

		drawDefaultBackground();
		genAchievementBackground(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		func_27110_k();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	public void updateScreen() {
		field_27116_m = guiMapX;
		field_27115_n = guiMapY;
		double var1 = field_27112_q - guiMapX;
		double var3 = field_27111_r - guiMapY;

		if (var1 * var1 + var3 * var3 < 4.0D) {
			guiMapX += var1;
			guiMapY += var3;
		} else {
			guiMapX += var1 * 0.85D;
			guiMapY += var3 * 0.85D;
		}
	}

	protected void func_27110_k() {
		int var1 = (width - achievementsPaneWidth) / 2;
		int var2 = (height - achievementsPaneHeight) / 2;
		fontRenderer.drawString("Achievements", var1 + 15, var2 + 5, 0);
	}

	protected void genAchievementBackground(int par1, int par2, float par3) {
		int var4 = MathHelper.floor_double(field_27116_m + (guiMapX - field_27116_m) * par3);
		int var5 = MathHelper.floor_double(field_27115_n + (guiMapY - field_27115_n) * par3);

		if (var4 < guiMapTop) {
			var4 = guiMapTop;
		}

		if (var5 < guiMapLeft) {
			var5 = guiMapLeft;
		}

		if (var4 >= guiMapBottom) {
			var4 = guiMapBottom - 1;
		}

		if (var5 >= guiMapRight) {
			var5 = guiMapRight - 1;
		}

		int var8 = (width - achievementsPaneWidth) / 2;
		int var9 = (height - achievementsPaneHeight) / 2;
		int var10 = var8 + 16;
		int var11 = var9 + 17;
		zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int var12 = var4 + 288 >> 4;
		int var13 = var5 + 288 >> 4;
		int var14 = (var4 + 288) % 16;
		int var15 = (var5 + 288) % 16;
		Random var21 = new Random();
		int var22;
		int var25;
		int var24;
		int var26;

		for (var22 = 0; var22 * 16 - var15 < 155; ++var22) {
			float var23 = 0.6F - (var13 + var22) / 26.0F * 0.3F;
			GL11.glColor4f(var23, var23, var23, 1.0F);

			for (var24 = 0; var24 * 16 - var14 < 224; ++var24) {
				var21.setSeed((1234 + var12 + var24));
				var21.nextInt();
				var25 = var21.nextInt(1 + var13 + var22) + (var13 + var22) / 2;
				Icon icon = LKMod.pridestone.getIcon(2, 0);

				if (var25 <= 37 && var13 + var22 != 35) {
					if (var25 == 34) {
						icon = LKMod.orePeacock.getIcon(2, 0);
					} else if (var25 == 22) {
						icon = LKMod.oreSilver.getIcon(2, 0);
					} else if (var25 == 10) {
						icon = LKMod.prideCoal.getIcon(2, 0);
					}
				}

				if ((var13 + var22 == 31 && (var12 + var24 < 10 || var12 + var24 > 27)) || (var13 + var22 == 32 && (var12 + var24 < 15 || var12 + var24 > 22)) || (var13 + var22 > 32)) {
					icon = LKMod.pridestone.getIcon(2, 1);
					if (var25 == 30) {
						icon = LKMod.prideCoal.getIcon(2, 1);
					} else if (var25 == 34) {
						icon = LKMod.oreSilver.getIcon(2, 1);
					}
				}

				mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
				drawTexturedModelRectFromIcon(var10 + var24 * 16 - var14, var11 + var22 * 16 - var15, icon, 16, 16);
			}
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int var27;
		int var30;

		List<Achievement> achievementList = (currentPage == -1 ? minecraftAchievements : AchievementPage.getAchievementPage(currentPage).getAchievements());
		for (var22 = 0; var22 < achievementList.size(); ++var22) {
			Achievement var33 = achievementList.get(var22);

			if (var33.parentAchievement != null && achievementList.contains(var33.parentAchievement)) {
				var24 = var33.displayColumn * 24 - var4 + 11 + var10;
				var25 = var33.displayRow * 24 - var5 + 11 + var11;
				var26 = var33.parentAchievement.displayColumn * 24 - var4 + 11 + var10;
				var27 = var33.parentAchievement.displayRow * 24 - var5 + 11 + var11;
				boolean var28 = statFileWriter.hasAchievementUnlocked(var33);
				boolean var29 = statFileWriter.canUnlockAchievement(var33);
				var30 = Math.sin((System.currentTimeMillis() % 600L) / 600.0D * Math.PI * 2.0D) > 0.6D ? 255 : 130;
				int var31 = -16777216;

				if (var28) {
					var31 = -9408400;
				} else if (var29) {
					var31 = 65280 + (var30 << 24);
				}

				drawHorizontalLine(var24, var26, var25, var31);
				drawVerticalLine(var26, var25, var27, var31);
			}
		}

		Achievement var32 = null;
		RenderItem var37 = new RenderItem();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int var42;
		int var41;

		for (var24 = 0; var24 < achievementList.size(); ++var24) {
			Achievement var35 = achievementList.get(var24);
			var26 = var35.displayColumn * 24 - var4;
			var27 = var35.displayRow * 24 - var5;

			if (var26 >= -24 && var27 >= -24 && var26 <= 224 && var27 <= 155) {
				float var38;

				if (statFileWriter.hasAchievementUnlocked(var35)) {
					var38 = 1.0F;
					GL11.glColor4f(var38, var38, var38, 1.0F);
				} else if (statFileWriter.canUnlockAchievement(var35)) {
					var38 = Math.sin((System.currentTimeMillis() % 600L) / 600.0D * Math.PI * 2.0D) < 0.6D ? 0.6F : 0.8F;
					GL11.glColor4f(var38, var38, var38, 1.0F);
				} else {
					var38 = 0.3F;
					GL11.glColor4f(var38, var38, var38, 1.0F);
				}

				mc.getTextureManager().bindTexture(texture);
				var42 = var10 + var26;
				var41 = var11 + var27;

				if (var35.getSpecial()) {
					drawTexturedModalRect(var42 - 2, var41 - 2, 26, 202, 26, 26);
				} else {
					drawTexturedModalRect(var42 - 2, var41 - 2, 0, 202, 26, 26);
				}

				if (!statFileWriter.canUnlockAchievement(var35)) {
					float var40 = 0.1F;
					GL11.glColor4f(var40, var40, var40, 1.0F);
					var37.renderWithColor = false;
				}

				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_CULL_FACE);
				var37.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, var35.theItemStack, var42 + 3, var41 + 3);
				GL11.glDisable(GL11.GL_LIGHTING);

				if (!statFileWriter.canUnlockAchievement(var35)) {
					var37.renderWithColor = true;
				}

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				if (par1 >= var10 && par2 >= var11 && par1 < var10 + 224 && par2 < var11 + 155 && par1 >= var42 && par1 <= var42 + 22 && par2 >= var41 && par2 <= var41 + 22) {
					var32 = var35;
				}
			}
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(var8, var9, 0, 0, achievementsPaneWidth, achievementsPaneHeight);
		GL11.glPopMatrix();
		zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		super.drawScreen(par1, par2, par3);

		if (var32 != null) {
			String var34 = StatCollector.translateToLocal(var32.getName());
			String var36 = var32.getDescription();
			var26 = par1 + 12;
			var27 = par2 - 4;

			if (statFileWriter.canUnlockAchievement(var32)) {
				var42 = Math.max(fontRenderer.getStringWidth(var34), 123);
				var41 = fontRenderer.splitStringWidth(var36, var42);

				if (statFileWriter.hasAchievementUnlocked(var32)) {
					var41 += 12;
				}

				drawGradientRect(var26 - 3, var27 - 3, var26 + var42 + 3, var27 + var41 + 3 + 12, -1073741824, -1073741824);
				fontRenderer.drawSplitString(var36, var26, var27 + 12, var42, -6250336);

				if (statFileWriter.hasAchievementUnlocked(var32)) {
					fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("achievement.taken"), var26, var27 + var41 + 4, -7302913);
				}
			} else {
				var42 = Math.max(fontRenderer.getStringWidth(var34), 120);
				String var39 = StatCollector.translateToLocalFormatted("achievement.requires", StatCollector.translateToLocal(var32.parentAchievement.getName()));
				var30 = fontRenderer.splitStringWidth(var39, var42);
				drawGradientRect(var26 - 3, var27 - 3, var26 + var42 + 3, var27 + var30 + 12 + 3, -1073741824, -1073741824);
				fontRenderer.drawSplitString(var39, var26, var27 + 12, var42, -9416624);
			}

			fontRenderer.drawStringWithShadow(var34, var26, var27, statFileWriter.canUnlockAchievement(var32) ? (var32.getSpecial() ? -128 : -1) : (var32.getSpecial() ? -8355776 : -8355712));
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.disableStandardItemLighting();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
