package org.mynewmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.mynewmod.MyNewMod;
import org.mynewmod.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<FurnaceBlockEntity> FURNACE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MyNewMod.MODID, "furnace_block"),
                    FabricBlockEntityTypeBuilder.create(FurnaceBlockEntity::new,
                            ModBlocks.FURNACE_BLOCK).build());

    public static void registerBlockEntities() {
        MyNewMod.LOGGER.info("Registering Block Entities for " + MyNewMod.MODID);
    }
}
