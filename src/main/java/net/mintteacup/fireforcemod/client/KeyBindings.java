package net.mintteacup.fireforcemod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.network.ModNetwork;
import net.mintteacup.fireforcemod.network.UseAbilityPacket;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = FireForceMod.MODID, value = Dist.CLIENT)
public class KeyBindings {

    public static final Lazy<KeyMapping> USE_ABILITY_KEY = Lazy.of(() -> new KeyMapping(
            "key.fireforcemod.use_ability",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.categories.fireforcemod"
    ));


    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(USE_ABILITY_KEY.get());
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (USE_ABILITY_KEY.get().consumeClick()) {
                ModNetwork.INSTANCE.sendToServer(new UseAbilityPacket("self_heal"));
            }
        }
    }
}
