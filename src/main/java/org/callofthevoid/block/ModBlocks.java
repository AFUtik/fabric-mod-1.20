package org.callofthevoid.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.item.ModItems;
import org.callofthevoid.item.ModItemsGroups;

public class ModBlocks {
    public static Block GRAFITE_ORE_BLOCK = registerBlock("grafite_ore_block", new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f)), ModItemsGroups.NEW_GROUP);

    private static Block registerBlock(String id, Block block) {
        ModItems.registerItem(id, new BlockItem(block, new FabricItemSettings()));

        return Registry.register(Registry.BLOCK, new Identifier(CallOfTheVoid.MOD_ID, id), block);
    }
    private static Block registerBlock(String id, Block block, ItemGroup itemGroup) {
        ModItems.registerItem(id, new BlockItem(block, new FabricItemSettings().group(itemGroup)));

        return Registry.register(Registry.BLOCK, new Identifier(CallOfTheVoid.MOD_ID, id), block);
    }

    public static void register() {
        CallOfTheVoid.LOGGER.debug("registering blocks for " + CallOfTheVoid.MOD_ID);
    }
}
