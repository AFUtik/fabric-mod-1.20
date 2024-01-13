package org.callofthevoid.events;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.callofthevoid.block.ModBlocks;

public class ItemTooltips {
    public static void register() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            // Check if the item is the one you want to add a tooltip to
            Item item = stack.getItem();
            if (item == ModBlocks.EXTRACTOR_BLOCK.asItem()) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item == ModBlocks.ACCUMULATOR_BLOCK.asItem()) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
        });

    }
}
