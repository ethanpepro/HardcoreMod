package com.ethanpepro.hardcoremod.client.gui.hud;

import com.ethanpepro.hardcoremod.api.notifier.Message;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(value = EnvType.CLIENT)
public class NotifierHud extends DrawableHelper implements HudRenderCallback {
	private final MinecraftClient client;

	private final List<Message> messages;

	public NotifierHud() {
		this.client = MinecraftClient.getInstance();

		this.messages = Lists.newArrayList();
	}

	public void pushMessage(@NotNull String message) {
		if (this.messages.size() >= 5) {
			this.messages.remove(0);
		}

		this.messages.add(new Message(Language.getInstance().get(message), this.client.inGameHud.getTicks()));
	}

	private void purgeMessages() {
		if (this.messages.isEmpty()) {
			return;
		}

		this.messages.removeIf(message -> this.client.inGameHud.getTicks() - message.getTick() >= 200);
	}

	private float calculateOpacityPercentageForAge(float age) {
		float opacity = age / 200.0f;

		opacity = 1.0f - opacity;
		opacity *= 10.0f;
		opacity = MathHelper.clamp(opacity, 0.0f, 1.0f);
		opacity *= opacity;

		return opacity;
	}

	@Override
	public void onHudRender(MatrixStack matrixStack, float tickDelta) {
		purgeMessages();

		List<Message> messageList = this.messages;

		for (int i = 0; i < messageList.size(); i++) {
			Message message = messageList.get(i);
			String string = message.getMessage();

			int age = this.client.inGameHud.getTicks() - message.getTick();
			if (age >= 200) {
				continue;
			}

			float percentage = calculateOpacityPercentageForAge(age);

			int alpha = (int)(255.0f * percentage);
			if (alpha <= 4) {
				continue;
			}

			this.client.textRenderer.drawWithShadow(matrixStack, string, (this.client.getWindow().getScaledWidth() * 0.5f) - (this.client.textRenderer.getWidth(string) * 0.5f), 2.0f + this.client.textRenderer.fontHeight * i, 0xffef86 + (alpha << 24));
		}
	}
}
