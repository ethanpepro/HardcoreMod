package com.ethanpepro.hardcoremod.api.temperature.modifier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface DynamicTemperatureModifier extends BaseTemperatureModifier {
	float getModifier(@NotNull PlayerEntity player, @NotNull World world, @NotNull BlockPos pos, float currentTemperature);
}
