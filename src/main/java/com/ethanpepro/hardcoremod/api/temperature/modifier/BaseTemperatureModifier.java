package com.ethanpepro.hardcoremod.api.temperature.modifier;

import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface BaseTemperatureModifier {
	void clearResources();

	void processResources(@NotNull ResourceManager manager, @NotNull Collection<Identifier> resources);

	@NotNull Identifier getIdentifier();
}
