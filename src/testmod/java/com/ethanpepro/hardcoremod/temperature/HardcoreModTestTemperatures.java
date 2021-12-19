package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import com.ethanpepro.hardcoremod.temperature.modifier.*;

public class HardcoreModTestTemperatures {
	public static void register() {
		TemperatureRegistry.register(new DimensionModifier());
		TemperatureRegistry.register(new BiomeModifier());
		TemperatureRegistry.register(new AltitudeModifier());
		TemperatureRegistry.register(new TimeModifier());
		TemperatureRegistry.register(new WeatherModifier());
		TemperatureRegistry.register(new BlockModifier());
	}
}
