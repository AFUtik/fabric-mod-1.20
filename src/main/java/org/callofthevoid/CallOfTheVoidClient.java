package org.callofthevoid;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.callofthevoid.screen.ExtractorScreen;
import org.callofthevoid.screen.ModScreenHandlers;

public class CallOfTheVoidClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.EXTRACTOR_SCREEN_HANDLER, ExtractorScreen::new);
	}
}