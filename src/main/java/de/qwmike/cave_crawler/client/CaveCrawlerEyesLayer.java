package de.qwmike.cave_crawler.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.qwmike.cave_crawler.CaveCrawler;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class CaveCrawlerEyesLayer extends GeoRenderLayer<CaveCrawlerEntity> {
    public static ResourceLocation TEXTURE = new ResourceLocation(CaveCrawler.MODID, "textures/entity/cave_crawler_eyes_texture" + Utils.getTextureAppend() + ".png");

    public CaveCrawlerEyesLayer(final GeoRenderer<CaveCrawlerEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(final PoseStack poseStack, final CaveCrawlerEntity animatable, final BakedGeoModel bakedModel, final RenderType renderType, final MultiBufferSource bufferSource, final VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        packedLight = 15728880;
        RenderType eyesRenderType = RenderType.entityCutoutNoCull(TEXTURE);

        this.getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, eyesRenderType, bufferSource.getBuffer(eyesRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}