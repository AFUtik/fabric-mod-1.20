package org.mynewmod;

import net.fabricmc.api.ModInitializer;
import org.mynewmod.block.ModBlocks;
//import org.mynewmod.block.entity.ModBlocksEntity;
import org.mynewmod.item.ModItems;
import org.mynewmod.item.ModItemsGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyNewMod implements ModInitializer {
	public static final String MODID = "mynewmod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModItems.register();
		ModBlocks.register();

		ModItemsGroups.registerItemGroups();

		//ModBlocksEntity.registerAllEntityBlock();
	}
}