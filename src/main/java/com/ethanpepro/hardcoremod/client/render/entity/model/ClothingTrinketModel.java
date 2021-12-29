package com.ethanpepro.hardcoremod.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class ClothingTrinketModel extends BipedEntityModel<LivingEntity> {
	public ClothingTrinketModel(ModelPart root) {
		super(root);
	}
}
