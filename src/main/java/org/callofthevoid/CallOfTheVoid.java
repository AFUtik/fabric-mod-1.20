package org.callofthevoid;

import org.callofthevoid.fluid.ModFluids;
import net.fabricmc.api.ModInitializer;
import org.callofthevoid.block.ModBlocks;
import org.callofthevoid.blockentity.ModBlockEntities;
import org.callofthevoid.item.ModItems;
import org.callofthevoid.item.ModItemsGroups;
import org.callofthevoid.screen.ModScreenHandlers;
import org.callofthevoid.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallOfTheVoid implements ModInitializer {
	public static final String MOD_ID = "callofthevoid";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.register();
		ModBlocks.register();
		ModFluids.register();
		ModItemsGroups.registerItemGroups();

		ModScreenHandlers.registerScreenHandlers();

		ModBlockEntities.registerBlockEntities();

		ModWorldGeneration.generateModWorldGen();
	}
}