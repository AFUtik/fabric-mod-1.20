package org.mynewmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.mynewmod.MyNewMod;

public class ModItems {
    public static final Item NEW_ITEM = registerItem("new_item", new Item(new FabricItemSettings()), ModItemsGroups.NEW_GROUP);

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MyNewMod.MODID, id), item);
    }

    public static Item registerItem(String id, Item item, RegistryKey<ItemGroup> itemGroup) {
        Item returnItem = Registry.register(Registries.ITEM, new Identifier(MyNewMod.MODID, id), item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> {entries.add(item);});
        return returnItem;
    }

    public static void register() {
        MyNewMod.LOGGER.debug("registering items for " + MyNewMod.MODID);
    }
}
