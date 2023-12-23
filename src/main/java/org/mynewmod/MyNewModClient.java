package org.mynewmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.mynewmod.block.ModBlocks;

public class MyNewModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NEW_BLOCK, RenderLayer.getCutout());

		//HandledScreens.register(ModScreenHandlers.NEW_BLOCK_SCREEN_HANDLER, NewBlockScreen::new);
	}
}