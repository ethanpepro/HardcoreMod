package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.HardcoreModExample;
import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import com.ethanpepro.hardcoremod.entity.effect.HardcoreModStatusEffects;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	private boolean shouldRust;
	private float rustChance;
	private int rustMaxDamage;
	private final List<String> rustMaterials;
	private final Object2ObjectOpenHashMap<Identifier, List<String>> clothingDataMap;
	
	public ClothingModifier() {
		identifier = new Identifier("hardcoremod-example", "clothing");
		
		rustMaterials = Lists.newArrayList();
		clothingDataMap = new Object2ObjectOpenHashMap<>();
	}
	
	private boolean calculateRustDamageChance(Random random) {
		return random.nextFloat() < rustChance;
	}
	
	@Override
	public void clearResources() {
		modifier = 0;
		shouldRust = false;
		rustChance = 0;
		rustMaxDamage = 0;
		rustMaterials.clear();
		clothingDataMap.clear();
	}
	
	// TODO: Cleanup
	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		shouldRust = root.get("shouldRust").getAsBoolean();
		rustChance = root.get("rustChance").getAsFloat();
		rustMaxDamage = root.get("rustMaxDamage").getAsInt();
		
		JsonArray rustMaterialsArray = root.get("rustMaterials").getAsJsonArray();
		
		for (JsonElement element : rustMaterialsArray) {
			rustMaterials.add(element.getAsString());
		}
		
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
	
	// TODO: These should technically only be calculating a single number, the temperature modifier. But performance demands we do it here.
	@Override
	public float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos, float temperature) {
		// TODO: Get rid of.
		Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(entity);
		
		if (optional.isEmpty()) {
			return temperature;
		}
		
		TrinketComponent component = optional.get();
		
		boolean hasRustResist = false;
		
		List<Pair<SlotReference, ItemStack>> equippedClothingPairs = component.getAllEquipped();
		
		for (Pair<SlotReference, ItemStack> clothingPair : equippedClothingPairs) {
			ItemStack clothingItemStack = clothingPair.getRight();
			Item clothingItem = clothingItemStack.getItem();
			Identifier clothingItemIdentifier = Registry.ITEM.getId(clothingItem);
			if (clothingDataMap.containsKey(clothingItemIdentifier)) {
				List<String> values = clothingDataMap.get(clothingItemIdentifier);
				
				for (String value : values) {
					switch (value) {
						case "resistsCold":
							break;
						case "resistsHeat":
							break;
						case "preventsRust":
							hasRustResist = true;
							break;
						default:
							break;
					}
				}
			}
		}
		
		// TODO: Move to PlayerEntityMixin?
		if (shouldRust && entity.isWet() && !hasRustResist) {
			for (ItemStack armorItemStack : entity.getArmorItems()) {
				if (armorItemStack.getItem() instanceof ArmorItem armorItem) {
					if (rustMaterials.contains(armorItem.getMaterial().getName())) {
						Random random = world.getRandom();
						
						if (calculateRustDamageChance(random)) {
							armorItemStack.damage(random.nextInt(rustMaxDamage + 1), entity, callback -> callback.sendEquipmentBreakStatus(armorItem.getSlotType()));
							
							// TODO: Notify the player their armor is being damaged!
						}
					}
				}
			}
		}
		
		// TODO: If temperature > 0, cap temperature out at burning - 1. Then use score.
		// TODO: If temperature < 0, cap temperature out at freezing + 1. Then use score.
		
		return temperature;
	}
}
