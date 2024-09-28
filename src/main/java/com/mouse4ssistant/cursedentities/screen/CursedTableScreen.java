package com.mouse4ssistant.cursedentities.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class CursedTableScreen extends AbstractContainerScreen<CursedTableMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CursedEntitiesMod.MODID, "textures/gui/cursed_table_gui.png");

    public CursedTableScreen(CursedTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY= 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 202, 167);

        renderProgress(guiGraphics, x, y);
    }

    private void renderProgress(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
           guiGraphics.blit(TEXTURE, x + 75, y + 32, 176, 0, 26, menu.getScaledProgress(), 202, 167);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics,mouseX,mouseY,delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

}
