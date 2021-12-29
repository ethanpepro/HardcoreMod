package com.ethanpepro.hardcoremod.client.render.entity.feature;

import com.ethanpepro.hardcoremod.client.render.entity.model.HardcoreModTrinketModels;
import com.ethanpepro.hardcoremod.item.ClothingItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CloakTrinketRenderer implements TrinketRenderer {
	private final Identifier texture;
	
	public CloakTrinketRenderer(Identifier texture) {
		this.texture = texture;
	}
	
	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		int color = ((ClothingItem)stack.getItem()).getColor(stack);
		float r = (float)(color >> 16 & 0xFF) / 255.0f;
		float g = (float)(color >> 8 & 0xFF) / 255.0f;
		float b = (float)(color & 0xFF) / 255.0f;
		
		HardcoreModTrinketModels.CLOAK_MODEL.animateModel(entity, limbAngle, limbDistance, tickDelta);
		HardcoreModTrinketModels.CLOAK_MODEL.setAngles(entity, limbAngle, limbDistance, animationProgress, animationProgress, headPitch);
		TrinketRenderer.followBodyRotations(entity, HardcoreModTrinketModels.CLOAK_MODEL);
		
		VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
		
		HardcoreModTrinketModels.CLOAK_MODEL.head.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0f);
		
		// TODO: Move to a helper.
		// TODO: Make smoother?
		if (entity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
			if (abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
				return;
			}
			
			if (abstractClientPlayerEntity.canRenderCapeTexture() && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE) && abstractClientPlayerEntity.getCapeTexture() != null) {
				return;
			}
			
			matrices.push();
			matrices.translate(0.0, 0.0, 0.125);
			
			double capeX = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevCapeX, abstractClientPlayerEntity.capeX) - MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevX, abstractClientPlayerEntity.getX());
			double capeY = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevCapeY, abstractClientPlayerEntity.capeY) - MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevY, abstractClientPlayerEntity.getY());
			double capeZ = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevCapeZ, abstractClientPlayerEntity.capeZ) - MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevZ, abstractClientPlayerEntity.getZ());
			
			float bodyYaw = abstractClientPlayerEntity.prevBodyYaw + (abstractClientPlayerEntity.bodyYaw - abstractClientPlayerEntity.prevBodyYaw);
			
			double o = MathHelper.sin(bodyYaw * MathHelper.RADIANS_PER_DEGREE);
			double p = -MathHelper.cos(bodyYaw * MathHelper.RADIANS_PER_DEGREE);
			
			float q = (float)capeY * 10.0f;
			q = MathHelper.clamp(q, -6.0f, 32.0f);
			
			float u = (float)(capeX * o + capeZ * p) * 100.0f;
			u = MathHelper.clamp(u, 0.0f, 150.0f);
			
			float s = (float)(capeX * p - capeZ * o) * 100.0f;
			s = MathHelper.clamp(s, -20.0f, 20.0f);
			
			if (u < 0.0f) {
				u = 0.0f;
			}
			
			float t = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevStrideDistance, abstractClientPlayerEntity.strideDistance);
			
			q += MathHelper.sin(MathHelper.lerp(tickDelta, abstractClientPlayerEntity.prevHorizontalSpeed, abstractClientPlayerEntity.horizontalSpeed) * 6.0f) * 32.0f * t;
			
			if (abstractClientPlayerEntity.isInSneakingPose()) {
				q += 25.0f;
			}
			
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(6.0f + u / 2.0f + q));
			matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(s / 2.0f));
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - s / 2.0f));
			
			HardcoreModTrinketModels.CLOAK_MODEL.cloak.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0f);
			matrices.pop();
		}
	}
}
