package com.ethanpepro.hardcoremod.api.temperature;

import org.jetbrains.annotations.NotNull;

public class TemperatureData {
	private String name;
	private int min;
	private int max;
	private String[] conditions;
	private String[] flavor;

	public @NotNull String getName() {
		return this.name;
	}

	public int getMin() {
		return this.min;
	}

	public int getMax() {
		return this.max;
	}

	public @NotNull String[] getConditions() {
		return this.conditions;
	}

	public @NotNull String[] getFlavor() {
		return this.flavor;
	}
}
