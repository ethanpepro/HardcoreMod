package com.ethanpepro.hardcoremod.client.gui.hud;

import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageHud implements HudRenderCallback {
	private final MinecraftClient client;
	
	private final List<Message> messages;
	
	public MessageHud() {
		client = MinecraftClient.getInstance();
		
		messages = Lists.newArrayList();
	}
	
	public void pushMessage(@NotNull Text message) {
		if (messages.size() >= HardcoreModConfig.accessibility.maxMessages) {
			messages.remove(0);
		}
		
		messages.add(new Message(message, client.inGameHud.getTicks()));
	}
	
	private void processMessages() {
		if (messages.isEmpty()) {
			return;
		}
		
		messages.removeIf(message -> client.inGameHud.getTicks() - message.getTick() >= HardcoreModConfig.accessibility.messageLifetime);
	}
	
	private float calculateAlphaPercentageForAge(float age) {
		float opacity = age / HardcoreModConfig.accessibility.messageLifetime;
		
		opacity = 1.0f - opacity;
		opacity *= 10.0f;
		opacity = MathHelper.clamp(opacity, 0.0f, 1.0f);
		opacity *= opacity;
		
		return opacity;
	}
	
	@Override
	public void onHudRender(MatrixStack matrixStack, float tickDelta) {
		processMessages();
		
		if (!HardcoreModConfig.accessibility.enableMessages) {
			return;
		}
		
		for (int i = 0; i < messages.size(); i++) {
			Message message = messages.get(i);
			Text messageString = message.getMessage();
			
			int age = client.inGameHud.getTicks() - message.getTick();
			if (age >= HardcoreModConfig.accessibility.messageLifetime) {
				continue;
			}
			
			int alpha = (int)(255.0f * calculateAlphaPercentageForAge(age));
			if (alpha <= 4) {
				continue;
			}
			
			int width = (client.getWindow().getScaledWidth() / 2) - (client.textRenderer.getWidth(messageString) / 2);
			int height = 2 + client.textRenderer.fontHeight * i;
			
			client.textRenderer.drawWithShadow(matrixStack, messageString, width, height, HardcoreModConfig.accessibility.messageColor + (alpha << 24));
		}
	}
}
