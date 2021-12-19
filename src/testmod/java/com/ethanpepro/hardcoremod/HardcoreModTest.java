package com.ethanpepro.hardcoremod;

import com.ethanpepro.hardcoremod.temperature.HardcoreModTestTemperatures;
import net.fabricmc.api.ModInitializer;

public class HardcoreModTest implements ModInitializer {
	@Override
	public void onInitialize() {
		HardcoreModTestTemperatures.register();
	}
}