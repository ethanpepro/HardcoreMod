package com.ethanpepro.hardcoremod;

import com.ethanpepro.hardcoremod.entity.effect.HardcoreModStatusEffects;
import com.ethanpepro.hardcoremod.item.HardcoreModItems;
import com.ethanpepro.hardcoremod.temperature.data.TemperatureData;
import com.ethanpepro.hardcoremod.temperature.data.registry.TemperatureDataRegistry;
import com.ethanpepro.hardcoremod.temperature.modifier.BaseTemperatureModifier;
import com.ethanpepro.hardcoremod.temperature.modifier.registry.TemperatureModifierRegistry;
import com.ethanpepro.hardcoremod.util.clothing.ClothingUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

// TODO: Refactor build.gradle dependencies.
// TODO: Make our libraries required?

// TODO: Ensure Tweed actually synchronizes between the client and server configs (where server config takes precedence).

public class HardcoreMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("hardcoremod");

	@Override
	public void onInitialize() {
		// TODO: Get this code out of ResourceManagerHelper.
		// TODO: Mods should not be able to modify this. Don't use ResourceManagerHelper?
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return new Identifier("hardcoremod", "temperature_data");
			}

			@Override
			public void reload(ResourceManager manager) {
				TemperatureDataRegistry.clear();

				Identifier file = new Identifier("hardcoremod", "temperature/temperature_data.json");

				try (InputStream stream = manager.getResource(file).getInputStream()) {
					InputStreamReader streamReader = new InputStreamReader(stream);
					JsonReader jsonReader = new JsonReader(streamReader);

					JsonObject root = JsonParser.parseReader(jsonReader).getAsJsonObject();

					Set<Map.Entry<String, JsonElement>> members = root.entrySet();
					for (Map.Entry<String, JsonElement> member : members) {
						String key = member.getKey();

						Gson gson = new Gson();
						JsonObject value = member.getValue().getAsJsonObject();

						TemperatureData data = gson.fromJson(value, TemperatureData.class);

						TemperatureDataRegistry.register(key, data);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// TODO: Ensure this can always be modified.
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return new Identifier("hardcoremod", "temperature_modifiers");
			}

			@Override
			public void reload(ResourceManager manager) {
				for (BaseTemperatureModifier modifier : TemperatureModifierRegistry.getTemperatureModifiers().values()) {
					modifier.clearResources();
					
					String name = modifier.getIdentifier().getPath() + ".json";
					Identifier file = new Identifier("hardcoremod", "temperature/modifier/" + name);
					
					try (InputStream stream = manager.getResource(file).getInputStream()) {
						InputStreamReader streamReader = new InputStreamReader(stream);
						JsonReader jsonReader = new JsonReader(streamReader);

						JsonObject root = JsonParser.parseReader(jsonReader).getAsJsonObject();

						modifier.processResources(root);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		HardcoreModStatusEffects.register();

		HardcoreModItems.register();
		
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return new Identifier("hardcoremod", "clothing_data");
			}
			
			@Override
			public void reload(ResourceManager manager) {
				ClothingUtil.clearClothingData();
				
				Identifier file = new Identifier("hardcoremod", "clothing/clothing_data.json");
				
				try (InputStream stream = manager.getResource(file).getInputStream()) {
					InputStreamReader streamReader = new InputStreamReader(stream);
					JsonReader jsonReader = new JsonReader(streamReader);
					
					JsonObject root = JsonParser.parseReader(jsonReader).getAsJsonObject();
					
					ClothingUtil.processClothingData(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
