package com.ethanpepro.hardcoremod;

import de.siphalor.tweed4.annotated.AConfigEntry;
import de.siphalor.tweed4.annotated.ATweedConfig;
import de.siphalor.tweed4.config.ConfigEnvironment;
import de.siphalor.tweed4.config.ConfigScope;
import de.siphalor.tweed4.tailor.cloth.ClothData;

@ATweedConfig(scope = ConfigScope.SMALLEST, environment = ConfigEnvironment.SYNCED, tailors = {"tweed4:cloth", "tweed4:screen"})
@ClothData(modid = "hardcoremod")
public class HardcoreModConfig {
	@AConfigEntry(comment = "Enable Temperature")
	public static boolean enableTemperature = true;
}
