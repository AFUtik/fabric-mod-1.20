package org.callofthevoid.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;

public class ModItemsGroups {
    public static final ItemGroup NEW_GROUP = FabricItemGroupBuilder.build(
            new Identifier(CallOfTheVoid.MOD_ID, "new_group"),
            () -> new ItemStack(Items.DIAMOND));

}
