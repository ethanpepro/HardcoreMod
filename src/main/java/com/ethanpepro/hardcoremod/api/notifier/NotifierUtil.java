package com.ethanpepro.hardcoremod.api.notifier;

import com.ethanpepro.hardcoremod.api.network.HardcoreModNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

// Diegetic uses of the Notifier system
public class NotifierUtil {
	public static void pushMessage(@NotNull PlayerEntity player, @NotNull String message) {
		PacketByteBuf buf = PacketByteBufs.create();

		buf.writeString(message);

		ServerPlayNetworking.send((ServerPlayerEntity)player, HardcoreModNetworking.PUSH_MESSAGE, buf);
	}
}
