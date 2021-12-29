package com.ethanpepro.hardcoremod.item;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

// TODO: How should clothing be damaged?
public class ClothingItem extends TrinketItem implements DyeableItem {
	private final ClothingType clothingType;
	private final int protection;
	
	public ClothingItem(ClothingType clothingType, int protection, FabricItemSettings settings) {
		super(settings.maxDamageIfAbsent(clothingType.getDurability()));
		
		this.clothingType = clothingType;
		this.protection = protection;
	}
	
	public ClothingType getClothingType() {
		return clothingType;
	}
	
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
		
		if (protection > 0) {
			modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Armor modifier", protection, EntityAttributeModifier.Operation.ADDITION));
		}
		
		return modifiers;
	}
	
	@Override
	public int getColor(ItemStack stack) {
		NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
		return nbtCompound != null && nbtCompound.contains(COLOR_KEY, 99) ? nbtCompound.getInt(COLOR_KEY) : 0xffffff;
	}
}
