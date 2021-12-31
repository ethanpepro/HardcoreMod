package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class TimeModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private float modifier;
	private int timeIterations;

	public TimeModifier() {
		this.identifier = new Identifier("hardcoremod-example", "time");
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
		timeIterations = 0;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		timeIterations = root.get("timeIterations").getAsInt();
	}

	@Override
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos) {
		if (!TemperatureHelper.shouldTemperatureModifierRun(modifier, world)) {
			return 0.0f;
		}

		float time = (float)Math.sin(world.getTimeOfDay() * (MathHelper.TAU / 24000.0f));

		return Math.round(time * timeIterations) * modifier;
	}
}
