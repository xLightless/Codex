package org.codex.packetmanager;

import org.bukkit.Server;

import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;

public class Interceptor extends NetworkManager {

	Server server;

	public Interceptor(EnumProtocolDirection enumprotocoldirection, Server serv) {
		super(enumprotocoldirection);
		server = serv;
	}

	@Override
	public void handle(@SuppressWarnings("rawtypes") Packet packet) {
		PacketEvent e = new PacketEvent(packet);
		if(server == null) {
			System.err.println("Server is null ");
			super.handle(packet);
			return;
		}
		server.getPluginManager().callEvent(e);
		if (!e.isCancelled()) {
			super.handle(packet);
		}

	}

}
