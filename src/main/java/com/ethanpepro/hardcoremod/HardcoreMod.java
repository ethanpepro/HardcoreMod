package com.ethanpepro.hardcoremod;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: Refactor build.gradle dependencies
// TODO: Make our libraries required?
// TODO: Tweed, I shouldn't have to include every single module with different versions

// TODO: Evaluate how components are implemented (structuring)
// TODO: Ensure synchronization between client and server configs (where server config takes precedence)

// TODO: Just get it working, then abstract out interface to implementation for third-party mods

public class HardcoreMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("hardcoremod");

	@Override
	public void onInitialize() {
		HardcoreModTemperatures.register();
	}
}
