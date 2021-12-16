package com.ethanpepro.hardcoremod.mixin;

import com.ethanpepro.hardcoremod.api.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.components.HardcoreModComponents;
import com.ethanpepro.hardcoremod.components.temperature.TemperatureComponent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud extends DrawableHelper {
	private static final Identifier MOD_GUI_ICONS_TEXTURE = new Identifier("hardcoremod", "textures/gui/icons.png");

	@Shadow
	@Final
	private Random random;

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private int ticks;

	@Shadow
	private int scaledWidth;

	@Shadow
	private int scaledHeight;

	@Shadow
	protected abstract PlayerEntity getCameraPlayer();

	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V", shift = At.Shift.AFTER))
	private void renderTemperatureOrb(MatrixStack matrices, float tickDelta, CallbackInfo info) {
		PlayerEntity playerEntity = getCameraPlayer();

		if (playerEntity != null) {
			int x = scaledWidth / 2 - 8;
			// TODO: Interferes with text display when switching items.
			int offset = (playerEntity.experienceLevel > 0) ? 54 : 48;
			int y = scaledHeight - offset;

			// TODO: Color interpolation with defined colors for freezing and burning.
			// TODO: Make dynamic enough to scale with changes in the temperature range data.
			// TODO: Bounce like the hunger bar a % every tick with faster movements towards extreme temperatures.

			RenderSystem.setShaderTexture(0, MOD_GUI_ICONS_TEXTURE);

			drawTexture(matrices, x, y, 0, 0, 16, 16);
		}
	}
}
