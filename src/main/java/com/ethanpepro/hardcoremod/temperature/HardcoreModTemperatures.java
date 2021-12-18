package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import com.ethanpepro.hardcoremod.temperature.modifier.AltitudeModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.BiomeModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.TimeModifier;

public class HardcoreModTemperatures {
	public static void register() {
		TemperatureRegistry.register(new BiomeModifier());
		TemperatureRegistry.register(new AltitudeModifier());
		TemperatureRegistry.register(new TimeModifier());
	}
}
