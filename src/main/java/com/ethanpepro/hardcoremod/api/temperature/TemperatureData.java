package com.ethanpepro.hardcoremod.api.temperature;

import org.jetbrains.annotations.NotNull;

public class TemperatureData {
	private String name;
	private int min;
	private int max;
	private String[] conditions;
	private String[] flavorText;

	public TemperatureData() {

	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public int getMin() {
		return this.min;
	}

	public int getMax() {
		return this.max;
	}

	@NotNull
	public String[] getConditions() {
		return this.conditions;
	}

	@NotNull
	public String[] getFlavorText() {
		return this.flavorText;
	}
}
