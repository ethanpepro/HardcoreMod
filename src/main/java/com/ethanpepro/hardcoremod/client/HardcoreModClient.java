package com.ethanpepro.hardcoremod.client;

import com.ethanpepro.hardcoremod.api.network.HardcoreModNetworking;
import com.ethanpepro.hardcoremod.client.gui.hud.HardcoreModHudElements;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(value = EnvType.CLIENT)
public class HardcoreModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HardcoreModHudElements.register();

		ClientPlayNetworking.registerGlobalReceiver(HardcoreModNetworking.PUSH_MESSAGE, (client, handler, buf, responseSender) -> {
			String message = buf.readString();

			client.execute(() -> {
				HardcoreModHudElements.notifierHud.pushMessage(message);
			});
		});
	}
}
