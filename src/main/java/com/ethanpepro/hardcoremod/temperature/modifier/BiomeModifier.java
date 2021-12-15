package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class BiomeModifier implements StaticTemperatureModifier {
	private final Identifier identifier;
	private int modifier;

	public BiomeModifier() {
		this.identifier = new Identifier("hardcoremod", "biome");
	}

	@Override
	public void clearResources() {
		modifier = 0;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsInt();
	}

	@Override
	@NotNull
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public float getModifier(@NotNull PlayerEntity player, @NotNull World world, @NotNull BlockPos pos) {
		float center = world.getBiome(pos).getTemperature();
		float north = world.getBiome(pos.add(0, 0, -16)).getTemperature();
		float south = world.getBiome(pos.add(0, 0, 16)).getTemperature();
		float east = world.getBiome(pos.add(16, 0, 0)).getTemperature();
		float west = world.getBiome(pos.add(-16, 0, 0)).getTemperature();

		float average = (center + north + south + east + west) / 5.0f;

		return ((average - 0.75f) / 1.25f) * modifier;
	}
}
