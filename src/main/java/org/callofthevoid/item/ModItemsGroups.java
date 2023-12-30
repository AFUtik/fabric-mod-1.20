package org.callofthevoid.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;

public class ModItemsGroups {
    public static final RegistryKey<ItemGroup> NEW_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(CallOfTheVoid.MOD_ID, "new_group"));
    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, NEW_GROUP, FabricItemGroup.builder()
                       .icon(() -> new ItemStack(Items.DIAMOND_PICKAXE))
                       .displayName(Text.translatable("Call of The void"))
                       .build()); // build() no longer registers by itself
    }
}
