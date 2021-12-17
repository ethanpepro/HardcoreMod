package com.ethanpepro.hardcoremod.api.temperature.modifier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface StaticTemperatureModifier extends BaseTemperatureModifier {
	default float getEnvironmentalModifier(@NotNull World world, @NotNull BlockPos pos) {
		return 0.0f;
	}

	default float getPlayerModifier(@NotNull PlayerEntity player) {
		return getEnvironmentalModifier(player.getEntityWorld(), player.getBlockPos());
	}
}
