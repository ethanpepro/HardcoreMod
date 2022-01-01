package com.ethanpepro.hardcoremod.mixin;

import com.ethanpepro.hardcoremod.components.HardcoreModComponents;
import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.temperature.data.registry.TemperatureDataRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class MixinInGameHud extends DrawableHelper {
	private static final Identifier MOD_GUI_ICONS_TEXTURE = new Identifier("hardcoremod", "textures/gui/icons.png");

	private static int lerpColors(int a, int b, float percentage){
		int mask1 = 0x00FF00FF;
		int mask2 = 0xFF00FF00;

		int f2 = (int)(256.0f * percentage);
		int f1 = 256 - f2;

		return (a & mask1) * f1 + (b & mask1) * f2 >> 8 & mask1 | ((a & mask2) * f1 + (b & mask2) * f2 >> 8 & mask2);
	}

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
		if (!HardcoreModConfig.accessibility.enableOrbDisplay) {
			return;
		}

		PlayerEntity playerEntity = getCameraPlayer();

		if (playerEntity != null) {
			int x = scaledWidth / 2 - 8;
			// TODO: Mixin renderHeldItemTooltip.
			// TODO: Also interferes with boss health bars.
			int offset = (playerEntity.experienceLevel > 0) ? 54 : 48;
			int y = scaledHeight - offset;

			int temperature = HardcoreModComponents.TEMPERATURE.get(playerEntity).getTemperature();
			float percentage = Math.abs(TemperatureHelper.convertTemperatureToAbsoluteRangeRatio(temperature));

			if (HardcoreModConfig.accessibility.enableOrbMovements) {
				int interval = (int)(HardcoreModConfig.accessibility.orbMovementMaximumTime * (float)Math.pow(1.0f / HardcoreModConfig.accessibility.orbMovementMaximumTime, percentage));

				if (ticks % interval == 0) {
					y += random.nextInt(3) - 1;
				}
			}

			// TODO: Better color interpolation.
			int color = lerpColors(0xffffff, temperature > TemperatureDataRegistry.getEquilibriumTemperature() ? HardcoreModConfig.temperature.burningTemperatureColor : HardcoreModConfig.temperature.freezingTemperatureColor, percentage);
			
			float r = (float)(color >> 16 & 0xFF) / 255.0f;
			float g = (float)(color >> 8 & 0xFF) / 255.0f;
			float b = (float)(color & 0xFF) / 255.0f;

			RenderSystem.setShaderColor(r, g, b, 1.0f);

			RenderSystem.setShaderTexture(0, MOD_GUI_ICONS_TEXTURE);

			drawTexture(matrices, x, y, 0, 0, 16, 16);

			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
}
