package com.ethanpepro.hardcoremod.client;

import com.ethanpepro.hardcoremod.client.item.ThermometerModelPredicateProvider;
import com.ethanpepro.hardcoremod.item.HardcoreModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HardcoreModClient implements ClientModInitializer {
	public static ThermometerModelPredicateProvider thermometerModelPredicateProvider;

	@Override
	public void onInitializeClient() {
		// TODO: No more programmer art for the thermometer.
		FabricModelPredicateProviderRegistry.register(HardcoreModItems.THERMOMETER, new Identifier("hardcoremod", "temperature"), thermometerModelPredicateProvider = new ThermometerModelPredicateProvider());
		
		ClientTickEvents.END_WORLD_TICK.register(world -> {
			thermometerModelPredicateProvider.cacheThink();
		});
	}
}
