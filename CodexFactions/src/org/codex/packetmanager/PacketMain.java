package org.codex.packetmanager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class PacketMain implements Listener {

	Server server;

	public PacketMain(Server server) {
		this.server = server;
	}

	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p instanceof CraftPlayer) {
			CraftPlayer cp = (CraftPlayer) p;
			EntityPlayer ep = cp.getHandle();
			try {
				Field field = PlayerConnection.class.getDeclaredField("networkManager");
				field.setAccessible(true);
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
				field.set(ep.playerConnection, new Interceptor(EnumProtocolDirection.CLIENTBOUND, server));
			} catch (Throwable err) {
				err.printStackTrace();
			}

		}

	}
	public static void sendPlayerPacket(Player p, Packet<?> packet) {
		if(p instanceof CraftPlayer) {
			CraftPlayer cp = (CraftPlayer) p;
			cp.getHandle().playerConnection.sendPacket(packet);
		}
	}
}
