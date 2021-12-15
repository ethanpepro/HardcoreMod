package com.ethanpepro.hardcoremod.components.temperature;

import com.ethanpepro.hardcoremod.api.notifier.NotifierUtil;
import com.ethanpepro.hardcoremod.api.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.components.HardcoreModComponents;
import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TemperatureComponent implements ComponentV3, AutoSyncedComponent, ServerTickingComponent {
	private final PlayerEntity player;

	private int temperatureLevelCurrent;
	private int temperatureLevelCurrentTimer;

	private int temperatureLevelTarget;
	private int temperatureLevelTargetTimer;

	public TemperatureComponent(PlayerEntity player) {
		this.player = player;

		this.temperatureLevelCurrent = 0;
		this.temperatureLevelCurrentTimer = 0;

		this.temperatureLevelTarget = 0;
		this.temperatureLevelTargetTimer = 0;
	}

	private int getCurrentTemperatureUpdateThreshold() {
		int updateRange = HardcoreModConfig.getMaximumTargetTemperatureUpdateThreshold() - HardcoreModConfig.getMinimumTargetTemperatureUpdateThreshold();

		int temperatureRange = (this.temperatureLevelTarget > 0) ? TemperatureHelper.getAbsoluteMaximumTemperature() - TemperatureHelper.getEquilibriumTemperature() : TemperatureHelper.getEquilibriumTemperature() - TemperatureHelper.getAbsoluteMinimumTemperature();

		int currentRange = Math.abs(this.temperatureLevelCurrent - this.temperatureLevelTarget);

		return Math.max(HardcoreModConfig.getMinimumTargetTemperatureUpdateThreshold(), HardcoreModConfig.getMaximumTargetTemperatureUpdateThreshold() - (currentRange * updateRange) / temperatureRange);
	}

	private int calculateTargetTemperatureForPlayer(@NotNull PlayerEntity player) {
		float target = TemperatureHelper.calculateTemperatureTarget(player, player.getEntityWorld(), player.getBlockPos());

		return TemperatureHelper.clampTemperature(target);
	}

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		// TODO: Find all edge cases. This should sync with the player and spectators but should also save precious cycles if we don't have to always sync.
		return this.player == player || this.player == player.getCameraEntity();
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
		buf.writeVarInt(this.temperatureLevelCurrent);
		buf.writeVarInt(this.temperatureLevelCurrentTimer);
	}

	@Override
	public void applySyncPacket(PacketByteBuf buf) {
		this.temperatureLevelCurrent = buf.readVarInt();
		this.temperatureLevelCurrentTimer = buf.readVarInt();

		// TODO: We always synchronize the same information, do we need this?
		// TODO: We don't sync because this is the sync packet, right?
	}

	@Override
	public void serverTick() {
		if (!HardcoreModConfig.isTemperatureSystemEnabled()) {
			return;
		}

		// TODO: Filter out players who would not be affected by temperature

		this.temperatureLevelTargetTimer++;

		if (this.temperatureLevelTargetTimer >= HardcoreModConfig.getTargetTemperatureUpdateThreshold()) {
			this.temperatureLevelTargetTimer = 0;

			this.temperatureLevelTarget = this.calculateTargetTemperatureForPlayer(this.player);
		}

		this.temperatureLevelCurrentTimer++;

		if (this.temperatureLevelCurrentTimer >= this.getCurrentTemperatureUpdateThreshold()) {
			this.temperatureLevelCurrentTimer = 0;

			if (this.temperatureLevelCurrent != this.temperatureLevelTarget) {
				this.temperatureLevelCurrent += Integer.signum(this.temperatureLevelTarget - this.temperatureLevelCurrent);
			}

			// TODO: Apply status effects

			String message = TemperatureHelper.getFlavorTextForTemperature(this.player.world, temperatureLevelCurrent);

			Objects.requireNonNull(message);

			NotifierUtil.pushMessage(player, message);

			HardcoreModComponents.TEMPERATURE.sync(this.player);
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		this.temperatureLevelCurrent = tag.getInt("temperatureLevelCurrent");
		this.temperatureLevelCurrentTimer = tag.getInt("temperatureLevelCurrentTimer");

		HardcoreModComponents.TEMPERATURE.sync(this.player);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("temperatureLevelCurrent", this.temperatureLevelCurrent);
		tag.putInt("temperatureLevelCurrentTimer", this.temperatureLevelCurrentTimer);
	}
}
