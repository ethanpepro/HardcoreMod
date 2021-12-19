package com.ethanpepro.hardcoremod.client.item;

import com.ethanpepro.hardcoremod.api.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
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
	
	// TODO: Why is this deprecated?
	@Override
	public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
		return unclampedCall(itemStack, clientWorld, livingEntity, i);
	}
	
	@Override
	public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
		// TODO: This disables item frames.
		if (entity == null) {
			return 0.0f;
		}
		
		UUID uuid = entity.getUuid();
		
		if (cache.containsKey(uuid)) {
			return cache.get(uuid);
		}
		
		float temperature = TemperatureHelper.calculateTemperature(entity, entity.getEntityWorld(), entity.getBlockPos());
		float value = TemperatureHelper.convertTemperatureToAbsoluteRangeRatio(temperature);
		
		cache.put(uuid, value);
		
		return value;
	}
	
	public void counterThink() {
		counter++;
		
		if (counter >= HardcoreModConfig.temperature.targetThreshold) {
			counter = 0;
			
			cache.clear();
		}
	}
}
