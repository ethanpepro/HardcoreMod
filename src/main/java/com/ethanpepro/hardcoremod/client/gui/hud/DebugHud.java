package com.ethanpepro.hardcoremod.client.gui.hud;

import com.ethanpepro.hardcoremod.api.notifier.Message;
import com.ethanpepro.hardcoremod.api.temperature.modifier.BaseTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.modifier.DynamicTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.modifier.StaticTemperatureModifier;
import com.ethanpepro.hardcoremod.api.temperature.registry.TemperatureRegistry;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Dynamic;

import java.util.List;

@Environment(value = EnvType.CLIENT)
public class DebugHud extends DrawableHelper implements HudRenderCallback {
	private final MinecraftClient client;

	public DebugHud() {
		this.client = MinecraftClient.getInstance();
	}

	@Override
	public void onHudRender(MatrixStack matrixStack, float tickDelta) {
		List<String> stringList = Lists.newArrayList();

		for (BaseTemperatureModifier modifier : TemperatureRegistry.getModifiers().values()) {
			if (modifier instanceof StaticTemperatureModifier) {
				stringList.add(String.format("%s [Static]", modifier.getIdentifier()));
			}

			if (modifier instanceof DynamicTemperatureModifier) {
				stringList.add(String.format("%s [Dynamic]", modifier.getIdentifier()));
			}
		}

		for (int i = 0; i < stringList.size(); i++) {
			String string = stringList.get(i);

			this.client.textRenderer.draw(matrixStack, string, 2.0f, 2.0f + i * this.client.textRenderer.fontHeight, 0xe0e0e0);
		}
	}
}
