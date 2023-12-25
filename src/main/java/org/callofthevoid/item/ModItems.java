package org.callofthevoid.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.callofthevoid.CallOfTheVoid;

public class ModItems {
    public static final Item CRUDE_ORE_ITEM = registerItem("crude_ore_item", new Item(new FabricItemSettings().group(ModItemsGroups.NEW_GROUP)));

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(CallOfTheVoid.MOD_ID, id), item);
    }

    public static void register() {
        CallOfTheVoid.LOGGER.debug("registering items for " + CallOfTheVoid.MOD_ID);
    }
}
