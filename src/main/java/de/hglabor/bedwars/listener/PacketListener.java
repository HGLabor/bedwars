package de.hglabor.bedwars.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import de.hglabor.bedwars.Bedwars;
import de.hglabor.bedwars.utils.PacketUtils;
import de.hglabor.bedwars.utils.protocol.PacketByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomPayload;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;

public class PacketListener implements Listener {

    public PacketListener(Plugin plugin) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CUSTOM_PAYLOAD) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packetContainer = event.getPacket();
                PacketPlayInCustomPayload packet = (PacketPlayInCustomPayload) packetContainer.getHandle();
                Player player = event.getPlayer();
                if (packet.tag.toString().equalsIgnoreCase("noriskclient:blockhit")) {
                    PacketPlayOutCustomPayload newPacket = new PacketPlayOutCustomPayload(
                            new MinecraftKey(StandardMessenger.validateAndCorrectChannel("noriskclient:blockhit")),
                            new PacketDataSerializer(new PacketByteBuf(Unpooled.buffer()).writeString(player.getUniqueId().toString())));

                    Bukkit.getScheduler().runTask(plugin, () -> {
                        for (Object nearbyPlayer : player.getWorld().getNearbyEntities(player.getLocation(), 15, 15, 15).stream().filter(it -> it instanceof Player).toArray()) {
                            ((CraftPlayer) nearbyPlayer).getHandle().playerConnection.sendPacket(newPacket);
                        }
                    });
                } else if (packet.tag.toString().equalsIgnoreCase("noriskclient:release")) {
                    PacketPlayOutCustomPayload newPacket = new PacketPlayOutCustomPayload(
                            new MinecraftKey(StandardMessenger.validateAndCorrectChannel("noriskclient:release")),
                            new PacketDataSerializer(new PacketByteBuf(Unpooled.buffer()).writeString(player.getUniqueId().toString())));
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        for (Object nearbyPlayer : player.getWorld().getNearbyEntities(player.getLocation(), 15, 15, 15).stream().filter(it -> it instanceof Player).toArray()) {
                            ((CraftPlayer) nearbyPlayer).getHandle().playerConnection.sendPacket(newPacket);
                        }
                    });
                } else if(packet.tag.toString().equalsIgnoreCase("hglabor:c2s_handshake")) {
                    /*
                    PacketUtils.sendCustomPayload(player, new PacketPlayOutCustomPayload(
                            new MinecraftKey(StandardMessenger.validateAndCorrectChannel("hglabor:s2c_handshake")),
                            new PacketDataSerializer(new PacketByteBuf(Unpooled.buffer()).writeString(player.getUniqueId().toString()))));
                     */
                }
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                Bukkit.broadcastMessage("§a-> §foutgoing: " + event.getPacketType() + ": " + event.getPacket().getStrings());
            }
        });
    }

}
