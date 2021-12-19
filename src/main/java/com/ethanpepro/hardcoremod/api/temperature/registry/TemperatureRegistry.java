package com.ethanpepro.hardcoremod.api.temperature.registry;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.api.temperature.modifier.BaseTemperatureModifier;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TemperatureRegistry {
	private static final Object2ObjectOpenHashMap<Identifier, BaseTemperatureModifier> temperatureModifiers = new Object2ObjectOpenHashMap<>();

	public static void register(@NotNull BaseTemperatureModifier modifier) {
		Identifier identifier = modifier.getIdentifier();
		
		HardcoreMod.LOGGER.info("register tried for {}", identifier);
		
		if (!temperatureModifiers.containsKey(identifier)) {
			temperatureModifiers.put(identifier, modifier);
		}
	}

	@NotNull
	public static ImmutableMap<Identifier, BaseTemperatureModifier> getModifiers() {
		return ImmutableMap.copyOf(temperatureModifiers);
	}
}
