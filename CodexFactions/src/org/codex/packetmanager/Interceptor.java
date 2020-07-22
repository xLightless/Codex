package org.codex.packetmanager;

import org.bukkit.Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.server.v1_8_R3.Packet;


public class Interceptor extends SimpleChannelInboundHandler<Packet<?>>{
	
	Server server ;
	
	public Interceptor(Server server) {
		this.server = server;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Packet<?> arg1) throws Exception {
		PacketEvent event = new PacketEvent(arg1);
		server.getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			arg0.fireChannelRead(arg1);
		}
		
	}

}
