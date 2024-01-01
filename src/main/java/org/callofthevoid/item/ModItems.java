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

public class ModItems {
    public static final Item CRUDE_GRAPHITE = registerItem("crude_graphite", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);



    public static final Item POCKET_WORKBENCH = registerItem("pocket_workbench", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);

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
