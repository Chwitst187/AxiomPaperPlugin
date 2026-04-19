package com.moulberry.axiom.packet;

import com.moulberry.axiom.AxiomPaper;
import io.netty.buffer.Unpooled;
import net.kyori.adventure.text.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class WrapperPacketListener implements PluginMessageListener {

    private final PacketHandler packetHandler;

    public WrapperPacketListener(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {
        player.getScheduler().execute(AxiomPaper.PLUGIN, () -> {
            RegistryFriendlyByteBuf friendlyByteBuf = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(bytes), ((CraftPlayer)player).getHandle().registryAccess());
            try {
                this.packetHandler.onReceive(player, friendlyByteBuf);
            } catch (Throwable t) {
                AxiomPaper.PLUGIN.getLogger().log(Level.SEVERE, "Error while processing packet " + s, t);
                player.kick(Component.text("Error while processing packet " + s + ": " + formatThrowable(t)));
            }
        }, null, 1L);
    }

    private static String formatThrowable(Throwable throwable) {
        String message = throwable.getMessage();
        if (message == null || message.isBlank()) {
            return throwable.getClass().getSimpleName();
        }
        return throwable.getClass().getSimpleName() + ": " + message;
    }
}
