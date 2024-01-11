package org.callofthevoid.blockentity.machines;

import org.callofthevoid.block.BaseCustomBlock;
import org.callofthevoid.fluid.ModFluids;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.callofthevoid.blockentity.BaseBlockEntity;
import org.callofthevoid.blockentity.ModBlockEntities;
import org.callofthevoid.util.FluidStack;
import org.callofthevoid.util.SimplePropertyDelegate;
import org.jetbrains.annotations.Nullable;
import org.callofthevoid.item.ModItems;
import org.callofthevoid.screen.ExtractorScreenHandler;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class ExtractorBlockEntity extends BaseBlockEntity implements BlockEntityTicker<ExtractorBlockEntity> {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int[] INPUT_SLOT = {0, 1};
    private static final int OUTPUT_SLOT = 2;

    protected final SimplePropertyDelegate propertyDelegate;
    public final SimpleEnergyStorage energyStorage;
    public final SingleVariantStorage<FluidVariant> fluidStorage;

    public ExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTRACTOR_BLOCK_ENTITY, pos, state);
        this.energyStorage = createSimpleEnergyStorage(30000, 42, 32);
        this.fluidStorage = createSimpleFluidStorage(FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 5);
        this.propertyDelegate = new SimplePropertyDelegate(72, 3);
    }

    private static boolean hasFluidSourceInSlot(ExtractorBlockEntity entity) {
        return entity.getStack(0).getItem() == ModFluids.SOAP_WATER_BUCKET;
    }

    private static boolean hasEnoughEnergy(ExtractorBlockEntity entity) {
        return entity.energyStorage.amount >= 32;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Extractor");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        propertyDelegate.writeNbt("extractor.progress", nbt);

        nbt.put("extractor.variant", fluidStorage.variant.toNbt());
        nbt.putLong("extractor.fluid", fluidStorage.amount);

        nbt.putLong("extractor.energy", energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        propertyDelegate.readNbt("extractor.progress", nbt);

        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("extractor.variant"));
        fluidStorage.amount = nbt.getLong("extractor.fluid");

        energyStorage.amount = nbt.getLong("extractor.energy");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        super.createMenu(syncId, playerInventory, player);

        sendEnergyPacket(energyStorage);
        sendFluidPacket(fluidStorage);

        return new ExtractorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void setFluidLevel(FluidVariant fluidVariant, long fluidLevel) {
        this.fluidStorage.variant = fluidVariant;
        this.fluidStorage.amount = fluidLevel;
    }

    @Override
    public void setEnergyLevel(long energyLevel) {
        this.energyStorage.amount = energyLevel;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT[0], 1);
        this.removeStack(INPUT_SLOT[1], 1);
        ItemStack result = new ItemStack(Items.DIAMOND);

        this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        ItemStack result = new ItemStack(Items.DIAMOND);
        boolean hasInput = getStack(INPUT_SLOT[0]).getItem() == ModItems.CRUDE_GRAPHITE && getStack(INPUT_SLOT[1]).getItem() == ModItems.CRUDE_GRAPHITE;

        return hasInput && canInsertAmountIntoOutputSlot(OUTPUT_SLOT, result) && canInsertItemIntoOutputSlot(OUTPUT_SLOT, result.getItem());
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, ExtractorBlockEntity blockEntity) {
        if(world.isClient()) {
            return;
        }

        if(this.hasRecipe() && hasEnoughEnergy(blockEntity) && isOutputSlotEmptyOrReceivable(OUTPUT_SLOT)) {
            updateState(true);
            propertyDelegate.increaseCraftProgress();
            markDirty(world, pos, state);
            if(propertyDelegate.hasCraftingFinished()) {
                extractEnergy(energyStorage, 32);
                this.craftItem();
                propertyDelegate.resetProgress();
                transferFluidToFluidStorage(fluidStorage, ModFluids.STILL_GRAPHITE_OIL, FluidConstants.BUCKET);
            }
        } else {
            propertyDelegate.resetProgress();
            updateState(false);
            markDirty(world, pos, state);
        }
    }
}
