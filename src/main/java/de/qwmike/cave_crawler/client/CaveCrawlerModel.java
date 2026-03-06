 package de.qwmike.cave_crawler.client;

import de.qwmike.cave_crawler.CaveCrawler;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

 public class CaveCrawlerModel extends GeoModel<CaveCrawlerEntity> {
    @Override
    public ResourceLocation getModelResource(final CaveCrawlerEntity ignored) {
        return new ResourceLocation(CaveCrawler.MODID, "geo/cave_crawler.geo" + Utils.getTextureAppend() + ".json");
    }

    @Override
    public ResourceLocation getTextureResource(final CaveCrawlerEntity ignored) {
        return new ResourceLocation(CaveCrawler.MODID, "textures/entity/cave_crawler_texture" + Utils.getTextureAppend() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(final CaveCrawlerEntity ignored) {
        return new ResourceLocation(CaveCrawler.MODID, "animations/cave_crawler.animation.json");
    }

     @Override
     public void setCustomAnimations(final CaveCrawlerEntity animatable, long instanceId, final AnimationState<CaveCrawlerEntity> animationState) {
         CoreGeoBone head = getAnimationProcessor().getBone("head");

         if (head != null) {
             EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
             head.setRotX(entityData.headPitch() * ((float) (Math.PI / 180.0)));
             head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
         }

         super.setCustomAnimations(animatable, instanceId, animationState);
     }
}