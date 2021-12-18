package com.ethanpepro.hardcoremod;

import com.ethanpepro.hardcoremod.api.temperature.TemperatureData;
import com.ethanpepro.hardcoremod.api.temperature.modifier.BaseTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureDataRegistry;
import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import com.ethanpepro.hardcoremod.entity.effect.HardcoreModStatusEffects;
import com.ethanpepro.hardcoremod.item.HardcoreModItems;
import com.ethanpepro.hardcoremod.temperature.HardcoreModTemperatures;
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
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return new Identifier("hardcoremod", "temperature_data");
			}

			@Override
			public void reload(ResourceManager manager) {
				TemperatureDataRegistry.clear();

				Identifier file = new Identifier("hardcoremod", "temperature/temperatures.json");

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

				TemperatureDataRegistry.finish();
			}
		});

		HardcoreModTemperatures.register();

		// TODO: How would this work for other mods?
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return new Identifier("hardcoremod", "temperature_resources");
			}

			@Override
			public void reload(ResourceManager manager) {
				for (BaseTemperatureModifier modifier : TemperatureRegistry.getModifiers().values()) {
					modifier.clearResources();
				}

				for (BaseTemperatureModifier modifier : TemperatureRegistry.getModifiers().values()) {
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
	}
}
