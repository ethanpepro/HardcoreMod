package com.ethanpepro.hardcoremod.temperature.modifier;

import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class AltitudeModifier implements StaticTemperatureModifier {
	private final Identifier identifier;

	private float modifier;
	private int searchHorizontal;
	private float undergroundModifier;
	private float altitudeConstant;
	private float altitudeBase;

	public AltitudeModifier() {
		this.identifier = new Identifier("hardcoremod-example", "altitude");
	}

	private boolean isUnderground(@NotNull World world, @NotNull BlockPos pos) {
		if (pos.getY() >= world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY()) {
			return false;
		}

		for (BlockPos search : BlockPos.iterate(pos.add(-searchHorizontal, 0, -searchHorizontal), pos.add(searchHorizontal, 0, searchHorizontal))) {
			if (world.isSkyVisible(search)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void clearResources() {
		modifier = 0.0f;
		searchHorizontal = 0;
		undergroundModifier = 0.0f;
		altitudeConstant = 0.0f;
		altitudeBase = 0.0f;
	}

	@Override
	public void processResources(@NotNull JsonObject root) {
		modifier = root.get("modifier").getAsFloat();
		searchHorizontal = root.get("searchHorizontal").getAsInt();
		undergroundModifier = root.get("undergroundModifier").getAsFloat();
		altitudeConstant = root.get("altitudeConstant").getAsFloat();
		altitudeBase = root.get("altitudeBase").getAsFloat();
	}

	@Override
	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public float getModifier(@NotNull LivingEntity entity, @NotNull World world, @NotNull BlockPos pos) {
		if (!TemperatureHelper.shouldTemperatureModifierRun(modifier, world)) {
			return 0.0f;
		}

		float amount = 0.0f;
		int y = pos.getY();

		if (isUnderground(world, pos)) {
			amount = (float)Math.sqrt(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() - y) * undergroundModifier;
		} else if (y > world.getSeaLevel() + 1) {
			amount = altitudeConstant * (float)Math.pow(altitudeBase, world.getTopY() - y);
		}

		return Math.round(amount) * modifier;
	}
}
