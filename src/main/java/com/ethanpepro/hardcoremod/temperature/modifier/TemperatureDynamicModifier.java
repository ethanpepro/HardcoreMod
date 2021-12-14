package com.ethanpepro.hardcoremod.temperature.modifier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface TemperatureDynamicModifier {
	@NotNull Identifier getIdentifier();

	float getModifier(@NotNull PlayerEntity player, @NotNull World world, @NotNull BlockPos pos, float currentTemperature);
}
