package org.callofthevoid.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.block.ModBlocks;

public class OreGenerator {
    private static ConfiguredFeature<?, ?> GRAPHITE_ORE_OVERWORLD = configurefeature(ModBlocks.GRAPHITE_ORE_BLOCK, 0, 240, 9, 20);

    public static void register() {
        registerOre("graphite_ore", ModBlocks.GRAPHITE_ORE_BLOCK, GRAPHITE_ORE_OVERWORLD);
    }

    private static void registerOre(String ore_name, Block ore_block, ConfiguredFeature<?, ?> feature) {
        RegistryKey<ConfiguredFeature<?, ?>> OreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
                new Identifier(CallOfTheVoid.MOD_ID, ore_name));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, OreOverworld.getValue(), feature);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                OreOverworld);
    }

    private static ConfiguredFeature<?, ?> configurefeature(Block block, int min_y, int max_y, int size,
                                                            int num_per_chunk) {
        return Feature.ORE
                .configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, block.getDefaultState(),
                        size))
                .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, min_y, max_y))).spreadHorizontally()
                .repeat(num_per_chunk);
    }
}
