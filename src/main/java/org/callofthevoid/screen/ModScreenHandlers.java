package org.callofthevoid.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;

public class ModScreenHandlers {
    public static final ScreenHandlerType<ExtractorScreenHandler> EXTRACTOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(CallOfTheVoid.MOD_ID, "extractor"),
                    new ExtendedScreenHandlerType<>(ExtractorScreenHandler::new));
    public static void registerScreenHandlers() {
        CallOfTheVoid.LOGGER.info("Registering Screen Handlers for " + CallOfTheVoid.MOD_ID);
    }
}
