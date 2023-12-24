package org.mynewmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.mynewmod.MyNewMod;

public class ModScreenHandlers {
    public static final ScreenHandlerType<FurnaceScreenHandler> FURNACE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(MyNewMod.MODID, "furnace_block"),
                    new ExtendedScreenHandlerType<>(FurnaceScreenHandler::new));
    public static void registerScreenHandlers() {
        MyNewMod.LOGGER.info("Registering Screen Handlers for " + MyNewMod.MODID);
    }
}
