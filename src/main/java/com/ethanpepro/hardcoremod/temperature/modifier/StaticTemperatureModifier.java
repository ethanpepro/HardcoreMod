package com.ethanpepro.hardcoremod.temperature.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface StaticTemperatureModifier extends BaseTemperatureModifier {
	default float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos) {
		return 0.0f;
	}
}
