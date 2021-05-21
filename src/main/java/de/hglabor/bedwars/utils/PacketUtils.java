package de.hglabor.bedwars.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import de.hglabor.bedwars.utils.protocol.PacketByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.StandardMessenger;

import java.lang.reflect.InvocationTargetException;

public class PacketUtils {

    public static void sendCustomPayload(Player player, PacketPlayOutCustomPayload packetPlayOutCustomPayload) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutCustomPayload);
    }

    public static void sendNotification(Player player, String title, String message) {
        //sendCustomPayload(player, new PacketPlayOutCustomPayload(
        //                new MinecraftKey(StandardMessenger.validateAndCorrectChannel("hglabor:s2c_notification")),
        //                new PacketDataSerializer(new PacketByteBuf(Unpooled.buffer()).writeString(title + ";" + message))));
        sendPacket(player, new PacketPlayOutCustomPayload(new MinecraftKey(StandardMessenger.validateAndCorrectChannel("hglabor:s2c_notification")), new PacketDataSerializer(new PacketByteBuf(Unpooled.buffer()).writeString(title + ";" + message))));
    }

    public static void sendPacket(Player player, Packet<PacketListenerPlayOut> packet) {
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packet);
    }

}
