package com.ethanpepro.hardcoremod.api.temperature.registry;

import com.ethanpepro.hardcoremod.api.temperature.TemperatureData;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

public class TemperatureDataRegistry {
	private static final Object2ObjectOpenHashMap<String, TemperatureData> temperatureData = new Object2ObjectOpenHashMap<>();

	public static void registerTemperatureData(@NotNull String name, @NotNull TemperatureData data) {
		if (!temperatureData.containsKey(name)) {
			temperatureData.put(name, data);
		}
	}

	@NotNull public static ImmutableMap<String, TemperatureData> getTemperatureData() {
		return ImmutableMap.copyOf(temperatureData);
	}
}
