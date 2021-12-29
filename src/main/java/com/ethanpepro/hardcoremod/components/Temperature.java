package com.ethanpepro.hardcoremod.components;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import com.ethanpepro.hardcoremod.entity.effect.HardcoreModStatusEffects;
import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.util.message.MessageUtil;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Temperature implements ComponentV3, AutoSyncedComponent, ServerTickingComponent {
	private final PlayerEntity player;

	private int temperatureTarget;
	private int temperatureTargetTimer;

	private int temperature;
	private int temperatureTimer;

	public Temperature(PlayerEntity player) {
		this.player = player;

		temperatureTarget = 0;
		temperatureTargetTimer = 0;

		temperature = 0;
		temperatureTimer = 0;
	}

	// TODO: Bias amount when current temperature is closer to an extreme target temperature.
	// TODO: Feed the unclamped temperature value into this?
	private int getTemperatureUpdateThreshold() {
		int updateRange = HardcoreModConfig.temperature.maximumTemperatureThreshold - HardcoreModConfig.temperature.minimumTemperatureThreshold;
		int currentRange = Math.abs(temperature - temperatureTarget);
		
		int temperatureRange = 0;
		
		if (temperatureTarget > 0) {
			temperatureRange = TemperatureHelper.getAbsoluteMaximumTemperature() - TemperatureHelper.getEquilibriumTemperature();
		} else {
			temperatureRange = TemperatureHelper.getEquilibriumTemperature() - TemperatureHelper.getAbsoluteMinimumTemperature();
		}
		
		int adjustedUpdateRange = HardcoreModConfig.temperature.maximumTemperatureThreshold - (HardcoreModConfig.temperature.maximumTemperatureThreshold * Math.abs(temperature / temperatureRange));
		
		return Math.max(HardcoreModConfig.temperature.minimumTemperatureThreshold, adjustedUpdateRange - ((currentRange * updateRange) / temperatureRange));
	}

	private int calculateTargetTemperatureForPlayer(@NotNull PlayerEntity player) {
		float target = TemperatureHelper.calculateTemperature(player, player.getEntityWorld(), player.getBlockPos());

		return TemperatureHelper.clampAndRound(target);
	}

	private void onUpdateTemperature() {
		// TODO: A smarter system that compares if the body's current temperature is in a different range than the target temperature.
		Text message = TemperatureHelper.getFlavorTextForTemperature(player.world, temperatureTarget);
		
		Objects.requireNonNull(message);
		
		MessageUtil.pushMessage(player, message);
	}

	private void applyStatusEffects() {
		String[] conditions = TemperatureHelper.getConditionsForTemperature(temperature);

		// TODO: Not like this.
		if (Objects.nonNull(conditions)) {
			for (String condition : conditions) {
				switch (condition) {
					case "hypothermia":
						player.addStatusEffect(new StatusEffectInstance(HardcoreModStatusEffects.HYPOTHERMIA, HardcoreModConfig.temperature.maximumTemperatureThreshold, 0));
						break;
					case "hyperthermia":
						player.addStatusEffect(new StatusEffectInstance(HardcoreModStatusEffects.HYPERTHERMIA, HardcoreModConfig.temperature.maximumTemperatureThreshold, 0));
						break;
					case "default":
						player.removeStatusEffect(HardcoreModStatusEffects.HYPOTHERMIA);
						player.removeStatusEffect(HardcoreModStatusEffects.HYPERTHERMIA);
						break;
					default:
						break;
				}
			}
		}
	}

	// TODO: Need to override freezing mechanics.
	public int getTemperature() {
		return temperature;
	}

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		// TODO: Find all edge cases. This should sync with the player and spectators but should also save precious cycles if we don't have to always sync.
		return this.player == player || this.player == player.getCameraEntity();
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
		buf.writeVarInt(temperature);
		buf.writeVarInt(temperatureTimer);
	}

	@Override
	public void applySyncPacket(PacketByteBuf buf) {
		temperature = buf.readVarInt();
		temperatureTimer = buf.readVarInt();

		// TODO: We always synchronize the same information, do we need this?
		// TODO: We don't sync because this is the sync packet, right?
	}

	@Override
	public void serverTick() {
		if (!HardcoreModConfig.temperature.enabled) {
			return;
		}

		if (player.world.getDifficulty() == Difficulty.PEACEFUL) {
			return;
		}
		
		// TODO: Prevent player from sleeping if too hot or cold?
		if (player.isSleeping()) {
			return;
		}

		if (player.isSpectator() || player.isCreative()) {
			return;
		}

		temperatureTargetTimer++;
		temperatureTimer++;

		if (temperatureTargetTimer >= HardcoreModConfig.temperature.targetThreshold) {
			temperatureTargetTimer = 0;

			temperatureTarget = calculateTargetTemperatureForPlayer(player);
			
			HardcoreMod.LOGGER.info("{} -> {} ({}/{} ticks)", temperature, temperatureTarget, temperatureTimer, getTemperatureUpdateThreshold());
		}

		if (temperatureTimer >= getTemperatureUpdateThreshold()) {
			temperatureTimer = 0;

			if (temperature != temperatureTarget) {
				if (temperature < temperatureTarget) {
					temperature++;
				} else {
					temperature--;
				}

				onUpdateTemperature();
			}

			applyStatusEffects();
		}

		// TODO: We're always syncing every game tick because of temperatureTimer, find a way around this.
		HardcoreModComponents.TEMPERATURE.sync(player);
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		temperature = tag.getInt("temperature");
		temperatureTimer = tag.getInt("temperatureTimer");

		HardcoreModComponents.TEMPERATURE.sync(player);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("temperature", temperature);
		tag.putInt("temperatureTimer", temperatureTimer);
	}
}
