package com.ethanpepro.hardcoremod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HardcoreModItems {
	public static final Item THERMOMETER = new Item(new FabricItemSettings().maxCount(1).group(HardcoreModItemGroup.TOOLS));
	public static final Item YARN = new Item(new FabricItemSettings().maxCount(16).group(HardcoreModItemGroup.CLOTHING));
	public static final Item LEATHER_STRIPS = new Item(new FabricItemSettings().maxCount(16).group(HardcoreModItemGroup.CLOTHING));

	public static void register() {
		Registry.register(Registry.ITEM, new Identifier("hardcoremod", "thermometer"), THERMOMETER);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod", "yarn"), YARN);
		Registry.register(Registry.ITEM, new Identifier("hardcoremod", "leather_strips"), LEATHER_STRIPS);
	}
}
