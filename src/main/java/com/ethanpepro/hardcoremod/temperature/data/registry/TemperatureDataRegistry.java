package com.ethanpepro.hardcoremod.temperature.data.registry;

import com.ethanpepro.hardcoremod.temperature.data.TemperatureData;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

public class TemperatureDataRegistry {
	private static final Object2ObjectOpenHashMap<String, TemperatureData> temperatureData;

	public static void register(@NotNull String name, @NotNull TemperatureData data) {
		// TODO: There is currently no sanity checking on the temperature range bounds.
		// TODO: Members must be valid.
		// TODO: min/max must make sense, and must be "balanced" so equilibrium is 0 (absolute min and max must be "equal") when "done" registering.

		if (!temperatureData.containsKey(name)) {
			temperatureData.put(name, data);
		}
	}

	@NotNull
	public static ImmutableMap<String, TemperatureData> getTemperatureData() {
		return ImmutableMap.copyOf(temperatureData);
	}

	public static void clear() {
		temperatureData.clear();
	}
	
	static {
		temperatureData = new Object2ObjectOpenHashMap<>();
	}
}
