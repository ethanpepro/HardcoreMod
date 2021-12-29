package com.ethanpepro.hardcoremod.client.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class Message {
	private final Text message;
	private final int tick;
	
	public Message(@NotNull Text message, int tick) {
		this.message = message;
		this.tick = tick;
	}
	
	@NotNull
	public Text getMessage() {
		return message;
	}
	
	public int getTick() {
		return tick;
	}
}
