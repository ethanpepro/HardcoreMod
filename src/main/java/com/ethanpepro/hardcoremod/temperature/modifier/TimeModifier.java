package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class TimeModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private float modifier;

	public TimeModifier() {
		this.identifier = new Identifier("hardcoremod", "time");
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
	}

	@Override
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public boolean affectsPlayers() {
		return false;
	}

	@Override
	public float getEnvironmentalModifier(@NotNull World world, @NotNull BlockPos pos) {
		float time = 0.0f;

		if (world.getDimension().isNatural() && pos.getY() >= world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY()) {
			time = (float)Math.sin(world.getTimeOfDay() * ((2.0f * Math.PI) / 24000.0f));
		}

		return Math.round(time * modifier);
	}
}
