package com.ethanpepro.hardcoremod.item;

// TODO: Move to main?
public enum ClothingType {
	CLOAK("cloak", 100);
	
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
