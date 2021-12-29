package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.temperature.modifier.*;
import com.ethanpepro.hardcoremod.temperature.modifier.registry.TemperatureModifierRegistry;

public class HardcoreModExampleTemperatures {
	public static void register() {
		TemperatureModifierRegistry.register(new DimensionModifier());
		TemperatureModifierRegistry.register(new BiomeModifier());
		TemperatureModifierRegistry.register(new AltitudeModifier());
		TemperatureModifierRegistry.register(new TimeModifier());
		TemperatureModifierRegistry.register(new WeatherModifier());
		TemperatureModifierRegistry.register(new BlockModifier());
		//TemperatureModifierRegistry.register(new ClothingModifier());
	}
}
