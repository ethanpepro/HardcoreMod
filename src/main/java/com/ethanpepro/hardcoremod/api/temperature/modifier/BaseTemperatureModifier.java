package com.ethanpepro.hardcoremod.api.temperature.modifier;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface BaseTemperatureModifier {
	void clearResources();

	void processResources(@NotNull JsonObject root);

	@NotNull
	Identifier getIdentifier();
}
