package com.ethanpepro.hardcoremod.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class CloakTrinketModel extends BipedEntityModel<LivingEntity> {
	public final ModelPart cloak;
	
	public CloakTrinketModel(ModelPart root) {
		super(root);
		
		cloak = root.getChild("cloak");
	}
	
	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0f);
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("cloak", ModelPartBuilder.create().uv(32, 0).cuboid(-5.0f, 0.0f, -1.0f, 10.0f, 16.0f, 1.0f, Dilation.NONE), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 64, 32);
	}
	
	@Override
	public void setAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j) {
		super.setAngles(livingEntity, f, g, h, i, j);
		
		if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
			if (livingEntity.isInSneakingPose()) {
				cloak.pivotZ = 1.4f;
				cloak.pivotY = 1.85f;
			} else {
				cloak.pivotZ = 0.0f;
				cloak.pivotY = 0.0f;
			}
		} else if (livingEntity.isInSneakingPose()) {
			cloak.pivotZ = 0.3f;
			cloak.pivotY = 0.8f;
		} else {
			cloak.pivotZ = -1.1f;
			cloak.pivotY = -0.85f;
		}
	}
}
