package org.callofthevoid.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.screen.renderer.EnergyInfoArea;
import org.callofthevoid.screen.renderer.FluidStackRenderer;
import org.callofthevoid.util.FluidStack;
import org.callofthevoid.util.MouseUtil;

import java.util.Optional;

public class ExtractorScreen extends HandledScreen<ExtractorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(CallOfTheVoid.MOD_ID, "textures/gui/gui-extractor.png");
    private static final Identifier fluidTex = new Identifier(CallOfTheVoid.MOD_ID, "textures/fluids/fluids.png");

    EnergyInfoArea energyInfoArea;
    FluidStackRenderer fluidStackRenderer;

    public ExtractorScreen(ExtractorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;

        assignEnergyInfoArea();
        assignFluidStackRenderer();
    }

    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer(FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 5,
                true, 12, 50);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2) + 161,
                ((height - backgroundHeight) / 2 ) + 8, handler.blockEntity.energyStorage, 8, 69);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(context, x, y);

        energyInfoArea.draw(context);
        fluidStackRenderer.drawFluid(fluidTex, context, handler.fluidStack,0, 0, x + 20, y + 17, fluidStackRenderer.getWidth(), fluidStackRenderer.getHeight(),
                fluidStackRenderer.capacityMb);

        context.drawTexture(TEXTURE, x + 20, y + 17, 180, 17, fluidStackRenderer.getWidth(), fluidStackRenderer.getHeight());
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderFluidTooltip(context, mouseX, mouseY, x, y, handler.fluidStack, 20, 17, fluidStackRenderer);
        renderEnergyAreaTooltips(context, mouseX, mouseY, x, y);
        renderProgressArrowTooltips(context, mouseX, mouseY, x, y);
    }

    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y,
                                    FluidStack fluidStack, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, renderer.getTooltip(fluidStack, TooltipContext.Default.BASIC),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private void renderEnergyAreaTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 161, 8, 8, 69)) {
            context.drawTooltip(client.textRenderer, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderProgressArrowTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 87, 33, 22, 15)) {
            context.drawTooltip(client.textRenderer, handler.getPercent(handler.propertyDelegate),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 87, y + 33, 176, 0, handler.getScaledProgress(handler.propertyDelegate, 22), 15);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
