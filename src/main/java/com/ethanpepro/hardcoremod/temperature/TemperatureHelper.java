package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.temperature.data.TemperatureData;
import com.ethanpepro.hardcoremod.temperature.modifier.BaseTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.DynamicTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.StaticTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.data.registry.TemperatureDataRegistry;
import com.ethanpepro.hardcoremod.temperature.modifier.registry.TemperatureModifierRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public static Text getFlavorTextForTemperature(@NotNull World world, int temperature) {
		for (TemperatureData data : TemperatureDataRegistry.getTemperatureData().values()) {
			if (isTemperatureInRange(temperature, data.getMin(), data.getMax())) {
				String[] strings = data.getFlavorText();
				
				return new TranslatableText("hardcoremod.temperature." + data.getName() + "." + strings[world.getRandom().nextInt(strings.length)]);
			}
		}

		return null;
	}

	public static int clampAndRound(float temperature) {
		return Math.round(MathHelper.clamp(temperature, getAbsoluteMinimumTemperature(), getAbsoluteMaximumTemperature()));
	}

	public static float calculateTemperature(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos) {
		float temperature = getEquilibriumTemperature();
		
		for (BaseTemperatureModifier modifier : TemperatureModifierRegistry.getModifiers().values()) {
			// Uncomment this to "profile" for bottlenecks and comment out "continue" keyword.
			// long start = System.nanoTime();

			if (modifier instanceof StaticTemperatureModifier staticModifier) {
				temperature += staticModifier.getModifier(entity, world, pos);
				continue;
			}

			if (modifier instanceof DynamicTemperatureModifier dynamicModifier) {
				temperature = dynamicModifier.getModifier(entity, world, pos, temperature);
				continue;
			}

			// long end = System.nanoTime();
			// long duration = (long)((end - start) * 0.001);

			// HardcoreMod.LOGGER.info("{} took {} us", modifier.getIdentifier(), duration);
		}

		return temperature;
	}

	public static float convertTemperatureToAbsoluteRangeRatio(float temperature) {
		float percentage = (2.0f / getAbsoluteTemperatureRange()) * (temperature - getAbsoluteMinimumTemperature()) - 1.0f;

		return MathHelper.clamp(percentage, -1.0f, 1.0f);
	}
}
