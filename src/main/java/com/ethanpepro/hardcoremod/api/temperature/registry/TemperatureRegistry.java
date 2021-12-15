package com.ethanpepro.hardcoremod.api.temperature.registry;

import com.ethanpepro.hardcoremod.api.temperature.modifier.BaseTemperatureModifier;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

// Mods should be able to add new temperature modifiers with access to their own respective resources
// Mods should not be able to add new temperature modifier types (hardcoded in TemperatureHelper)
public class TemperatureRegistry {
	private static final Object2ObjectOpenHashMap<Identifier, BaseTemperatureModifier> temperatureModifiers = new Object2ObjectOpenHashMap<>();

	public static void register(@NotNull BaseTemperatureModifier modifier) {
		Identifier identifier = modifier.getIdentifier();

		if (!temperatureModifiers.containsKey(identifier)) {
			temperatureModifiers.put(identifier, modifier);
		}
	}

	@NotNull
	public static ImmutableMap<Identifier, BaseTemperatureModifier> getModifiers() {
		return ImmutableMap.copyOf(temperatureModifiers);
	}
}
