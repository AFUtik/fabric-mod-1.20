package org.callofthevoid.blockentity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.callofthevoid.block.BaseCustomBlock;
import org.callofthevoid.item.inventory.ImplementedInventory;
import org.callofthevoid.network.ModMessages;
import org.callofthevoid.util.FluidStack;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class BaseBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    SingleVariantStorage<FluidVariant> fluidStorage;
    SimpleEnergyStorage energyStorage;

    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected void sendFluidPacket(SingleVariantStorage<FluidVariant> fluidStorage) {
        PacketByteBuf data = PacketByteBufs.create();
        fluidStorage.variant.toPacket(data);
        data.writeLong(fluidStorage.amount);
        data.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data);
        }
    }

    protected void sendEnergyPacket(SimpleEnergyStorage energyStorage) {
        PacketByteBuf data = PacketByteBufs.create();
        data.writeLong(energyStorage.amount);
        data.writeBlockPos(getPos());

        for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.ENERGY_SYNC, data);
        }
    }

    protected void transferFluidToFluidStorage(SingleVariantStorage<FluidVariant> fluidStorage, FlowableFluid fluid, long droplets) {
        try(Transaction transaction = Transaction.openOuter()) {
            fluidStorage.insert(FluidVariant.of(fluid),
                    FluidStack.convertDropletsToMb(droplets), transaction);
            transaction.commit();
        }
    }

    protected void transferEnergyToEnergyStorage(SimpleEnergyStorage energyStorage, long maxAmount) {
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.insert(maxAmount, transaction);
            transaction.commit();
        }
    }

    protected void extractFluid(SingleVariantStorage<FluidVariant> fluidStorage, FlowableFluid fluid, long maxAmount) {
        try(Transaction transaction = Transaction.openOuter()) {
            fluidStorage.extract(FluidVariant.of(fluid),
                    maxAmount, transaction);
            transaction.commit();
        }
    }

    protected void extractEnergy(SimpleEnergyStorage energyStorage, long maxAmount) {
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.extract(maxAmount, transaction);
            transaction.commit();

        }
        sendEnergyPacket(energyStorage);
    }

    protected static boolean hasEnoughFluid(SingleVariantStorage<FluidVariant> fluidStorage, long amount) {
        return fluidStorage.amount >= amount; // mB amount!
    }

    protected SingleVariantStorage<FluidVariant> createSimpleFluidStorage(long capacityFluid) {
         fluidStorage = new SingleVariantStorage<FluidVariant>() {
            @Override
            protected FluidVariant getBlankVariant() {
                return FluidVariant.blank();
            }

            @Override
            protected long getCapacity(FluidVariant variant) {
                return capacityFluid;
            }

            @Override
            protected void onFinalCommit() {
                markDirty();
                if(!world.isClient()) {
                    sendFluidPacket(fluidStorage);
                }
            }
        };
        return fluidStorage;
    }

    protected SimpleEnergyStorage createSimpleEnergyStorage(long capacity, long maxInsert, long maxExtract) {
        energyStorage = new SimpleEnergyStorage(capacity, maxInsert, maxExtract) {
            @Override
            protected void onFinalCommit() {
                markDirty();
                if (!world.isClient()) {
                    sendEnergyPacket(energyStorage);
                }
            }
        };
        return energyStorage;
    }

    public void setFluidLevel(FluidVariant fluidVariant, long fluidLevel) {}

    public void setEnergyLevel(long energyLevel) {}

    public void updateState(boolean active) {
        final BlockState BlockStateContainer = world.getBlockState(pos);
        if (BlockStateContainer.getBlock() instanceof final BaseCustomBlock baseCustomBlock){
            if (BlockStateContainer.get(BaseCustomBlock.ACTIVE) != active)  {
                baseCustomBlock.setActive(active, world, pos);
            }
        }
    }

    public boolean canInsertItemIntoOutputSlot(int output_slot, Item item) {
        return this.getStack(output_slot).getItem() == item || this.getStack(output_slot).isEmpty();
    }

    public boolean canInsertAmountIntoOutputSlot(int output_slot, ItemStack result) {
        return this.getStack(output_slot).getCount() + result.getCount() <= getStack(output_slot).getMaxCount();
    }

    public boolean isOutputSlotEmptyOrReceivable(int output_slot) {
        return this.getStack(output_slot).isEmpty() || this.getStack(output_slot).getCount() < this.getStack(output_slot).getMaxCount();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }
}