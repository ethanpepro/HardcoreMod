package com.ethanpepro.hardcoremod.entity.effect;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// TODO: Temperature death messages
public class HardcoreModStatusEffects {
	public static final HypothermiaStatusEffect HYPOTHERMIA = new HypothermiaStatusEffect();
	public static final HyperthermiaStatusEffect HYPERTHERMIA = new HyperthermiaStatusEffect();

	public static void register() {
		Registry.register(Registry.STATUS_EFFECT, new Identifier("hardcoremod", "hypothermia"), HYPOTHERMIA);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("hardcoremod", "hyperthermia"), HYPERTHERMIA);
	}
}
