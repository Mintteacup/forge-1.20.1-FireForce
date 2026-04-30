package net.mintteacup.fireforcemod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mintteacup.fireforcemod.block.ModBlocks;
import net.mintteacup.fireforcemod.entity.ModEntities;
import net.mintteacup.fireforcemod.item.ModCreativeModeTabs;
import net.mintteacup.fireforcemod.item.ModItems;
import org.slf4j.Logger;
import net.mintteacup.fireforcemod.entity.custom.AdollaBugEntity;
import net.mintteacup.fireforcemod.entity.client.AdollaBugRenderer;
import net.mintteacup.fireforcemod.poi.ModPoiTypes;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FireForceMod.MODID)
public class FireForceMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "fireforcemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public FireForceMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModEntities.ENTITIES.register(modEventBus);


        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::registerEntityAttributes);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        ModPoiTypes.POI_TYPES.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ADOLLA_BUG.get(), AdollaBugEntity.createAttributes().build());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ADOLLA_BUG);
            event.accept(ModItems.FIRE_EXTINGUISHER);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code

        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.ADOLLA_BUG.get(), AdollaBugRenderer::new);
        }
    }


}
