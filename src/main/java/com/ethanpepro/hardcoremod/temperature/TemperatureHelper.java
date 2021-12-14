package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.temperature.modifier.TemperatureDynamicModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.TemperatureStaticModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class TemperatureHelper {
	public static final int EQUILIBRIUM = TemperatureRange.TEMPERATE.getMiddle();

	public static int clampTemperature(float temperature) {
		return (int)MathHelper.clamp(temperature, TemperatureRange.FREEZING.getLow(), TemperatureRange.BURNING.getHigh());
	}

	public static float getTemperatureTarget(@NotNull PlayerEntity player, @NotNull World world, @NotNull BlockPos pos) {
		float target = EQUILIBRIUM;

		for (TemperatureStaticModifier modifier : TemperatureRegistry.getStaticModifiers().values()) {
			target += modifier.getModifier(player, world, pos);
		}

		for (TemperatureDynamicModifier modifier : TemperatureRegistry.getDynamicModifiers().values()) {
			target = modifier.getModifier(player, world, pos, target);
		}

		return target;
	}
}
