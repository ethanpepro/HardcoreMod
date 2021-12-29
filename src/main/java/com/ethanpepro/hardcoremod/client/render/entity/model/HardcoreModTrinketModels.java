package com.ethanpepro.hardcoremod.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;

@Environment(EnvType.CLIENT)
public class HardcoreModTrinketModels {
	private static final Dilation CLOTHING_DILATION = new Dilation(0.8f);
	private static final Dilation BRACER_DILATION = new Dilation(0.2f);
	private static final Dilation GREAVES_DILATION = new Dilation(0.3f);
	private static final Dilation CLOAK_DILATION = new Dilation(1.0f);
	
	private static final TexturedModelData CLOTHING_MODEL_DATA = TexturedModelData.of(BipedEntityModel.getModelData(CLOTHING_DILATION, 0.0f), 64, 32);
	private static final TexturedModelData BRACER_MODEL_DATA = TexturedModelData.of(BipedEntityModel.getModelData(BRACER_DILATION, 0.0f), 64, 32);
	private static final TexturedModelData GREAVES_MODEL_DATA = TexturedModelData.of(BipedEntityModel.getModelData(GREAVES_DILATION, 0.0f), 64, 32);
	private static final TexturedModelData CLOAK_MODEL_DATA = CloakTrinketModel.getTexturedModelData(CLOAK_DILATION);
	
	public static final ClothingTrinketModel CLOTHING_MODEL = new ClothingTrinketModel(CLOTHING_MODEL_DATA.createModel());
	public static final ClothingTrinketModel BRACER_MODEL = new ClothingTrinketModel(BRACER_MODEL_DATA.createModel());
	public static final ClothingTrinketModel GREAVES_MODEL = new ClothingTrinketModel(GREAVES_MODEL_DATA.createModel());
	public static final CloakTrinketModel CLOAK_MODEL = new CloakTrinketModel(CLOAK_MODEL_DATA.createModel());
}
