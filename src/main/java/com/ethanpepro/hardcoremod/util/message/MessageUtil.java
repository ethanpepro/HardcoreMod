package com.ethanpepro.hardcoremod.util.message;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class MessageUtil {
	public static final Identifier PUSH_MESSAGE = new Identifier("hardcoremod", "push_message");
	
	public static void pushMessage(@NotNull PlayerEntity player, @NotNull Text message) {
		if (player.getEntityWorld().isClient()) {
			return;
		}
		
		PacketByteBuf buf = PacketByteBufs.create();
		
		buf.writeText(message);
		
		if (player instanceof ServerPlayerEntity serverPlayer) {
			ServerPlayNetworking.send(serverPlayer, PUSH_MESSAGE, buf);
		}
	}
}
