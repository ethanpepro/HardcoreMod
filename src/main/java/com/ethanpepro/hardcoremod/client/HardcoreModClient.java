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
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

		// TODO: Need to associate a thermometer with its position and cache its value, updating every configurable interval
		// TODO: No more programmer art for the thermometer.
		FabricModelPredicateProviderRegistry.register(HardcoreModItems.THERMOMETER, new Identifier("hardcoremod", "temperature"), (itemStack, clientWorld, livingEntity, i) -> {
			float temperature = 0.0f;

			if (livingEntity instanceof PlayerEntity) {
				temperature = TemperatureHelper.calculateTemperature((PlayerEntity)livingEntity, livingEntity.getEntityWorld(), livingEntity.getBlockPos());
			}

			ItemFrameEntity frame = itemStack.getFrame();
			if (frame != null) {
				temperature = TemperatureHelper.calculateTemperature(null, frame.getEntityWorld(), frame.getBlockPos());
			}

			return TemperatureHelper.convertTemperatureToAbsoluteRangeRatio(temperature);
		});
	}
}
