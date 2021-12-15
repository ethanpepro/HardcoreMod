package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import com.ethanpepro.hardcoremod.temperature.modifier.BiomeModifier;

public class HardcoreModTemperatures {
	public static void register() {
		TemperatureRegistry.register(new BiomeModifier());
	}
}
