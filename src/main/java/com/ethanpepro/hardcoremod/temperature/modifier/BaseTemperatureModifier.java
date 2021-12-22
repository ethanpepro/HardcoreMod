package com.ethanpepro.hardcoremod.temperature.modifier;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BaseTemperatureModifier {
	default void clearResources() {

	}

	default void processResources(@NotNull JsonObject root) {

	}

	default Identifier getIdentifier() {
		return null;
	}

	// TODO: Move to TemperatureHelper
	default boolean shouldNotRun(@NotNull World world) {
		if (!world.getDimension().isNatural()) {
			return true;
		}

		// TODO: Other conditions.

		return false;
	}
}
