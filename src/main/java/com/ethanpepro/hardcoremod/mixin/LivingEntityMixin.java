package com.ethanpepro.hardcoremod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	// TODO: Use temperature system to set absolute minimum temperature?
	@Inject(method = "canFreeze()Z", at = @At("HEAD"), cancellable = true)
	private void canFreeze(CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(false);
	}
}
