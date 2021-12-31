package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.temperature.modifier.*;
import com.ethanpepro.hardcoremod.temperature.modifier.registry.TemperatureModifierRegistry;

public class HardcoreModExampleTemperatures {
	// TODO: Total, these take about < 100 us every 3 seconds. Acceptable for the sum of players and thermometers?
	public static void register() {
		// TODO: Complex web of overrides? Like clothing can override biome, altitude, weather, but not block or an underwater overide?
		// TODO: Non-performance-crippling way of doing this?
		TemperatureModifierRegistry.register(new DimensionModifier());
		TemperatureModifierRegistry.register(new BiomeModifier());
		TemperatureModifierRegistry.register(new AltitudeModifier());
		TemperatureModifierRegistry.register(new TimeModifier());
		TemperatureModifierRegistry.register(new WeatherModifier());
		TemperatureModifierRegistry.register(new BlockModifier());
		TemperatureModifierRegistry.register(new ClothingModifier());
	}
}
