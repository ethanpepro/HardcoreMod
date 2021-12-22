package com.ethanpepro.hardcoremod.item;

import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.DyeableItem;

// TODO: Move to main?
public class ClothingItem extends TrinketItem implements DyeableItem {
	public ClothingItem(ClothingType material, FabricItemSettings settings) {
		super(settings.maxDamageIfAbsent(material.getDurability()));
	}
}
