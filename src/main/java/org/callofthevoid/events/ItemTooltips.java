package org.callofthevoid.events;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.callofthevoid.block.ModBlocks;
import org.callofthevoid.item.ModItems;

public class ItemTooltips {
    public static void register() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            // Check if the item is the one you want to add a tooltip to
            Item item = stack.getItem();
            if (item == ModItems.COPPER_PLATE) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item ==  ModItems.IRON_PLATE) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item == ModItems.COPPER_WIRE) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item ==  ModItems.IRON_WIRE) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item == ModItems.LATEX_BALL) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item ==  ModItems.SCHEME) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item ==  ModItems.MICROCIRCUIT) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item == ModItems.SENSOR) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }


            if (item == ModBlocks.EXTRACTOR_BLOCK.asItem()) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
            if (item == ModBlocks.ACCUMULATOR_BLOCK.asItem()) {
                lines.add(Text.translatable(item.getTranslationKey() + ".description").formatted(Formatting.AQUA));
            }
        });

    }
}
