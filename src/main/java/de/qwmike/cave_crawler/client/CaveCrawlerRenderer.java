package de.qwmike.cave_crawler.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.qwmike.cave_crawler.CaveCrawler;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CaveCrawlerRenderer extends GeoEntityRenderer<CaveCrawlerEntity> {
    public CaveCrawlerRenderer(final EntityRendererProvider.Context context) {
        super(context, new CaveCrawlerModel());
        this.shadowRadius = 0.3F;

        addRenderLayer(new CaveCrawlerEyesLayer(this));
    }

    @Override // TODO :: Is this even used?
    public @NotNull ResourceLocation getTextureLocation(@NotNull final CaveCrawlerEntity instance) {
        return new ResourceLocation(CaveCrawler.MODID, "textures/entity/cave_crawler_texture" + Utils.getTextureAppend() + ".png");
    }

    @Override // FIXME :: In some cases this does not get called, resulting in an invisible entity?
    public void render(final CaveCrawlerEntity entity, float entityYaw, float partialTick, @NotNull final PoseStack poseStack, @NotNull final MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        if (entity.isBaby()) {
            poseStack.scale(0.1F, 0.1F, 0.1F);
        } else {
            poseStack.scale(1.3F, 1.3F, 1.3F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
