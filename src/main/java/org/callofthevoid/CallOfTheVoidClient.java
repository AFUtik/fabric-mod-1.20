package org.callofthevoid;

import org.callofthevoid.fluid.ModFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.callofthevoid.networking.ModMessages;
import org.callofthevoid.screen.ExtractorScreen;
import org.callofthevoid.screen.ModScreenHandlers;

public class CallOfTheVoidClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModMessages.registerS2CPackets();

		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_GRAPHITE_OIL, ModFluids.FLOWING_GRAPHITE_OIL,
				new SimpleFluidRenderHandler(
						new Identifier("minecraft:block/water_still"),
						new Identifier("minecraft:block/water_flow"),
						0xA1E038D0
				));

		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
				ModFluids.STILL_GRAPHITE_OIL, ModFluids.FLOWING_GRAPHITE_OIL);

		HandledScreens.register(ModScreenHandlers.EXTRACTOR_SCREEN_HANDLER, ExtractorScreen::new);
	}
}