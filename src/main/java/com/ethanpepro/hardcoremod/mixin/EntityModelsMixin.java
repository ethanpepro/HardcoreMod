package com.ethanpepro.hardcoremod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(EntityModels.class)
public abstract class EntityModelsMixin {
	@Shadow
	@Final
	@Mutable
	private static Dilation ARMOR_DILATION;
	
	@Shadow
	@Final
	@Mutable
	private static Dilation HAT_DILATION;
	
	static {
		ARMOR_DILATION = new Dilation(0.9f);
		HAT_DILATION = new Dilation(0.4f);
	}
}
