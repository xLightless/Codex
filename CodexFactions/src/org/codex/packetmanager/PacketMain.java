package org.codex.packetmanager;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;

public class PacketMain implements Listener {

	Server server;

	public PacketMain(Server server) {
		this.server = server;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p instanceof CraftPlayer) {
			CraftPlayer cp = (CraftPlayer) p;
			EntityPlayer ep = cp.getHandle();
			NetworkManager nm = ep.playerConnection.networkManager;
			nm.channel.pipeline().addFirst(new Interceptor(server));

		}

	}

	public static void sendPlayerPacket(Player p, Packet<?> packet) {
		if (p instanceof CraftPlayer) {
			CraftPlayer cp = (CraftPlayer) p;
			cp.getHandle().playerConnection.sendPacket(packet);
		}
	}

	private static Class<?> getNMSClass(String name) {

		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		try {
			Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
			Object enumSubTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE")
					.get(null);
			Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\":\"" + title + "\"}");
			Object subchat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\":\"" + subtitle + "\"}");
			Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
					getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Packet<?> packet = (Packet<?>) titleConstructor.newInstance(enumTitle, chat, fadeIn, stay, fadeOut);
			Packet<?> packet2 = (Packet<?>) titleConstructor.newInstance(enumSubTitle, subchat, fadeIn, stay, fadeOut);
			sendPlayerPacket(player, packet);
			sendPlayerPacket(player, packet2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
