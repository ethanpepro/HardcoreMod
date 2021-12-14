package com.ethanpepro.hardcoremod;

import de.siphalor.tweed4.annotated.AConfigConstraint;
import de.siphalor.tweed4.annotated.AConfigEntry;
import de.siphalor.tweed4.annotated.ATweedConfig;
import de.siphalor.tweed4.config.ConfigEnvironment;
import de.siphalor.tweed4.config.ConfigScope;
import de.siphalor.tweed4.config.constraints.RangeConstraint;
import de.siphalor.tweed4.tailor.cloth.ClothData;

@ATweedConfig(scope = ConfigScope.SMALLEST, environment = ConfigEnvironment.SYNCED, tailors = {"tweed4:cloth", "tweed4:screen"})
@ClothData(modid = "hardcoremod")
public class HardcoreModConfig {
	public static class Accessibility {
		public boolean enableOverlays = true;
	}

	@AConfigEntry(environment = ConfigEnvironment.CLIENT)
	public static Accessibility accessibility;

	public static class Temperature {
		public boolean enable = true;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int updateRate = 20;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int updateMinimumRate = 20;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int updateMaximumRate = 800;
	}

	public static Temperature temperature;

	public static boolean isTemperatureModuleEnabled() {
		return temperature.enable;
	}

	public static int getTargetTemperatureUpdateThreshold() {
		return temperature.updateRate;
	}

	public static int getMinimumTargetTemperatureUpdateThreshold() {
		return temperature.updateMinimumRate;
	}

	public static int getMaximumTargetTemperatureUpdateThreshold() {
		return temperature.updateMaximumRate;
	}
}
