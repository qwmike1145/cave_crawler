package de.qwmike.cave_crawler.registry;

import de.qwmike.cave_crawler.CaveCrawler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CaveCrawler.MODID);

    // Cave ambience noises (play before spawn as warnings)
    public static final RegistryObject<SoundEvent> CAVENOISE_1 = register("cave_crawler.cavenoise_1");
    public static final RegistryObject<SoundEvent> CAVENOISE_2 = register("cave_crawler.cavenoise_2");
    public static final RegistryObject<SoundEvent> CAVENOISE_3 = register("cave_crawler.cavenoise_3");
    public static final RegistryObject<SoundEvent> CAVENOISE_4 = register("cave_crawler.cavenoise_4");

    // Chase sounds
    public static final RegistryObject<SoundEvent> CHASE_1 = register("cave_crawler.chase_1");
    public static final RegistryObject<SoundEvent> CHASE_2 = register("cave_crawler.chase_2");
    public static final RegistryObject<SoundEvent> CHASE_3 = register("cave_crawler.chase_3");
    public static final RegistryObject<SoundEvent> CHASE_4 = register("cave_crawler.chase_4");

    // Footstep sounds during chase
    public static final RegistryObject<SoundEvent> CHASE_STEP_1 = register("cave_crawler.chase_step_1");
    public static final RegistryObject<SoundEvent> CHASE_STEP_2 = register("cave_crawler.chase_step_2");
    public static final RegistryObject<SoundEvent> CHASE_STEP_3 = register("cave_crawler.chase_step_3");
    public static final RegistryObject<SoundEvent> CHASE_STEP_4 = register("cave_crawler.chase_step_4");

    // Flee sounds (when hurt)
    public static final RegistryObject<SoundEvent> FLEE_1 = register("cave_crawler.flee_1");
    public static final RegistryObject<SoundEvent> FLEE_2 = register("cave_crawler.flee_2");

    // Event sounds
    public static final RegistryObject<SoundEvent> SPOTTED  = register("cave_crawler.spotted");
    public static final RegistryObject<SoundEvent> DISAPPEAR = register("cave_crawler.disappear");

    // Hurt and death sounds
    public static final RegistryObject<SoundEvent> DWELLER_HURT_1 = register("cave_crawler.dweller_hurt_1");
    public static final RegistryObject<SoundEvent> DWELLER_HURT_2 = register("cave_crawler.dweller_hurt_2");
    public static final RegistryObject<SoundEvent> DWELLER_HURT_3 = register("cave_crawler.dweller_hurt_3");
    public static final RegistryObject<SoundEvent> DWELLER_HURT_4 = register("cave_crawler.dweller_hurt_4");
    public static final RegistryObject<SoundEvent> DWELLER_DEATH  = register("cave_crawler.dweller_death");

    private static RegistryObject<SoundEvent> register(final String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CaveCrawler.MODID, name)));
    }

    public static void register(final IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
