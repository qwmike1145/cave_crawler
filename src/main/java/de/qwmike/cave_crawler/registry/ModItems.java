package de.qwmike.cave_crawler.registry;

import de.qwmike.cave_crawler.CaveCrawler;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CaveCrawler.MODID);

    // Spawn egg - primary color (dark gray), secondary color (dark red)
    public static final RegistryObject<Item> CAVE_CRAWLER_SPAWN_EGG =
            ITEMS.register("cave_crawler_spawn_egg", () ->
                    new ForgeSpawnEggItem(ModEntityTypes.CAVE_CRAWLER, 0x1a1a1a, 0x6b0000, new Item.Properties())
            );

    public static void addCreative(final CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(CAVE_CRAWLER_SPAWN_EGG);
        }
    }

    public static void register(final IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}