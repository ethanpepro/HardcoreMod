package com.ethanpepro.hardcoremod.util.clothing;

import com.ethanpepro.hardcoremod.item.ClothingItem;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ClothingUtil {
	// Weathering-protective clothes.
	private static final List<Item> weatheringProtectiveClothes;
	
	// Powder-snow-walkable clothes.
	private static final List<Item> powderSnowWalkableClothes;
	
	private static void processClothingDataArray(@NotNull JsonObject root, @NotNull String name, @NotNull List<Item> list) {
		JsonArray array = root.get(name).getAsJsonArray();
		
		for (JsonElement element : array) {
			Identifier identifier = Identifier.tryParse(element.getAsString());
			Item item = Registry.ITEM.get(identifier);
			
			if (item instanceof ClothingItem clothingItem) {
				if (!list.contains(clothingItem)) {
					list.add(clothingItem);
				}
			}
		}
	}
	
	public static void processClothingData(@NotNull JsonObject root) {
		processClothingDataArray(root, "weatheringProtectiveClothes", weatheringProtectiveClothes);
		processClothingDataArray(root, "powderSnowWalkableClothes", powderSnowWalkableClothes);
	}
	
	public static void clearClothingData() {
		weatheringProtectiveClothes.clear();
		powderSnowWalkableClothes.clear();
	}
	
	@NotNull
	public static ImmutableList<Item> getWeatheringProtectiveClothes() {
		return ImmutableList.copyOf(weatheringProtectiveClothes);
	}
	
	@NotNull
	public static ImmutableList<Item> getPowderSnowWalkableClothes() {
		return ImmutableList.copyOf(powderSnowWalkableClothes);
	}
	
	public static boolean entityWearingClothingInClothingDataArray(@NotNull LivingEntity entity, @NotNull ImmutableList<Item> list) {
		Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(entity);
		if (optional.isPresent()) {
			TrinketComponent component = optional.get();
			
			for (Pair<SlotReference, ItemStack> clothingPair : component.getAllEquipped()) {
				ItemStack clothingItemStack = clothingPair.getRight();
				Item clothingItem = clothingItemStack.getItem();
				
				if (list.contains(clothingItem)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static {
		weatheringProtectiveClothes = Lists.newArrayList();
		powderSnowWalkableClothes = Lists.newArrayList();
	}
}
