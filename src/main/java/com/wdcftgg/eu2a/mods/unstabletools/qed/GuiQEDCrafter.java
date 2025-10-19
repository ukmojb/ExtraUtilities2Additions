package com.wdcftgg.eu2a.mods.unstabletools.qed;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityQED;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiQEDCrafter extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ExtraUtilities2Additions.MODID, "textures/gui/qed.png");
    private static final ResourceLocation ARROW = new ResourceLocation(ExtraUtilities2Additions.MODID, "textures/gui/arrow.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityQED tile;

    public GuiQEDCrafter(InventoryPlayer playerInv, TileEntityQED tile) {
        super(new ContainerQEDCrafter(playerInv, tile));
        this.playerInventory = playerInv;
        this.tile = tile;

    }

    @Override
    public void initGui() {
        super.initGui();

        ScaledResolution resolution = new ScaledResolution(mc);

        this.width = resolution.getScaledWidth();
        this.height = resolution.getScaledHeight();

        this.xSize = (int) (this.width * 0.75);
        this.ySize = (int) (this.height * 0.75);

        this.guiLeft = (this.width - 176) / 2;
        this.guiTop = (int) ((this.height - 166) / 2 + this.width * 0.02);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString("QED", 8, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        ScaledResolution resolution = new ScaledResolution(mc);

        this.width = resolution.getScaledWidth();
        this.height = resolution.getScaledHeight();

        this.xSize = (int) 176;
        this.ySize = (int) 166;

        this.mc.getTextureManager().bindTexture(TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, 176, 166, 176, 166);


        int progress = tile.getProgress();
        int max = tile.getMaxProgress();
        if (max > 0 && progress > 0) {
            int barWidth = (int)(22.0F * progress / max);
            GlStateManager.doPolygonOffset(-1, -0);

            this.mc.getTextureManager().bindTexture(ARROW);
            Gui.drawModalRectWithCustomSizedTexture((int) (guiLeft + xSize * 91 / 176), guiTop + ySize * 35 / 166, 0, 0, barWidth, 15, 22, 15);
//            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, barWidth, 15);
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.pushAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();


//        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166);


        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

    }
}
