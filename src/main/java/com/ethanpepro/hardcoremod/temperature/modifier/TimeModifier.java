package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class TimeModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private int modifier;

	public TimeModifier() {
		identifier = new Identifier("hardcoremod", "time");
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
		float time = 0.0f;

		if (world.getDimension().isNatural()) {
			time = (float)Math.sin(world.getTimeOfDay() * ((2.0f * Math.PI) / 24000.0f));
		}

		return Math.round(time) * modifier;
	}
}
