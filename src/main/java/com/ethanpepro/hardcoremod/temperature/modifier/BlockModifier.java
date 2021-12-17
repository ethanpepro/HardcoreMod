package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.api.temperature.TemperatureData;
import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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

	private int modifier;
	private int horizontal;
	private int vertical;
	private Object2ObjectOpenHashMap<String, Integer> base = new Object2ObjectOpenHashMap<>();
	private Object2ObjectOpenHashMap<String, Integer> lit = new Object2ObjectOpenHashMap<>();

	public BlockModifier() {
		identifier = new Identifier("hardcoremod", "block");
	}

	@Override
	public void clearResources() {
		modifier = 0;
		horizontal = 0;
		vertical = 0;

		base.clear();
		lit.clear();
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsInt();

		horizontal = root.get("horizontal").getAsInt();
		vertical = root.get("vertical").getAsInt();

		JsonObject baseObject = root.get("base").getAsJsonObject();
		Set<Map.Entry<String, JsonElement>> baseObjectEntries = baseObject.entrySet();

		for (Map.Entry<String, JsonElement> entry : baseObjectEntries) {
			base.put(entry.getKey(), entry.getValue().getAsInt());
		}

		JsonObject litObject = root.get("lit").getAsJsonObject();
		Set<Map.Entry<String, JsonElement>> litObjectEntries = litObject.entrySet();

		for (Map.Entry<String, JsonElement> entry : litObjectEntries) {
			lit.put(entry.getKey(), entry.getValue().getAsInt());
		}
	}

	@Override
	@NotNull
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public float getModifier(@NotNull PlayerEntity player, @NotNull World world, @NotNull BlockPos pos) {
		float sum = 0.0f;

		// TODO: Absolutely needs to be optimized
		// TODO: Blocks should affect temperature exponentially less with each occurrence, how to efficiently keep track of this?
		for (BlockPos search : BlockPos.iterate(pos.add(-horizontal, -vertical, -horizontal), pos.add(horizontal, vertical, horizontal))) {
			BlockState blockState = world.getBlockState(search);
			Block block = blockState.getBlock();
			Identifier blockIdentifier = Registry.BLOCK.getId(block);
			String blockName = blockIdentifier.toString();

			Integer baseValue = base.get(blockName);
			if (baseValue != null) {
				sum += baseValue;
			}

			Integer litValue = lit.get(blockName);
			if (litValue != null && blockState.get(Properties.LIT)) {
				sum += litValue;
			}
		}

		return sum * modifier;
	}
}
