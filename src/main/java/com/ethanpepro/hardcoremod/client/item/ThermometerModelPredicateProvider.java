package com.ethanpepro.hardcoremod.client.item;

import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ThermometerModelPredicateProvider implements UnclampedModelPredicateProvider {
	private final Object2ObjectOpenHashMap<UUID, Float> cache;
	private int counter;
	
	public ThermometerModelPredicateProvider() {
		cache = new Object2ObjectOpenHashMap<>();
		counter = 0;
	}
	
	public void cacheThink() {
		counter++;
		
		if (counter >= HardcoreModConfig.temperature.targetThreshold) {
			counter = 0;
			
			cache.clear();
		}
	}
	
	// TODO: Why is this deprecated?
	@SuppressWarnings("deprecation")
	@Override
	public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
		return unclampedCall(itemStack, clientWorld, livingEntity, i);
	}
	
	@Override
	public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
		// TODO: This disables item frames, fix.
		if (entity == null) {
			return 0.0f;
		}
		
		return cache.computeIfAbsent(entity.getUuid(), value -> TemperatureHelper.convertTemperatureToAbsoluteRangeRatio(TemperatureHelper.clampAndRound(TemperatureHelper.calculateTemperature(entity, entity.getEntityWorld(), entity.getBlockPos()))));
	}
}
