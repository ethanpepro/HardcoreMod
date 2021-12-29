package com.ethanpepro.hardcoremod.temperature.modifier.registry;

import com.ethanpepro.hardcoremod.temperature.modifier.BaseTemperatureModifier;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TemperatureModifierRegistry {
	private static final Object2ObjectOpenHashMap<Identifier, BaseTemperatureModifier> temperatureModifiers;

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
	
	static {
		temperatureModifiers = new Object2ObjectOpenHashMap<>();
	}
}
