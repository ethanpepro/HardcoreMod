package com.ethanpepro.hardcoremod.api.temperature.registry;

import com.ethanpepro.hardcoremod.api.temperature.TemperatureData;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

public class TemperatureDataRegistry {
	private static final Object2ObjectOpenHashMap<String, TemperatureData> temperatureData = new Object2ObjectOpenHashMap<>();

	public static void register(@NotNull String name, @NotNull TemperatureData data) {
		// TODO: There is currently no sanity checking on the temperature range bounds.

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
}
