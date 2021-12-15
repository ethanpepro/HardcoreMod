package com.ethanpepro.hardcoremod.api.temperature;

import com.ethanpepro.hardcoremod.api.temperature.modifier.BaseTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.modifier.DynamicTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureDataRegistry;
import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class TemperatureHelper {
	public static int getAbsoluteMinimumTemperature() {
		int minimum = 0;

		for (TemperatureData data : TemperatureDataRegistry.getTemperatureData().values()) {
			minimum = Math.min(minimum, data.getMin());
		}

		return minimum;
	}

	public static int getAbsoluteMaximumTemperature() {
		int maximum = 0;

		for (TemperatureData data : TemperatureDataRegistry.getTemperatureData().values()) {
			maximum = Math.max(maximum, data.getMax());
		}

		return maximum;
	}

	public static int getEquilibriumTemperature() {
		return (getAbsoluteMaximumTemperature() + getAbsoluteMinimumTemperature()) / 2;
	}

	public static int clampTemperature(float temperature) {
		return Math.round(MathHelper.clamp(temperature, getAbsoluteMinimumTemperature(), getAbsoluteMaximumTemperature()));
	}

	public static float calculateTemperatureTarget(@NotNull PlayerEntity player, @NotNull World world, @NotNull BlockPos pos) {
		float target = getEquilibriumTemperature();

		for (BaseTemperatureModifier modifier : TemperatureRegistry.getModifiers().values()) {
			if (modifier instanceof StaticTemperatureModifier) {
				target += ((StaticTemperatureModifier)modifier).getModifier(player, world, pos);
			}

			if (modifier instanceof DynamicTemperatureModifier) {
				target = ((DynamicTemperatureModifier)modifier).getModifier(player, world, pos, target);
			}
		}

		return target;
	}

	public static String getFlavorTextForTemperature(@NotNull World world, int temperature) {
		for (TemperatureData data : TemperatureDataRegistry.getTemperatureData().values()) {
			if (temperature >= data.getMin() && temperature <= data.getMax()) {
				String[] strings = data.getFlavor();

				return strings[world.getRandom().nextInt(strings.length)];
			}
		}

		return null;
	}
}
