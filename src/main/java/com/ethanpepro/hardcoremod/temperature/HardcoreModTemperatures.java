package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import com.ethanpepro.hardcoremod.temperature.modifier.BiomeModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.BlockModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.TimeModifier;

public class HardcoreModTemperatures {
	public static void register() {
		TemperatureRegistry.register(new BlockModifier());
		TemperatureRegistry.register(new BiomeModifier());
		TemperatureRegistry.register(new TimeModifier());
	}
}
