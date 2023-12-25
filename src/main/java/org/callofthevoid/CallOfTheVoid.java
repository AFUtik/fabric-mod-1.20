package org.callofthevoid;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.callofthevoid.block.ModBlocks;
import org.callofthevoid.item.ModItems;
import org.callofthevoid.world.OreGenerator;

public class CallOfTheVoid implements ModInitializer {
    public static final String MOD_ID = "callofthevoid";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        ModItems.register();
        ModBlocks.register();

        OreGenerator.register();
    }
}
