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
import org.callofthevoid.block.custom.ExtractorBlock;
import org.callofthevoid.item.ModItems;
import org.callofthevoid.item.ModItemsGroups;

public class ModBlocks  {
    public static Block GRAPHITE_ORE_BLOCK = registerBlock("graphite_ore", new Block(FabricBlockSettings.create().strength(4.0f)), ModItemsGroups.NEW_GROUP);

    public static Block EXTRACTOR_BLOCK = registerBlock("extractor", new ExtractorBlock(FabricBlockSettings.create().strength(4.0f)), ModItemsGroups.NEW_GROUP);

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
