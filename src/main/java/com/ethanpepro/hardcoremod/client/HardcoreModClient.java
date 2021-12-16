package com.ethanpepro.hardcoremod.client;

import com.ethanpepro.hardcoremod.HardcoreMod;
import com.ethanpepro.hardcoremod.api.network.HardcoreModNetworking;
import com.ethanpepro.hardcoremod.api.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.client.gui.hud.NotifierHud;
import com.ethanpepro.hardcoremod.components.HardcoreModComponents;
import com.ethanpepro.hardcoremod.components.temperature.TemperatureComponent;
import com.ethanpepro.hardcoremod.item.HardcoreModItemGroup;
import com.ethanpepro.hardcoremod.item.HardcoreModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

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

		// TODO: No more programmer art for the thermometer.
		// TODO: Make thermometers get the player's target temperature in inventory and world temperature in frames and ground.
		// TODO: Use 0.0f instead of 0.5f as our starting mark, look at clock code as a reference.
		FabricModelPredicateProviderRegistry.register(HardcoreModItems.THERMOMETER, new Identifier("hardcoremod", "temperature"), (itemStack, clientWorld, livingEntity, i) -> 0.5f);
	}
}
