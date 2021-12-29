package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.HardcoreModExample;
import com.ethanpepro.hardcoremod.item.ClothingItem;
import com.ethanpepro.hardcoremod.item.ClothingType;
import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ClothingModifier implements DynamicTemperatureModifier {
	private final Identifier identifier;
	
	private float modifier;
	private float nakedModifier;
	private float armorModifier;
	private float feetModifier;
	private final Object2ObjectOpenHashMap<Identifier, List<String>> clothingDataMap;
	
	public ClothingModifier() {
		identifier = new Identifier("hardcoremod-example", "clothing");
		
		clothingDataMap = new Object2ObjectOpenHashMap<>();
	}
	
	@Override
	public void clearResources() {
		modifier = 0.0f;
		nakedModifier = 0.0f;
		armorModifier = 0.0f;
		feetModifier = 0.0f;
		clothingDataMap.clear();
	}
	
	// TODO: Cleanup
	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		nakedModifier = root.get("nakedModifier").getAsFloat();
		armorModifier = root.get("armorModifier").getAsFloat();
		feetModifier = root.get("feetModifier").getAsFloat();
		
		Set<Map.Entry<String, JsonElement>> clothingEntries = root.get("clothingDataMap").getAsJsonObject().entrySet();
		
		for (Map.Entry<String, JsonElement> entry : clothingEntries) {
			Identifier name = Identifier.tryParse(entry.getKey());
			
			JsonArray values = entry.getValue().getAsJsonArray();
			
			List<String> list = new ArrayList<>();
			
			for (JsonElement value : values) {
				list.add(value.getAsString());
			}
			
			clothingDataMap.put(name, list);
		}
	}
	
	@Override
	public Identifier getIdentifier() {
		return identifier;
	}
	
	@Override
	public float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos, float temperature) {
		int coldPoints = 0;
		int heatPoints = 0;
		
		boolean hasCloak = false;
		boolean hasFootwear = false;
		boolean isClothingNaked = true;
		boolean isArmorNaked = true;
		
		Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(entity);
		if (optional.isPresent()) {
			TrinketComponent component = optional.get();
			
			for (Pair<SlotReference, ItemStack> itemStackPair : component.getAllEquipped()) {
				ItemStack itemStack = itemStackPair.getRight();
				
				if (itemStack.isEmpty()) {
					continue;
				}
				
				isClothingNaked = false;
				
				Item item = itemStack.getItem();
				
				if (item instanceof ClothingItem clothingItem) {
					if (clothingItem.getClothingType().equals(ClothingType.CLOAK)) {
						hasCloak = true;
					}
					
					if (clothingItem.getClothingType().equals(ClothingType.FEET)) {
						hasFootwear = true;
					}
					
					Identifier clothingItemIdentifier = Registry.ITEM.getId(clothingItem);
					
					if (clothingDataMap.containsKey(clothingItemIdentifier)) {
						List<String> values = clothingDataMap.get(clothingItemIdentifier);
						
						for (String value : values) {
							switch (value) {
								case "addColdPoint":
									coldPoints++;
									break;
								case "addHeatPoint":
									heatPoints++;
									break;
								case "subtractColdPoint":
									coldPoints--;
									break;
								case "subtractHeatPoint":
									heatPoints--;
									break;
								default:
									break;
							}
						}
					}
				}
			}
		}
		
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
			if (equipmentSlot.getType() != EquipmentSlot.Type.ARMOR) {
				continue;
			}
			
			ItemStack itemStack = entity.getEquippedStack(equipmentSlot);
			
			if (itemStack.isEmpty()) {
				continue;
			}
			
			// TODO: Differentiate between clothing naked and armor naked.
			isArmorNaked = false;
			
			if (equipmentSlot.equals(EquipmentSlot.FEET)) {
				hasFootwear = true;
			}
			
			if (itemStack.getItem() instanceof ArmorItem armorItem) {
				ArmorMaterial armorMaterial = armorItem.getMaterial();
				
				if (armorMaterial.equals(ArmorMaterials.LEATHER)) {
					if (temperature < 0.0f) {
						temperature++;
					}
					
					continue;
				}
				
				if (armorMaterial.equals(ArmorMaterials.NETHERITE)) {
					if (temperature > 0.0f) {
						temperature--;
					}
					
					continue;
				}
				
				if (!hasCloak) {
					temperature *= armorModifier;
				}
			}
		}
		
		temperature = TemperatureHelper.clampAndRound(temperature);
		
		if (temperature > 0.0f) {
			temperature -= heatPoints;
		} else if (temperature < 0.0f) {
			temperature += coldPoints;
		}
		
		temperature = TemperatureHelper.clampAndRound(temperature);
		
		HardcoreModExample.LOGGER.info("coldPoints={}, heatPoints={}, hasCloak={}, isClothingNaked={}, isArmorNaked={}, wearingBoots={}, temperature={}", coldPoints, heatPoints, hasCloak, isClothingNaked, isArmorNaked, hasFootwear, temperature);
		
		return temperature * modifier;
	}
}
