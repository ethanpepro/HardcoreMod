package com.ethanpepro.hardcoremod.api.temperature;

import com.ethanpepro.hardcoremod.api.temperature.modifier.BaseTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.modifier.DynamicTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureDataRegistry;
import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TemperatureHelper {
	// TODO: The performance cost might not be terrible on these functions, but find a way to cache these once all TemperatureData members are registered.
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

	public static int getAbsoluteTemperatureRange() {
		return getAbsoluteMaximumTemperature() - getAbsoluteMinimumTemperature();
	}

	public static boolean isTemperatureInRange(int temperature, int min, int max) {
		return (temperature >= min && temperature <= max);
	}

	@Nullable
	public static String[] getConditionsForTemperature(int temperature) {
		for (TemperatureData data : TemperatureDataRegistry.getTemperatureData().values()) {
			if (isTemperatureInRange(temperature, data.getMin(), data.getMax())) {
				return data.getConditions();
			}
		}

		return null;
	}

	@Nullable
	public static String getFlavorTextForTemperature(@NotNull World world, int temperature) {
		for (TemperatureData data : TemperatureDataRegistry.getTemperatureData().values()) {
			if (isTemperatureInRange(temperature, data.getMin(), data.getMax())) {
				String[] strings = data.getFlavorText();

				return strings[world.getRandom().nextInt(strings.length)];
			}
		}

		return null;
	}

	public static int clamp(float temperature) {
		return (int)MathHelper.clamp(temperature, getAbsoluteMinimumTemperature(), getAbsoluteMaximumTemperature());
	}

	public static float calculateTemperature(@Nullable PlayerEntity player, @NotNull World world, @NotNull BlockPos pos) {
		float temperature = getEquilibriumTemperature();

		for (BaseTemperatureModifier modifier : TemperatureRegistry.getModifiers().values()) {
			if (modifier instanceof StaticTemperatureModifier) {
				// TODO: Should modifiers be allowed top implement both types of modifiers?
				temperature += ((StaticTemperatureModifier)modifier).getEnvironmentalModifier(world, pos);

				if (modifier.affectsPlayers() && Objects.nonNull(player)) {
					temperature += ((StaticTemperatureModifier)modifier).getPlayerModifier(player);
				}
			}

			if (modifier instanceof DynamicTemperatureModifier) {
				// TODO: Should modifiers be allowed top implement both types of modifiers?
				temperature = ((DynamicTemperatureModifier)modifier).getEnvironmentalModifier(world, pos, temperature);

				if (modifier.affectsPlayers() && Objects.nonNull(player)) {
					temperature = ((DynamicTemperatureModifier)modifier).getPlayerModifier(player, temperature);
				}
			}
		}

		return temperature;
	}

	public static float convertTemperatureToAbsoluteRangeRatio(float temperature) {
		int range = getAbsoluteTemperatureRange();
		float percentage = (2.0f / range) * (temperature - getAbsoluteMinimumTemperature()) - 1.0f;

		return MathHelper.clamp(percentage, -1.0f, 1.0f);
	}
}
