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
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.callofthevoid.item.inventory.ImplementedInventory;
import org.callofthevoid.network.ModMessages;
import org.callofthevoid.util.FluidStack;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class BaseBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
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

    protected static void transferFluidToFluidStorage(SingleVariantStorage<FluidVariant> fluidStorage, FlowableFluid fluid, long droplets) {
        try(Transaction transaction = Transaction.openOuter()) {
            fluidStorage.insert(FluidVariant.of(fluid),
                    FluidStack.convertDropletsToMb(droplets), transaction);
            transaction.commit();
        }
    }

    protected static void transferEnergyToEnergyStorage(SimpleEnergyStorage energyStorage, long maxAmount) {
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.insert(maxAmount, transaction);
            transaction.commit();
        }
    }

    protected static void extractFluid(SingleVariantStorage<FluidVariant> fluidStorage, FlowableFluid fluid, long maxAmount) {
        try(Transaction transaction = Transaction.openOuter()) {
            fluidStorage.extract(FluidVariant.of(fluid),
                    maxAmount, transaction);
            transaction.commit();
        }
    }

    protected static void extractEnergy(SimpleEnergyStorage energyStorage, long maxAmount) {
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.extract(maxAmount, transaction);
            transaction.commit();
        }
    }

    protected static boolean hasEnoughFluid(SingleVariantStorage<FluidVariant> fluidStorage, long amount) {
        return fluidStorage.amount >= amount; // mB amount!
    }

    public void setFluidLevel(FluidVariant fluidVariant, long fluidLevel) {}

    public void setEnergyLevel(long energyLevel) {}

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
