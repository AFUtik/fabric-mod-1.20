package org.callofthevoid.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<ExtractorBlockEntity> EXTRACTOR_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        EXTRACTOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(CallOfTheVoid.MOD_ID, "extractor"),
                FabricBlockEntityTypeBuilder.create(ExtractorBlockEntity::new,
                        ModBlocks.EXTRACTOR_BLOCK).build(null));
    }
}
