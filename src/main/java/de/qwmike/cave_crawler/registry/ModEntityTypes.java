package de.qwmike.cave_crawler.registry;

import de.qwmike.cave_crawler.CaveCrawler;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CaveCrawler.MODID);

    public static final RegistryObject<EntityType<CaveCrawlerEntity>> CAVE_CRAWLER =
            ENTITY_TYPES.register("cave_crawler", () ->
                    EntityType.Builder.<CaveCrawlerEntity>of(CaveCrawlerEntity::new, MobCategory.MONSTER)
                            .sized(0.75F, 2.5F)
                            .clientTrackingRange(10)
                            .updateInterval(3)
                            .build("cave_crawler")
            );

    public static void register(final IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
