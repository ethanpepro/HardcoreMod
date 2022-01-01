package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.temperature.data.registry.TemperatureDataRegistry;
import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class DimensionModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private float modifier;
	private boolean netherModifier;
	private boolean endModifier;

	public DimensionModifier() {
		identifier = new Identifier("hardcoremod-example", "dimension");
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
		netherModifier = false;
		endModifier = false;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		netherModifier = root.get("netherModifier").getAsBoolean();
		endModifier = root.get("endModifier").getAsBoolean();
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

		float temperature = 0.0f;

		if (endModifier && world.getRegistryKey().equals(World.END)) {
			temperature = TemperatureDataRegistry.getAbsoluteMinimumTemperature();
		}

		if (netherModifier && world.getRegistryKey().equals(World.NETHER)) {
			temperature = TemperatureDataRegistry.getAbsoluteMaximumTemperature();
		}

		return temperature * modifier;
	}
}
