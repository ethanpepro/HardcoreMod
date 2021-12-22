package com.ethanpepro.hardcoremod.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class HardcoreModComponents implements EntityComponentInitializer {
	public static final ComponentKey<Temperature> TEMPERATURE = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("hardcoremod", "temperature"), Temperature.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(TEMPERATURE, Temperature::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
