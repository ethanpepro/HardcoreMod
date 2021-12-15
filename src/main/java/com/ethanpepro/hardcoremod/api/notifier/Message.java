package com.ethanpepro.hardcoremod.api.notifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

@Environment(value = EnvType.CLIENT)
public class Message {
	private final String message;
	private final int tick;

	public Message(@NotNull String message, int tick) {
		this.message = message;
		this.tick = tick;
	}

	@NotNull
	public String getMessage() {
		return message;
	}

	public int getTick() {
		return tick;
	}
}
