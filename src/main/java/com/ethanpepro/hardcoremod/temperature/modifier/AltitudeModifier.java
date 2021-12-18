package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class AltitudeModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private float modifier;
	private int range;
	private float undergroundModifier;

	public AltitudeModifier() {
		this.identifier = new Identifier("hardcoremod", "altitude");
	}

	private boolean isUnderground(@NotNull World world, @NotNull BlockPos pos) {
		if (pos.getY() >= world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY()) {
			return false;
		}

		for (BlockPos search : BlockPos.iterate(pos.add(-range, 0, -range), pos.add(range, 0, range))) {
			if (world.isSkyVisible(search)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
		range = 0;
		undergroundModifier = 0.0f;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		range = root.get("range").getAsInt();
		undergroundModifier = root.get("undergroundModifier").getAsFloat();
	}

	@Override
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public boolean affectsPlayers() {
		return false;
	}

	@Override
	public float getEnvironmentalModifier(@NotNull World world, @NotNull BlockPos pos) {
		float amount = 0.0f;

		if (isUnderground(world, pos)) {
			int y = pos.getY();
			int surface = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY();

			amount = (float)Math.sqrt(surface - y) * undergroundModifier;
		}

		return amount * modifier;
	}
}
