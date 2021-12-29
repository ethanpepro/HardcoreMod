package com.ethanpepro.hardcoremod.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class HardcoreModItemGroup {
	public static final ItemGroup TOOLS = FabricItemGroupBuilder.create(new Identifier("hardcoremod", "tools")).icon(() -> new ItemStack(HardcoreModItems.THERMOMETER)).build();
	public static final ItemGroup CLOTHING = FabricItemGroupBuilder.create(new Identifier("hardcoremod", "clothing")).icon(() -> new ItemStack(HardcoreModItems.YARN)).build();
}
