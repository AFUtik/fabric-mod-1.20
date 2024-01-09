package org.callofthevoid.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import org.callofthevoid.blockentity.machines.ExtractorBlockEntity;
import org.callofthevoid.util.FluidStack;

public class ExtractorScreenHandler extends BaseScreenHandler {
    private final PropertyDelegate propertyDelegate;
    public FluidStack fluidStack;
    public final ExtractorBlockEntity blockEntity;

    public ExtractorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(2));
    }

    public ExtractorScreenHandler(int syncId, PlayerInventory playerInventory,
                                  BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.EXTRACTOR_SCREEN_HANDLER, syncId, playerInventory, blockEntity);
        checkSize(((Inventory) blockEntity), 2);
        this.propertyDelegate = arrayPropertyDelegate;
        this.fluidStack = new FluidStack(((ExtractorBlockEntity) blockEntity).fluidStorage.variant, ((ExtractorBlockEntity) blockEntity).fluidStorage.amount);
        this.blockEntity = (ExtractorBlockEntity) blockEntity;

        addSlot(0, 66, 16);
        addSlot(1, 66, 50);
        addOutputSlot(2, 115, 33);

        addProperties(arrayPropertyDelegate);
    }

    @Override
    public void setFluid(FluidStack stack) {
        fluidStack = stack;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress
        int progressArrowSize = 22; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

}
