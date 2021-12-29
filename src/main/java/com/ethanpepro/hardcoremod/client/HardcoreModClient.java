package com.ethanpepro.hardcoremod.client;

import com.ethanpepro.hardcoremod.client.gui.hud.MessageHud;
import com.ethanpepro.hardcoremod.client.item.ThermometerModelPredicateProvider;
import com.ethanpepro.hardcoremod.item.HardcoreModItems;
import com.ethanpepro.hardcoremod.util.message.MessageUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HardcoreModClient implements ClientModInitializer {
	public static MessageHud messageHud;
	public static ThermometerModelPredicateProvider thermometerModelPredicateProvider;

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(messageHud = new MessageHud());
		
		ClientPlayNetworking.registerGlobalReceiver(MessageUtil.PUSH_MESSAGE, (client, handler, buf, responseSender) -> {
			Text message = buf.readText();
			
			client.execute(() -> messageHud.pushMessage(message));
		});
		
		// TODO: No more programmer art for the thermometer.
		FabricModelPredicateProviderRegistry.register(HardcoreModItems.THERMOMETER, new Identifier("hardcoremod", "temperature"), thermometerModelPredicateProvider = new ThermometerModelPredicateProvider());
		
		ClientTickEvents.END_WORLD_TICK.register(world -> thermometerModelPredicateProvider.cacheThink());
	}
}
