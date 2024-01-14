package org.callofthevoid.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.item.custom.CustomPocketWorkbench;

public class ModItems {
    public static final Item CRUDE_GRAPHITE = registerItem("crude_graphite", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item POCKET_WORKBENCH = registerItem("pocket_workbench", new CustomPocketWorkbench(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);

    public static final Item IRON_WIRE = registerItem("iron_wire", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item COPPER_WIRE = registerItem("copper_wire", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item IRON_PLATE = registerItem("iron_plate", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item COPPER_PLATE = registerItem("copper_plate", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item LATEX_BALL = registerItem("latex_ball", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item SCHEME = registerItem("scheme", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item MICROCIRCUIT = registerItem("microcircuit", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item BATTERY = registerItem("battery", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item WRENCH = registerItem("wrench", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item SENSOR = registerItem("sensor", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item BULB = registerItem("bulb", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);
    public static final Item SAWDUST = registerItem("sawdust", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CallOfTheVoid.MOD_ID, id), item);
    }

    public static Item registerItem(String id, Item item, RegistryKey<ItemGroup> itemGroup) {
        Item returnItem = Registry.register(Registries.ITEM, new Identifier(CallOfTheVoid.MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> {entries.add(item);});
        return returnItem;
    }

    public static void register() {
        CallOfTheVoid.LOGGER.debug("registering items for " + CallOfTheVoid.MOD_ID);
    }
}
