package net.mintteacup.fireforcemod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.entity.custom.AdollaBugEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AdollaBugRenderer extends GeoEntityRenderer<AdollaBugEntity> {

    public AdollaBugRenderer(net.minecraft.client.renderer.entity.EntityRendererProvider.Context renderManager) {
        super(renderManager, new GeoModel<AdollaBugEntity>() {
            @Override
            public ResourceLocation getModelResource(AdollaBugEntity animatable) {
                return new ResourceLocation(FireForceMod.MODID, "geo/adolla_bug_3d.geo.json");
            }

            @Override
            public ResourceLocation getTextureResource(AdollaBugEntity animatable) {
                return new ResourceLocation(FireForceMod.MODID, "textures/entity/adolla_bug.png");
            }

            @Override
            public ResourceLocation getAnimationResource(AdollaBugEntity animatable) {
                return new ResourceLocation(FireForceMod.MODID, "animations/adolla_bug.animation.json");
            }
        });
    }
}