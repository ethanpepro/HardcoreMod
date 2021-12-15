package com.ethanpepro.hardcoremod.client.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@Environment(value = EnvType.CLIENT)
public class HardcoreModHudElements {
	public static NotifierHud notifierHud;
	public static DebugHud debugHud;

	public static void register() {
		HudRenderCallback.EVENT.register(notifierHud = new NotifierHud());
		HudRenderCallback.EVENT.register(debugHud = new DebugHud());
	}
}
