package com.ethanpepro.hardcoremod.temperature.data;

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
		return name;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@NotNull
	public String[] getConditions() {
		return conditions;
	}

	@NotNull
	public String[] getFlavorText() {
		return flavorText;
	}
}
