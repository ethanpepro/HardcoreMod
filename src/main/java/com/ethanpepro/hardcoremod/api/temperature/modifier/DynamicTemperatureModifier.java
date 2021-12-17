package com.ethanpepro.hardcoremod.api.temperature.modifier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface DynamicTemperatureModifier extends BaseTemperatureModifier {
	default float getEnvironmentalModifier(@NotNull World world, @NotNull BlockPos pos, float temperature) {
		return temperature;
	}

	default float getPlayerModifier(@NotNull PlayerEntity player, float temperature) {
		return getEnvironmentalModifier(player.getEntityWorld(), player.getBlockPos(), temperature);
	}
}
