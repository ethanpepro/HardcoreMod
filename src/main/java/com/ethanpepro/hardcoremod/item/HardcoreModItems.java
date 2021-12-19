package com.ethanpepro.hardcoremod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HardcoreModItems {
	public static final Item THERMOMETER = new Item(new FabricItemSettings().maxCount(1).group(HardcoreModItemGroup.TOOLS));

	public static void register() {
		Registry.register(Registry.ITEM, new Identifier("hardcoremod", "thermometer"), THERMOMETER);
	}
}
