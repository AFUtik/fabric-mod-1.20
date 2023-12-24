package org.mynewmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import org.mynewmod.block.ModBlocks;
import org.mynewmod.screen.FurnaceScreen;
import org.mynewmod.screen.ModScreenHandlers;

public class MyNewModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NEW_BLOCK, RenderLayer.getCutout());

		HandledScreens.register(ModScreenHandlers.FURNACE_SCREEN_HANDLER, FurnaceScreen::new);
	}
}