package org.callofthevoid.blockentity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
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
import org.callofthevoid.blockentity.machines.ExtractorBlockEntity;
import org.callofthevoid.fluid.ModFluids;
import org.callofthevoid.item.inventory.ImplementedInventory;
import org.callofthevoid.network.ModMessages;
import org.callofthevoid.util.FluidStack;
import org.jetbrains.annotations.Nullable;

public class BaseBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public final SingleVariantStorage<FluidVariant> fluidStorage;
    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long capacityFluidStorage) {
        super(type, pos, state);
        this.fluidStorage = new SingleVariantStorage<FluidVariant>() {
            @Override
            protected FluidVariant getBlankVariant() {
                return FluidVariant.blank();
            }

            @Override
            protected long getCapacity(FluidVariant variant) {
                return capacityFluidStorage;
            }

            @Override
            protected void onFinalCommit() {
                markDirty();
                if(!world.isClient()) {
                    sendFluidPacket();
                }
            }
        };;
    }

    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidStorage = null;
    }

    private void sendFluidPacket() {
        PacketByteBuf data = PacketByteBufs.create();
        fluidStorage.variant.toPacket(data);
        data.writeLong(fluidStorage.amount);
        data.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data);
        }
    }

    protected static void transferFluidToFluidStorage(BaseBlockEntity entity, FlowableFluid fluid, long droplets) {
        try(Transaction transaction = Transaction.openOuter()) {
            entity.fluidStorage.insert(FluidVariant.of(fluid),
                    FluidStack.convertDropletsToMb(droplets), transaction);
            transaction.commit();
        }
    }

    public void setFluidLevel(FluidVariant fluidVariant, long fluidLevel) {
        this.fluidStorage.variant = fluidVariant;
        this.fluidStorage.amount = fluidLevel;
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
        if (fluidStorage != null) {
            sendFluidPacket();
        }
        return null;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }


}
