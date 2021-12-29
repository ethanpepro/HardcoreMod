package com.ethanpepro.hardcoremod.item;

public enum ClothingType {
	HEAD("head", 125),
	CHEST("chest", 200),
	CLOAK("cloak", 75),
	LEGS("legs", 150),
	FEET("feet", 100),
	HAND("hand", 50),
	OFFHAND("offhand", 50);
	
	private final String name;
	private final int durability;
	
	ClothingType(String name, int durability) {
		this.name = name;
		this.durability = durability;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDurability() {
		return durability;
	}
}
