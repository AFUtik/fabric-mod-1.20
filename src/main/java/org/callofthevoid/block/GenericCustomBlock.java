package org.callofthevoid.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiFunction;

public class GenericCustomBlock extends BaseCustomBlock {
    final BiFunction<BlockPos, BlockState, BlockEntity> blockEntityClass;

    public GenericCustomBlock(FabricBlockSettings settings, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityClass) {
        super(settings);
        this.blockEntityClass = blockEntityClass;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (blockEntityClass == null) {
            return null;
        }
        return blockEntityClass.apply(pos, state);
    }
}
