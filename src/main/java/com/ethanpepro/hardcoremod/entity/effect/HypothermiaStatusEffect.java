package com.ethanpepro.hardcoremod.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class HypothermiaStatusEffect extends StatusEffect {
	public HypothermiaStatusEffect() {
		super(StatusEffectCategory.HARMFUL, 0xA5CDFF);
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (entity instanceof PlayerEntity) {
			entity.damage(DamageSource.GENERIC, 2.0f);
		}
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int i = 40 >> amplifier;
		if (i > 0) {
			return duration % i == 0;
		}

		return true;
	}
}
