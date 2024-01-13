package org.callofthevoid.screen;

import net.minecraft.block.entity.BlockEntity;
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
import org.callofthevoid.blockentity.BaseBlockEntity;
import org.callofthevoid.screen.slot.OutputSlot;
import org.callofthevoid.util.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public void setFluid(FluidStack stack) {

    }

    public int getScaledProgress(PropertyDelegate propertyDelegate, int progressArrowSize) {
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1);  // Max Progress

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public List<Text> getPercent(PropertyDelegate propertyDelegate) {
        List<Text> tooltip = new ArrayList<>();
        MutableText text = Text.literal((int) ((float) propertyDelegate.get(0) / propertyDelegate.get(1) * 100.0f) + "%");
        text.setStyle(Style.EMPTY.withColor(Formatting.YELLOW));

        tooltip.add(text);

        return tooltip;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
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
}
