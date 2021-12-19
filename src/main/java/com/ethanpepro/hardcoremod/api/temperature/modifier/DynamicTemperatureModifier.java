package com.ethanpepro.hardcoremod.api.temperature.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface DynamicTemperatureModifier extends BaseTemperatureModifier {
	default float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos, float temperature) {
		return temperature;
	}
}
