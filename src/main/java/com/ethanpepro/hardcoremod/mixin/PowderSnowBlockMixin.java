package com.ethanpepro.hardcoremod.mixin;

import com.ethanpepro.hardcoremod.util.clothing.ClothingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin extends Block implements FluidDrainable {
	public PowderSnowBlockMixin(Settings settings) {
		super(settings);
	}
	
	// TODO: Achievement.
	@Inject(method = "canWalkOnPowderSnow(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
	private static void canWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> info) {
		if (entity instanceof LivingEntity livingEntity) {
			ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.FEET);
			
			if ((itemStack.isEmpty() || itemStack.isOf(Items.LEATHER_BOOTS)) && ClothingUtil.entityWearingClothingInClothingDataArray(livingEntity, ClothingUtil.getPowderSnowWalkableClothes())) {
				info.setReturnValue(true);
			}
		}
	}
}
