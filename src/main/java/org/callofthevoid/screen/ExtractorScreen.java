package org.callofthevoid.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.screen.renderer.ButtonRenderer;
import org.callofthevoid.screen.renderer.EnergyInfoArea;
import org.callofthevoid.screen.renderer.FluidStackRenderer;
import org.callofthevoid.util.FluidStack;

public class ExtractorScreen extends HandledScreen<ExtractorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(CallOfTheVoid.MOD_ID, "textures/gui/gui-extractor.png");

    EnergyInfoArea energyInfoArea;
    FluidStackRenderer fluidStackRenderer;
    ButtonRenderer buttonRenderer;

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
        assingButtonManager();
    }

    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer(FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 5,
                true, 12, 50, 20, 15, Formatting.GOLD);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2),
                ((height - backgroundHeight) / 2 ),151, 17, handler.blockEntity.energyStorage, 11, 43);
    }

    private void assingButtonManager() {
        this.buttonRenderer = new ButtonRenderer((width - backgroundWidth) / 2, (height - backgroundHeight) / 2,
                0, 2, 19, 24, 2);

        this.buttonRenderer.addDefaultButtons();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        buttonRenderer.mouseClick(mouseX, mouseY);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        handler.renderProgressArrow(TEXTURE, context, x, y, handler.propertyDelegate, 87, 33, 176, 0);

        energyInfoArea.draw(context);
        fluidStackRenderer.drawFluid(context, handler.fluidStack,0, 0, x, y, fluidStackRenderer.capacityMb);

        context.drawTexture(TEXTURE, x + fluidStackRenderer.getOffsetX(), y + fluidStackRenderer.getOffsetY(), 180, 17, fluidStackRenderer.getWidth(), fluidStackRenderer.getHeight());

        buttonRenderer.draw(context);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        handler.renderFluidTooltip(context, mouseX, mouseY, x, y, handler.fluidStack, fluidStackRenderer);
        handler.renderEnergyAreaTooltip(context, mouseX, mouseY, x, y, this.energyInfoArea);
        handler.renderProgressArrowTooltip(context, mouseX, mouseY, x, y, handler.propertyDelegate, 87, 33, 22, 15);

        buttonRenderer.renderTooltip(context, client.textRenderer, mouseX, mouseY);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
