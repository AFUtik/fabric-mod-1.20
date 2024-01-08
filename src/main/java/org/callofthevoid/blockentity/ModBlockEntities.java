package org.callofthevoid.blockentity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.block.ModBlocks;
import org.callofthevoid.blockentity.machines.ExtractorBlockEntity;

import java.util.function.BiFunction;

public class ModBlockEntities {
    public static BlockEntityType<ExtractorBlockEntity> EXTRACTOR_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        EXTRACTOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(CallOfTheVoid.MOD_ID, "extractor"),
                FabricBlockEntityTypeBuilder.create(ExtractorBlockEntity::new,
                        ModBlocks.EXTRACTOR_BLOCK).build(null));

        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, EXTRACTOR_BLOCK_ENTITY);
    }
}
