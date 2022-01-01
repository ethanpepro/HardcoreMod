package com.ethanpepro.hardcoremod.temperature;

import com.ethanpepro.hardcoremod.temperature.data.TemperatureData;
import com.ethanpepro.hardcoremod.temperature.data.registry.TemperatureDataRegistry;
import com.ethanpepro.hardcoremod.temperature.modifier.DynamicTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.StaticTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.registry.TemperatureModifierRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TemperatureHelper {
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
		return Math.round(MathHelper.clamp(temperature, TemperatureDataRegistry.getAbsoluteMinimumTemperature(), TemperatureDataRegistry.getAbsoluteMaximumTemperature()));
	}

	public static float calculateTemperature(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos) {
		float temperature = TemperatureDataRegistry.getEquilibriumTemperature();
		
		for (StaticTemperatureModifier modifier : TemperatureModifierRegistry.getStaticTemperatureModifiers().values()) {
			temperature += modifier.getModifier(entity, world, pos);
		}
		
		for (DynamicTemperatureModifier modifier : TemperatureModifierRegistry.getDynamicTemperatureModifiers().values()) {
			temperature = modifier.getModifier(entity, world, pos, temperature);
		}

		return temperature;
	}

	// TODO: Could always remap [-1, 1] to [0, 1] and use that to avoid deprecation?
	public static float convertTemperatureToAbsoluteRangeRatio(float temperature) {
		float percentage = (2.0f / TemperatureDataRegistry.getAbsoluteTemperatureRange()) * (temperature - TemperatureDataRegistry.getAbsoluteMinimumTemperature()) - 1.0f;

		return MathHelper.clamp(percentage, -1.0f, 1.0f);
	}
	
	// TODO: Expand.
	public static boolean shouldTemperatureModifierRun(float modifier, @NotNull World world) {
		if (modifier > 0.0f) {
			return true;
		}
		
		if (world.getDimension().isNatural()) {
			return true;
		}
		
		// TODO: Other conditions.
		
		return false;
	}
	
	public static boolean shouldRun(PlayerEntity player) {
		if (player.getEntityWorld().getDifficulty() == Difficulty.PEACEFUL) {
			return false;
		}
		
		if (player.isSpectator() || player.isCreative()) {
			return false;
		}
		
		// TODO: Other conditions.
		
		return true;
	}
}
