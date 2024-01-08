package org.callofthevoid.fluid;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;

public class ModFluids {
    public static FlowableFluid STILL_GRAPHITE_OIL;
    public static FlowableFluid FLOWING_GRAPHITE_OIL;

    public static Block GRAPHITE_OIL_BLOCK;
    public static Item SOAP_WATER_BUCKET;

    public static void register() {
        STILL_GRAPHITE_OIL = Registry.register(Registries.FLUID,
                new Identifier(CallOfTheVoid.MOD_ID, "graphite_oil"), new GraphiteOilFluid.Still());
        FLOWING_GRAPHITE_OIL = Registry.register(Registries.FLUID,
                new Identifier(CallOfTheVoid.MOD_ID, "flowing_graphite_oil"), new GraphiteOilFluid.Flowing());

        GRAPHITE_OIL_BLOCK = Registry.register(Registries.BLOCK, new Identifier(CallOfTheVoid.MOD_ID, "graphite_oil_block.json"),
                new FluidBlock(ModFluids.STILL_GRAPHITE_OIL, FabricBlockSettings.copyOf(Blocks.WATER)){ });

        SOAP_WATER_BUCKET = Registry.register(Registries.ITEM, new Identifier(CallOfTheVoid.MOD_ID, "soap_water_bucket"),
                new BucketItem(ModFluids.STILL_GRAPHITE_OIL, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    }
}
