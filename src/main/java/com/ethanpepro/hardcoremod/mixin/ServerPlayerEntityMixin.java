package com.ethanpepro.hardcoremod.mixin;

import com.ethanpepro.hardcoremod.config.HardcoreModConfig;
import com.ethanpepro.hardcoremod.util.clothing.ClothingUtil;
import com.ethanpepro.hardcoremod.util.message.MessageUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	private int counter;
	
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}
	
	@Shadow
	public abstract World getWorld();
	
	@Inject(method = "tick()V", at = @At(value = "TAIL"))
	private void tick(CallbackInfo info) {
		if (!HardcoreModConfig.rust.enableRust) {
			return;
		}
		
		World world = this.getWorld();
		
		if (world.getDifficulty() == Difficulty.PEACEFUL) {
			return;
		}
		
		if (this.isSleeping()) {
			return;
		}
		
		if (this.isSpectator() || this.isCreative()) {
			return;
		}
		
		counter++;
		
		if (counter >= HardcoreModConfig.rust.rustCheckInterval) {
			counter = 0;
			
			if (ClothingUtil.entityWearingClothingInClothingDataArray(this, ClothingUtil.getWeatheringProtectiveClothes())) {
				return;
			}
			
			if (this.isWet()) {
				boolean hasArmorBeenDamagedThisInterval = false;
				
				for (ItemStack armorItemStack : this.getArmorItems()) {
					if (armorItemStack.getItem() instanceof ArmorItem armorItem) {
						if (HardcoreModConfig.rust.materialsThatRust.contains(armorItem.getMaterial().getName())) {
							Random random = world.getRandom();
							
							if (random.nextFloat() < HardcoreModConfig.rust.rustChance) {
								armorItemStack.damage(random.nextInt(HardcoreModConfig.rust.rustMaxDamage + 1), this, callback -> callback.sendEquipmentBreakStatus(armorItem.getSlotType()));
								
								if (!hasArmorBeenDamagedThisInterval) {
									MessageUtil.pushMessage(this, new TranslatableText("hardcoremod.temperature.rust"));
								}
								
								hasArmorBeenDamagedThisInterval = true;
							}
						}
					}
				}
			}
		}
	}
}
