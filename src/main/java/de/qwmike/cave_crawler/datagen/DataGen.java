package de.qwmike.cave_crawler.datagen;

import de.qwmike.cave_crawler.CaveCrawler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = CaveCrawler.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void configureDataGen(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ModItemModelProvider(generator.getPackOutput(), CaveCrawler.MODID, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(generator.getPackOutput(), lookupProvider, existingFileHelper));
    }
}
