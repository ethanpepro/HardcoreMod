package com.ethanpepro.hardcoremod.temperature;

// TODO: Make completely configurable
public enum TemperatureRange {
	FREEZING(-5, -4),
	COLD(-3, -2),
	TEMPERATE(-1, 1),
	HOT(2, 3),
	BURNING(4, 5);

	private final int low;
	private final int high;

	TemperatureRange(int low, int high) {
		this.low = low;
		this.high = high;
	}

	public boolean isInRange(int temperature) {
		return (temperature >= this.low && temperature <= this.high);
	}

	public int getLow() {
		return this.low;
	}

	public int getMiddle() {
		return (this.low + this.high) / 2;
	}

	public int getHigh() {
		return this.high;
	}
}
