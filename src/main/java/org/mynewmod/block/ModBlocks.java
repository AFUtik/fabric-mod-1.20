package org.mynewmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.mynewmod.MyNewMod;
import org.mynewmod.block.custom.ColoredBlock;
import org.mynewmod.item.ModItems;
import org.mynewmod.item.ModItemsGroups;

public class ModBlocks  {
    public static Block NEW_BLOCK = registerBlock("new_block", new Block(FabricBlockSettings.create().nonOpaque().strength(4.0f)), ModItemsGroups.NEW_GROUP);

    public static Block COLORED_BLOCK = registerBlock("colored_block", new ColoredBlock(FabricBlockSettings.create().strength(4.0f)), ModItemsGroups.NEW_GROUP);

    private static Block registerBlock(String id, Block block) {
        ModItems.registerItem(id, new BlockItem(block, new FabricItemSettings()));

        return Registry.register(Registries.BLOCK, new Identifier(MyNewMod.MODID, id), block);
    }
    private static Block registerBlock(String id, Block block, RegistryKey<ItemGroup> itemGroup) {
        ModItems.registerItem(id, new BlockItem(block, new FabricItemSettings()), itemGroup);

        return Registry.register(Registries.BLOCK, new Identifier(MyNewMod.MODID, id), block);
    }

    public static void register() {
        MyNewMod.LOGGER.debug("registering blocks for " + MyNewMod.MODID);
    }
}
