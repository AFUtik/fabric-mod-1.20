package org.callofthevoid.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.blockentity.machines.ExtractorBlockEntity;
import org.callofthevoid.item.ModItems;
import org.callofthevoid.item.ModItemsGroups;

public class ModBlocks  {
    public static Block GRAPHITE_ORE_BLOCK = registerBlock("graphite_ore", new Block(FabricBlockSettings.create().strength(4.0f)), ModItemsGroups.NEW_GROUP);
    public static Block EXTRACTOR_BLOCK = registerBlock("extractor", new GenericCustomBlock(FabricBlockSettings.create().strength(4.0f), ExtractorBlockEntity::new), ModItemsGroups.NEW_GROUP);
    public static Block ACCUMULATOR_BLOCK = registerBlock("accumulator", new GenericCustomBlock(FabricBlockSettings.create().strength(4.0f), null), ModItemsGroups.NEW_GROUP);
    public static Block BOILER_BLOCK = registerBlock("boiler", new GenericCustomBlock(FabricBlockSettings.create().strength(4.0f), null), ModItemsGroups.NEW_GROUP);
    public static Block TEMPERATURE_REGULATOR_BLOCK = registerBlock("temperature_regulator", new GenericCustomBlock(FabricBlockSettings.create().strength(4.0f), null), ModItemsGroups.NEW_GROUP);
    public static Block BODY_BLOCK = registerBlock("body", new Block(FabricBlockSettings.create().strength(4.0f)), ModItemsGroups.NEW_GROUP);

    public static Block SAWDUST_BLOCK = registerBlock("sawdust_block", new Block(FabricBlockSettings.create().strength(4.0f)), ModItemsGroups.NEW_GROUP);

    private static Block registerBlock(String id, Block block) {
        ModItems.registerItem(id, new BlockItem(block, new FabricItemSettings()));

        return Registry.register(Registries.BLOCK, new Identifier(CallOfTheVoid.MOD_ID, id), block);
    }
    private static Block registerBlock(String id, Block block, RegistryKey<ItemGroup> itemGroup) {
        ModItems.registerItem(id, new BlockItem(block, new FabricItemSettings()), itemGroup);

        return Registry.register(Registries.BLOCK, new Identifier(CallOfTheVoid.MOD_ID, id), block);
    }

    public static void register() {
        CallOfTheVoid.LOGGER.debug("registering blocks for " + CallOfTheVoid.MOD_ID);
    }
}
