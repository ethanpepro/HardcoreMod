package com.ethanpepro.hardcoremod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HardcoreModExampleItems {
	public static final Item CASUAL_CLOAK = new ClothingItem(ClothingType.CLOAK, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	public static final Item FORMAL_CLOAK = new ClothingItem(ClothingType.CLOAK, new FabricItemSettings().group(HardcoreModItemGroup.CLOTHING));
	
	public static void register() {
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "casual_cloak"), CASUAL_CLOAK);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod-example", "formal_cloak"), FORMAL_CLOAK);
	}
}
