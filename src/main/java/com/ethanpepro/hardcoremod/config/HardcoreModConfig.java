package com.ethanpepro.hardcoremod.config;

import de.siphalor.tweed4.annotated.AConfigConstraint;
import de.siphalor.tweed4.annotated.AConfigEntry;
import de.siphalor.tweed4.annotated.ATweedConfig;
import de.siphalor.tweed4.config.ConfigEnvironment;
import de.siphalor.tweed4.config.ConfigScope;
import de.siphalor.tweed4.config.constraints.RangeConstraint;
import de.siphalor.tweed4.tailor.cloth.ClothData;

@ATweedConfig(scope = ConfigScope.SMALLEST, environment = ConfigEnvironment.SYNCED, tailors = {"tweed4:cloth"})
@ClothData(modid = "hardcoremod")
public class HardcoreModConfig {
	public static class Notifier {
		public boolean enabled = true;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int maximumAge = 200;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "1.."))
		public int maximumMessages = 5;
	}

	@AConfigEntry(environment = ConfigEnvironment.CLIENT)
	public static Notifier notifier;

	public static class Temperature {
		public boolean enabled = true;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int targetThreshold = 60;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int minimumTemperatureThreshold = 20;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int maximumTemperatureThreshold = 1200;
	}

	public static Temperature temperature;
}
