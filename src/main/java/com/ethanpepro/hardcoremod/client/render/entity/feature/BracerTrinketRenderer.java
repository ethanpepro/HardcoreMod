package com.ethanpepro.hardcoremod.client.render.entity.feature;

import com.ethanpepro.hardcoremod.client.render.entity.model.HardcoreModTrinketModels;
import com.ethanpepro.hardcoremod.item.ClothingItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BracerTrinketRenderer implements TrinketRenderer {
	private final Identifier texture;
	private final boolean offhand;
	
	public BracerTrinketRenderer(Identifier texture, boolean offhand) {
		this.texture = texture;
		this.offhand = offhand;
	}
	
	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		int color = ((ClothingItem)stack.getItem()).getColor(stack);
		float r = (float)(color >> 16 & 0xFF) / 255.0f;
		float g = (float)(color >> 8 & 0xFF) / 255.0f;
		float b = (float)(color & 0xFF) / 255.0f;
		
		HardcoreModTrinketModels.BRACER_MODEL.animateModel(entity, limbAngle, limbDistance, tickDelta);
		HardcoreModTrinketModels.BRACER_MODEL.setAngles(entity, limbAngle, limbDistance, animationProgress, animationProgress, headPitch);
		TrinketRenderer.followBodyRotations(entity, HardcoreModTrinketModels.BRACER_MODEL);
		
		VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
		
		ModelPart arm = offhand ? HardcoreModTrinketModels.BRACER_MODEL.leftArm : HardcoreModTrinketModels.BRACER_MODEL.rightArm;
		arm.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0f);
	}
}
