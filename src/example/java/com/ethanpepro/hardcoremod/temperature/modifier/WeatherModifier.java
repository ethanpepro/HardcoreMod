package com.ethanpepro.hardcoremod.temperature.modifier;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class WeatherModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private float modifier;
	private float rainModifier;
	private float snowModifier;
	private float waterModifier;

	public WeatherModifier() {
		identifier = new Identifier("hardcoremod-example", "weather");
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
		rainModifier = 0.0f;
		snowModifier = 0.0f;
		waterModifier = 0.0f;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		rainModifier = root.get("rainModifier").getAsFloat();
		snowModifier = root.get("snowModifier").getAsFloat();
		waterModifier = root.get("waterModifier").getAsFloat();
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

		if (world.isRaining() && world.isSkyVisible(pos) && world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() <= pos.getY()) {
			Biome biome = world.getBiome(pos);

			if (biome.getPrecipitation() == Biome.Precipitation.RAIN) {
				temperature += rainModifier;
			}

			if (biome.getPrecipitation() == Biome.Precipitation.SNOW) {
				temperature += snowModifier;
			}
		} else if (entity.isWet()) {
			temperature += waterModifier;
		}

		return temperature * modifier;
	}
}
