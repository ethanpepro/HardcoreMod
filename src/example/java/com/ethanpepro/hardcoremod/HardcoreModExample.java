package com.ethanpepro.hardcoremod;

import com.ethanpepro.hardcoremod.item.HardcoreModExampleItems;
import com.ethanpepro.hardcoremod.temperature.HardcoreModExampleTemperatures;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HardcoreModExample implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("hardcoremod-example");
	
	@Override
	public void onInitialize() {
		HardcoreModExampleItems.register();
		HardcoreModExampleTemperatures.register();
	}
}