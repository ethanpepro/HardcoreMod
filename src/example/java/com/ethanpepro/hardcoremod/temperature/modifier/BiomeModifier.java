package com.ethanpepro.hardcoremod.temperature.modifier;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class BiomeModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private float modifier;
	private float hotModifier;
	private float coldModifier;
	private float humidityModifier;

	public BiomeModifier() {
		this.identifier = new Identifier("hardcoremod-example", "biome");
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
		hotModifier = 0.0f;
		coldModifier = 0.0f;
		humidityModifier = 0.0f;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		humidityModifier = root.get("humidityModifier").getAsFloat();
		hotModifier = root.get("hotModifier").getAsFloat();
		coldModifier = root.get("coldModifier").getAsFloat();
	}

	@Override
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos) {
		if (shouldNotRun(world)) {
			return 0.0f;
		}

		float temperature = 0.0f;
		
		Biome biome = world.getBiome(pos);
		
		float center = biome.getTemperature();
		float north = world.getBiome(pos.add(0, 0, -16)).getTemperature();
		float south = world.getBiome(pos.add(0, 0, 16)).getTemperature();
		float east = world.getBiome(pos.add(16, 0, 0)).getTemperature();
		float west = world.getBiome(pos.add(-16, 0, 0)).getTemperature();
		
		float average = (center + north + south + east + west) / 5.0f;
		
		temperature += Math.round((2.0f / 3.0f) * (average + 1.0f) - 1.0f);
		
		if (biome.isHot(pos)) {
			temperature += hotModifier;
		}
		
		if (biome.isCold(pos)) {
			temperature += coldModifier;
		}
		
		if (biome.hasHighHumidity()) {
			temperature += humidityModifier;
		}
		
		return temperature * modifier;
	}
}
