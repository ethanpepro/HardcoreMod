package com.ethanpepro.hardcoremod.temperature.modifier.registry;

import com.ethanpepro.hardcoremod.temperature.modifier.BaseTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.DynamicTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.StaticTemperatureModifier;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TemperatureModifierRegistry {
	private static final Object2ObjectOpenHashMap<Identifier, BaseTemperatureModifier> temperatureModifiers;
	private static final Object2ObjectOpenHashMap<Identifier, StaticTemperatureModifier> staticTemperatureModifiers;
	private static final Object2ObjectOpenHashMap<Identifier, DynamicTemperatureModifier> dynamicTemperatureModifiers;

	public static void register(@NotNull BaseTemperatureModifier modifier) {
		Identifier identifier = modifier.getIdentifier();
		boolean registered = false;
		
		if (modifier instanceof StaticTemperatureModifier staticTemperatureModifier) {
			if (!staticTemperatureModifiers.containsKey(identifier)) {
				staticTemperatureModifiers.put(identifier, staticTemperatureModifier);
				
				registered = true;
			}
		}
		
		if (modifier instanceof DynamicTemperatureModifier dynamicTemperatureModifier) {
			if (!dynamicTemperatureModifiers.containsKey(identifier)) {
				dynamicTemperatureModifiers.put(identifier, dynamicTemperatureModifier);
				
				registered = true;
			}
		}
		
		if (registered) {
			temperatureModifiers.put(identifier, modifier);
		}
	}
	
	@NotNull
	public static ImmutableMap<Identifier, BaseTemperatureModifier> getTemperatureModifiers() {
		return ImmutableMap.copyOf(temperatureModifiers);
	}
	
	@NotNull
	public static ImmutableMap<Identifier, StaticTemperatureModifier> getStaticTemperatureModifiers() {
		return ImmutableMap.copyOf(staticTemperatureModifiers);
	}
	
	@NotNull
	public static ImmutableMap<Identifier, DynamicTemperatureModifier> getDynamicTemperatureModifiers() {
		return ImmutableMap.copyOf(dynamicTemperatureModifiers);
	}
	
	static {
		temperatureModifiers = new Object2ObjectOpenHashMap<>();
		staticTemperatureModifiers = new Object2ObjectOpenHashMap<>();
		dynamicTemperatureModifiers = new Object2ObjectOpenHashMap<>();
	}
}
