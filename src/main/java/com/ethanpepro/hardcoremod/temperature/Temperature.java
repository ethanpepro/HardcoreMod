package com.ethanpepro.hardcoremod.temperature;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class Temperature implements ComponentV3, AutoSyncedComponent, ServerTickingComponent {
	private final PlayerEntity player;

	public Temperature(PlayerEntity player) {
		this.player = player;
	}

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		return AutoSyncedComponent.super.shouldSyncWith(player);
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
		AutoSyncedComponent.super.writeSyncPacket(buf, recipient);
	}

	@Override
	public void applySyncPacket(PacketByteBuf buf) {
		AutoSyncedComponent.super.applySyncPacket(buf);
	}

	@Override
	public void serverTick() {

	}

	@Override
	public void readFromNbt(NbtCompound tag) {

	}

	@Override
	public void writeToNbt(NbtCompound tag) {

	}
}
