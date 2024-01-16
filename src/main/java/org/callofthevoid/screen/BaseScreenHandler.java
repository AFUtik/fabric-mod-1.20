package org.callofthevoid.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.callofthevoid.blockentity.BaseBlockEntity;
import org.callofthevoid.screen.renderer.EnergyInfoArea;
import org.callofthevoid.screen.renderer.FluidStackRenderer;
import org.callofthevoid.screen.slot.OutputSlot;
import org.callofthevoid.util.FluidStack;
import org.callofthevoid.util.MouseUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final BaseBlockEntity blockEntity;

    protected BaseScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId,PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(type, syncId);
        this.inventory = ((Inventory) blockEntity);
        this.blockEntity = ((BaseBlockEntity) blockEntity);
        inventory.onOpen(playerInventory.player);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public void addSlot(int index, int x, int y) {
        this.addSlot(new Slot(inventory, index, x, y));
    }

    public void addOutputSlot(int index, int x, int y) {
        this.addSlot(new OutputSlot(inventory, index, x, y));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public void setFluid(FluidStack stack) {}

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    protected void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y,
                                    FluidStack fluidStack, FluidStackRenderer renderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, renderer.getOffsetX(), renderer.getOffsetY(), renderer)) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, renderer.getTooltip(fluidStack, TooltipContext.Default.BASIC),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    protected void renderProgressArrowTooltip(DrawContext context, int pMouseX, int pMouseY, int x, int y,
                                              PropertyDelegate propertyDelegate, int offsetX, int offsetY,
                                              int width, int height) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, width, height)) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, this.getPercent(propertyDelegate),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    protected void renderEnergyAreaTooltip(DrawContext context, int pMouseX, int pMouseY, int x, int y,
                                           EnergyInfoArea energyInfoArea) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, energyInfoArea.getOffsetX(), energyInfoArea.getOffsetY(), energyInfoArea.getWidth(), energyInfoArea.getHeight())) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private int getScaledProgress(PropertyDelegate propertyDelegate, int progressArrowSize) {
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1);  // Max Progress

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    protected void renderProgressArrow(Identifier texture, DrawContext context, int x, int y,
                                     PropertyDelegate propertyDelegate,int offsetX, int offsetY, int u, int v) {
        if(this.isCrafting(propertyDelegate)) { // 176
            context.drawTexture(texture, x + offsetX, y + offsetY, u, v, this.getScaledProgress(propertyDelegate, 22), 15);
        }
    }
    private List<Text> getPercent(PropertyDelegate propertyDelegate) {
        List<Text> tooltip = new ArrayList<>();
        MutableText text = Text.literal((int) ((float) propertyDelegate.get(0) / propertyDelegate.get(1) * 100.0f) + "%");
        text.setStyle(Style.EMPTY.withColor(Formatting.YELLOW));

        tooltip.add(text);

        return tooltip;
    }

    private boolean isCrafting(PropertyDelegate propertyDelegate) {
        return propertyDelegate.get(0) > 0;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
