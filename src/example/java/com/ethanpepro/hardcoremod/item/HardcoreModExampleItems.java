package com.ethanpepro.hardcoremod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HardcoreModExampleItems {
	// TODO: Use armor protection "types" like ARMOR and CLOTHING.
	public static final Item FACE_WRAP = new ClothingItem(ClothingType.HEAD, 0, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item ARMING_CAP = new ClothingItem(ClothingType.HEAD, 1, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	public static final Item TUNIC = new ClothingItem(ClothingType.CHEST, 0, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item GAMBESON = new ClothingItem(ClothingType.CHEST, 1, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	public static final Item CASUAL_CLOAK = new ClothingItem(ClothingType.CLOAK, 0, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item FORMAL_CLOAK = new ClothingItem(ClothingType.CLOAK, 1, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	public static final Item TROUSERS = new ClothingItem(ClothingType.LEGS, 0, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item GREAVES = new ClothingItem(ClothingType.LEGS, 1, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	public static final Item SHOES = new ClothingItem(ClothingType.FEET, 0, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item BOOTS = new ClothingItem(ClothingType.FEET, 1, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	public static final Item GLOVE = new ClothingItem(ClothingType.HAND, 0, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item BRACER = new ClothingItem(ClothingType.HAND, 1, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	public static final Item OFFHAND_GLOVE = new ClothingItem(ClothingType.OFFHAND, 0, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item OFFHAND_BRACER = new ClothingItem(ClothingType.OFFHAND, 1, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	// TODO: Make our own registry?
	public static void register() {
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "face_wrap"), FACE_WRAP);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "arming_cap"), ARMING_CAP);
		
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "tunic"), TUNIC);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "gambeson"), GAMBESON);
		
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "casual_cloak"), CASUAL_CLOAK);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "formal_cloak"), FORMAL_CLOAK);
		
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "trousers"), TROUSERS);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "greaves"), GREAVES);
		
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "shoes"), SHOES);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "boots"), BOOTS);
		
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "glove"), GLOVE);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "bracer"), BRACER);
		
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "offhand_glove"), OFFHAND_GLOVE);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "offhand_bracer"), OFFHAND_BRACER);
	}
}
