package com.ethanpepro.hardcoremod.api.temperature.modifier;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface BaseTemperatureModifier {
	default void clearResources() {

	}

	default void processResources(@NotNull JsonObject root) {

	}

	default Identifier getIdentifier() {
		return null;
	}

	default boolean affectsPlayers() {
		return false;
	}
}
