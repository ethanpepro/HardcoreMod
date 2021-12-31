package com.ethanpepro.hardcoremod.client;

import com.ethanpepro.hardcoremod.client.render.entity.feature.HandTrinketRenderer;
import com.ethanpepro.hardcoremod.client.render.entity.feature.CloakTrinketRenderer;
import com.ethanpepro.hardcoremod.client.render.entity.feature.ClothingTrinketRenderer;
import com.ethanpepro.hardcoremod.client.render.entity.feature.LegsTrinketRenderer;
import com.ethanpepro.hardcoremod.item.ClothingItem;
import com.ethanpepro.hardcoremod.item.HardcoreModExampleItems;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HardcoreModExampleClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((ClothingItem)stack.getItem()).getColor(stack),
				HardcoreModExampleItems.FACE_WRAP,
				HardcoreModExampleItems.ARMING_CAP,
				HardcoreModExampleItems.TUNIC,
				HardcoreModExampleItems.GAMBESON,
				HardcoreModExampleItems.CASUAL_CLOAK,
				HardcoreModExampleItems.FORMAL_CLOAK,
				HardcoreModExampleItems.TROUSERS,
				HardcoreModExampleItems.GREAVES,
				HardcoreModExampleItems.SHOES,
				HardcoreModExampleItems.BOOTS,
				HardcoreModExampleItems.GLOVE,
				HardcoreModExampleItems.BRACER,
				HardcoreModExampleItems.OFFHAND_GLOVE,
				HardcoreModExampleItems.OFFHAND_BRACER);
		
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.FACE_WRAP, new ClothingTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/face_wrap.png")));
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.ARMING_CAP, new ClothingTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/arming_cap.png")));
		
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.TUNIC, new ClothingTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/tunic.png")));
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.GAMBESON, new ClothingTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/gambeson.png")));
		
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.CASUAL_CLOAK, new CloakTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/casual_cloak.png")));
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.FORMAL_CLOAK, new CloakTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/formal_cloak.png")));
		
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.TROUSERS, new LegsTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/trousers.png")));
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.GREAVES, new LegsTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/greaves.png")));
		
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.SHOES, new ClothingTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/shoes.png")));
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.BOOTS, new ClothingTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/boots.png")));
		
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.GLOVE, new HandTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/glove.png"), false));
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.BRACER, new HandTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/bracer.png"), false));
		
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.OFFHAND_BRACER, new HandTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/bracer.png"), true));
		TrinketRendererRegistry.registerRenderer(HardcoreModExampleItems.OFFHAND_GLOVE, new HandTrinketRenderer(new Identifier("hardcoremod-example", "textures/models/glove.png"), true));
	}
}
