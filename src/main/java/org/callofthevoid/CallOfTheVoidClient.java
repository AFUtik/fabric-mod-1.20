package org.callofthevoid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import org.callofthevoid.block.ModBlocks;
import org.callofthevoid.screen.ExtractorScreen;
import org.callofthevoid.screen.ModScreenHandlers;

public class CallOfTheVoidClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.EXTRACTOR_SCREEN_HANDLER, ExtractorScreen::new);
	}
}