package com.ethanpepro.hardcoremod.config;

import com.google.common.collect.Lists;
import de.siphalor.tweed4.annotated.AConfigConstraint;
import de.siphalor.tweed4.annotated.AConfigEntry;
import de.siphalor.tweed4.annotated.ATweedConfig;
import de.siphalor.tweed4.config.ConfigEnvironment;
import de.siphalor.tweed4.config.ConfigScope;
import de.siphalor.tweed4.config.constraints.RangeConstraint;
import de.siphalor.tweed4.tailor.cloth.ClothData;

import java.util.ArrayList;

@ATweedConfig(scope = ConfigScope.SMALLEST, environment = ConfigEnvironment.SYNCED, tailors = {"tweed4:cloth"})
@ClothData(modid = "hardcoremod")
public class HardcoreModConfig {
	public static class Temperature {
		public boolean enabled = true;
		
		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int targetThreshold = 60;
		
		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int minimumTemperatureThreshold = 20;
		
		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int maximumTemperatureThreshold = 1200;
		
		@AConfigEntry(environment = ConfigEnvironment.CLIENT)
		public int freezingTemperatureColor = 0xA5CDFF;
		
		@AConfigEntry(environment = ConfigEnvironment.CLIENT)
		public int burningTemperatureColor = 0xB62202;
	}
	
	public static Temperature temperature;
	
	public static class Rust {
		public boolean enableRust = true;
		
		public int rustCheckInterval = 100;
		
		public float rustChance = 0.06f;
		
		public int rustMaxDamage = 6;
		
		public ArrayList<String> materialsThatRust = Lists.newArrayList("chainmail", "iron");
	}
	
	public static Rust rust;
	
	public static class Accessibility {
		public boolean enableOrbDisplay = true;

		public boolean enableOrbMovements = true;

		@AConfigEntry(constraints = @AConfigConstraint(value = RangeConstraint.class, param = "20.."))
		public int orbMovementMaximumTime = 200;
		
		public boolean enableMessages = true;
		
		public int maxMessages = 5;
		
		public int messageLifetime = 200;
		
		public int messageColor = 0xFFE662;
	}

	@AConfigEntry(environment = ConfigEnvironment.CLIENT)
	public static Accessibility accessibility;
}
