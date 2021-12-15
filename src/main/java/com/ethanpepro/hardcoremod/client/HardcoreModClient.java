package com.ethanpepro.hardcoremod.client;

import com.ethanpepro.hardcoremod.api.network.HardcoreModNetworking;
import com.ethanpepro.hardcoremod.client.gui.hud.NotifierHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@Environment(value = EnvType.CLIENT)
public class HardcoreModClient implements ClientModInitializer {
	public static NotifierHud notifierHud;

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(notifierHud = new NotifierHud());

		ClientPlayNetworking.registerGlobalReceiver(HardcoreModNetworking.PUSH_MESSAGE, (client, handler, buf, responseSender) -> {
			String message = buf.readString();

			client.execute(() -> {
				notifierHud.pushMessage(message);
			});
		});
	}
}
