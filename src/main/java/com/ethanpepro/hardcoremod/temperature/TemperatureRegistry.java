package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.temperature.modifier.TemperatureDynamicModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.TemperatureStaticModifier;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TemperatureRegistry {
	private static final Object2ObjectOpenHashMap<Identifier, TemperatureStaticModifier> temperatureStaticModifiers = new Object2ObjectOpenHashMap<>();

	private static final Object2ObjectOpenHashMap<Identifier, TemperatureDynamicModifier> temperatureDynamicModifiers = new Object2ObjectOpenHashMap<>();

	public static void registerStaticModifier(@NotNull TemperatureStaticModifier modifier) {
		Identifier identifier = modifier.getIdentifier();

		if (!temperatureStaticModifiers.containsKey(identifier)) {
			temperatureStaticModifiers.put(identifier, modifier);
		}
	}

	@NotNull public static ImmutableMap<Identifier, TemperatureStaticModifier> getStaticModifiers() {
		return ImmutableMap.copyOf(temperatureStaticModifiers);
	}

	public static void registerDynamicModifier(@NotNull TemperatureDynamicModifier modifier) {
		Identifier identifier = modifier.getIdentifier();

		if (!temperatureDynamicModifiers.containsKey(identifier)) {
			temperatureDynamicModifiers.put(identifier, modifier);
		}
	}

	@NotNull public static ImmutableMap<Identifier, TemperatureDynamicModifier> getDynamicModifiers() {
		return ImmutableMap.copyOf(temperatureDynamicModifiers);
	}
}
