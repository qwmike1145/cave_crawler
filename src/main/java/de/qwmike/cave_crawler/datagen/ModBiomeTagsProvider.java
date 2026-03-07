package de.qwmike.cave_crawler.datagen;

import de.qwmike.cave_crawler.CaveCrawler;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public static final TagKey<Biome> CAVE_CRAWLER_SURFACE_BIOMES =
            TagKey.create(Registries.BIOME, new ResourceLocation(CaveCrawler.MODID, "cave_crawler_surface_biomes"));

    public ModBiomeTagsProvider(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CaveCrawler.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(CAVE_CRAWLER_SURFACE_BIOMES).addOptionalTag(Tags.Biomes.IS_SPOOKY.location());
    }
}
