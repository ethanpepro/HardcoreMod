package com.ethanpepro.hardcoremod.temperature.data.registry;

import com.ethanpepro.hardcoremod.temperature.data.TemperatureData;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TemperatureDataRegistry {
	private static final Object2ObjectLinkedOpenHashMap<String, TemperatureData> temperatureData;
	private static int absoluteMinimumTemperature;
	private static int absoluteMaximumTemperature;

	public static void register(@NotNull String key, @NotNull TemperatureData value) throws Exception {
		String name = value.getName();
		
		if (!key.equals(name)) {
			throw new Exception(String.format("Key %s conflicts with internal name %s", key, name));
		}
		
		int min = value.getMin();
		int max = value.getMax();
		
		if (min > max) {
			throw new Exception(String.format("Nonsense temperature range of %d...%d in %s", min, max, value.getName()));
		}
		
		for (Map.Entry<String, TemperatureData> entry : temperatureData.entrySet()) {
			TemperatureData entryValue = entry.getValue();
			
			if (min <= entryValue.getMax() && entryValue.getMin() <= max) {
				throw new Exception(String.format("Temperature range in %s overlaps with existing range in %s", value.getName(), entryValue.getName()));
			}
			
			if (value.getName().equals(entryValue.getName())) {
				throw new Exception(String.format("Registered name conflict %s with existing %s", value.getName(), entryValue.getName()));
			}
		}
		
		// TODO: Is there a case where the above passes but fails at containsKey()?
		if (!temperatureData.containsKey(name)) {
			temperatureData.put(name, value);
		}
	}
	
	// TODO: Optimize more.
	public static void validate() throws Exception {
		int absoluteMin = 0;
		int absoluteMax = 0;
		
		// TODO: Cleanup.
		Map.Entry<String, TemperatureData> previousEntry = null;
		for (Map.Entry<String, TemperatureData> entry : temperatureData.entrySet()) {
			String key = entry.getKey();
			TemperatureData value = entry.getValue();
			
			int min = value.getMin();
			int max = value.getMax();
			
			if (previousEntry != null) {
				String previousKey = previousEntry.getKey();
				TemperatureData previousValue = previousEntry.getValue();
				
				int previousMin = previousValue.getMin();
				int previousMax = previousValue.getMax();
				
				if (min - 1 != previousMax) {
					throw new Exception(String.format("Temperature range for %s (%d...%d) does not logically follow previous range %s (%d...%d)", key, min, max, previousKey, previousMin, previousMax));
				}
			}
			
			absoluteMin = Math.min(absoluteMin, min);
			absoluteMax = Math.max(absoluteMax, max);
			
			previousEntry = entry;
		}
		
		// TODO: Allow "unbalanced" equilibrium temperatures?
		// Need abs() on absoluteMax?
		if ((Math.abs(absoluteMax) - Math.abs(absoluteMin)) != 0) {
			throw new Exception(String.format("Can't balance temperature range with %d...%d", absoluteMin, absoluteMax));
		}
		
		absoluteMinimumTemperature = absoluteMin;
		absoluteMaximumTemperature = absoluteMax;
	}

	@NotNull
	public static ImmutableMap<String, TemperatureData> getTemperatureData() {
		return ImmutableMap.copyOf(temperatureData);
	}

	public static void clear() {
		temperatureData.clear();
	}
	
	public static int getAbsoluteMinimumTemperature() {
		return absoluteMinimumTemperature;
	}
	
	public static int getAbsoluteMaximumTemperature() {
		return absoluteMaximumTemperature;
	}
	
	// TODO: In case we want to make a much more flexible system, such as temperature ranges are all positive like a [0, 100] scale.
	public static int getEquilibriumTemperature() {
		return 0;
	}
	
	public static int getAbsoluteTemperatureRange() {
		return absoluteMaximumTemperature - absoluteMinimumTemperature;
	}
	
	static {
		temperatureData = new Object2ObjectLinkedOpenHashMap<>();
		absoluteMinimumTemperature = 0;
		absoluteMaximumTemperature = 0;
	}
}
