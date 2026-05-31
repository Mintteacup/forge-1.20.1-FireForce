package net.mintteacup.fireforcemod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.mintteacup.fireforcemod.abilitysystem.core.AbilityContext;
import net.mintteacup.fireforcemod.abilitysystem.core.AbilityRegistry;
import net.mintteacup.fireforcemod.abilitysystem.core.IAbility;

import java.util.function.Supplier;

public class UseAbilityPacket {

    private String abilityId;

    public UseAbilityPacket(String abilityId) {
        this.abilityId = abilityId;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.abilityId);
    }

    public static UseAbilityPacket decode(FriendlyByteBuf buffer) {
        return new UseAbilityPacket(buffer.readUtf());
    }

    public static void handle(UseAbilityPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();

            IAbility ability = AbilityRegistry.get(msg.abilityId);

            if (sender != null) {
                AbilityContext context = new AbilityContext(
                        sender,
                        sender.level(),
                        null,
                        sender.position(),
                        "KEY_PRESS"
                );

                if (ability != null && ability.canActivate(context)) {
                    ability.execute(context);
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
