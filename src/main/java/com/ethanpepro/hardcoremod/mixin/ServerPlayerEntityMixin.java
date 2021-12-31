package com.ethanpepro.hardcoremod.mixin;

import com.ethanpepro.hardcoremod.components.HardcoreModComponents;
import com.ethanpepro.hardcoremod.components.Temperature;
import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import com.ethanpepro.hardcoremod.entity.effect.HardcoreModStatusEffects;
import com.ethanpepro.hardcoremod.temperature.TemperatureHelper;
import com.ethanpepro.hardcoremod.util.clothing.ClothingUtil;
import com.ethanpepro.hardcoremod.util.message.MessageUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;
import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	private int counter;
	
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}
	
	@Inject(method = "tick()V", at = @At(value = "TAIL"))
	private void tick(CallbackInfo info) {
		if (!HardcoreModConfig.rust.enableRust) {
			return;
		}
		
		if (!TemperatureHelper.shouldRun(this)) {
			return;
		}
		
		if (this.isSleeping()) {
			return;
		}
		
		counter++;
		
		if (counter >= HardcoreModConfig.rust.rustCheckInterval) {
			counter = 0;
			
			if (ClothingUtil.entityWearingClothingInClothingDataArray(this, ClothingUtil.getWeatheringProtectiveClothes())) {
				return;
			}
			
			if (this.isWet()) {
				for (ItemStack armorItemStack : this.getArmorItems()) {
					if (armorItemStack.getItem() instanceof ArmorItem armorItem) {
						if (HardcoreModConfig.rust.materialsThatRust.contains(armorItem.getMaterial().getName())) {
							Random random = this.getEntityWorld().getRandom();
							
							if (random.nextFloat() < HardcoreModConfig.rust.rustChance) {
								armorItemStack.damage(random.nextInt(HardcoreModConfig.rust.rustMaxDamage + 1), this, callback -> callback.sendEquipmentBreakStatus(armorItem.getSlotType()));
								
								MessageUtil.pushMessage(this, new TranslatableText("hardcoremod.temperature.rust", armorItemStack.getName()));
								
								break;
							}
						}
					}
				}
			}
		}
	}
	
	@Inject(method = "trySleep", at = @At(value = "HEAD"), cancellable = true)
	private void trySleep(BlockPos pos, CallbackInfoReturnable<Either<SleepFailureReason, Unit>> info) {
		if (!HardcoreModConfig.temperature.enabled) {
			return;
		}
		
		if (!TemperatureHelper.shouldRun(this)) {
			return;
		}
		
		Temperature component = HardcoreModComponents.TEMPERATURE.get(this);
		
		Objects.requireNonNull(component);
		
		int temperature = component.getTemperature();
		
		boolean cannotSleepHere = false;
		String[] conditions = TemperatureHelper.getConditionsForTemperature(temperature);
		
		Objects.requireNonNull(conditions);
		
		for (String condition : conditions) {
			switch (condition) {
				case "hypothermia":
				case "hyperthermia":
					cannotSleepHere = true;
					break;
				default:
					break;
			}
		}
		
		if (cannotSleepHere) {
			info.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.OTHER_PROBLEM));
			
			MessageUtil.pushMessage(this, new TranslatableText("hardcoremod.temperature.too_extreme"));
		}
	}
}
