package com.ethanpepro.hardcoremod.temperature.modifier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class BlockModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private final Object2ObjectOpenHashMap<Identifier, Integer> cache;

	private float modifier;
	private int searchHorizontal;
	private int searchVertical;
	private final Object2ObjectOpenHashMap<Identifier, Integer> defaultBlockMap;
	private final Object2ObjectOpenHashMap<Identifier, Integer> litBlockMap;

	public BlockModifier() {
		identifier = new Identifier("hardcoremod-example", "block");
		
		cache = new Object2ObjectOpenHashMap<>();

		defaultBlockMap = new Object2ObjectOpenHashMap<>();
		litBlockMap = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
		searchHorizontal = 0;
		searchVertical = 0;
		defaultBlockMap.clear();
		litBlockMap.clear();
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		searchHorizontal = root.get("searchHorizontal").getAsInt();
		searchVertical = root.get("searchVertical").getAsInt();

		Set<Map.Entry<String, JsonElement>> defaultBlockEntries = root.get("defaultBlockMap").getAsJsonObject().entrySet();

		for (Map.Entry<String, JsonElement> entry : defaultBlockEntries) {
			Identifier name = Identifier.tryParse(entry.getKey());

			defaultBlockMap.put(name, entry.getValue().getAsInt());
		}

		Set<Map.Entry<String, JsonElement>> litBlockEntries = root.get("litBlockMap").getAsJsonObject().entrySet();

		for (Map.Entry<String, JsonElement> entry : litBlockEntries) {
			Identifier name = Identifier.tryParse(entry.getKey());

			litBlockMap.put(name, entry.getValue().getAsInt());
		}
	}

	@Override
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos) {
		if (shouldNotRun(world)) {
			return 0.0f;
		}

		float sum = 0.0f;

		for (BlockPos search : BlockPos.iterate(pos.add(-searchHorizontal, -searchVertical, -searchHorizontal), pos.add(searchHorizontal, searchVertical, searchHorizontal))) {
			BlockState blockState = world.getBlockState(search);
			
			if (blockState.isAir()) {
				continue;
			}
			
			Block block = blockState.getBlock();
			Identifier blockIdentifier = Registry.BLOCK.getId(block);

			Integer defaultValue = defaultBlockMap.get(blockIdentifier);
			if (defaultValue != null) {
				int value = cache.getOrDefault(blockIdentifier, 0) + 1;
				
				cache.put(blockIdentifier, value);
				sum += defaultValue * (1.0f / value);
				continue;
			}

			Integer litValue = litBlockMap.get(blockIdentifier);
			if (litValue != null && blockState.get(Properties.LIT)) {
				int value = cache.getOrDefault(blockIdentifier, 0) + 1;
				
				cache.put(blockIdentifier, value);
				sum += litValue * (1.0f / value);
				continue;
			}
		}
		
		cache.clear();

		return sum * modifier;
	}
}
