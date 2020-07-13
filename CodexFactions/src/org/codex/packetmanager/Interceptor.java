package org.codex.packetmanager;

import java.net.SocketAddress;

import javax.crypto.SecretKey;

import org.bukkit.Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;

public class Interceptor extends NetworkManager {

	Server server;
	NetworkManager parent;

	public Interceptor(EnumProtocolDirection enumprotocoldirection, Server serv, NetworkManager parent) {
		super(enumprotocoldirection);
		server = serv;
		this.parent = parent;
	}

	@Override
	public void handle(@SuppressWarnings("rawtypes") Packet packet) {
		/*PacketEvent e = new PacketEvent(packet);
		if (server == null) {
			System.err.println("Server is null ");
			parent.handle(packet);
			return;
		}
		server.getPluginManager().callEvent(e);
		if (!e.isCancelled()) {
			parent.handle(packet);
		}
		*/

	}

	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		parent.handlerAdded(arg0);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		parent.handlerRemoved(arg0);
	}

	@Override
	public void a() {
		parent.a();
	}


	@Override
	public void a(EnumProtocol enumprotocol) {
		parent.a(enumprotocol);
	}

	@Override
	public void a(int i) {
		parent.a(i);
	}

	@Override
	public void a(Packet packet, GenericFutureListener<? extends Future<? super Void>> genericfuturelistener,
			GenericFutureListener<? extends Future<? super Void>>... agenericfuturelistener) {
		parent.a(packet, genericfuturelistener, agenericfuturelistener);
	}
	@Override
	public void a(PacketListener packetlistener) {
		parent.a(packetlistener);
	}

	@Override
	public void a(SecretKey secretkey) {
		parent.a(secretkey);
	}

	@Override
	public boolean acceptInboundMessage(Object arg0) throws Exception {
		return parent.acceptInboundMessage(arg0);
	}

	@Override
	public boolean c() {
		return parent.c();
	}

	@Override
	public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
		parent.channelActive(channelhandlercontext);
	}

	@Override
	public void channelInactive(ChannelHandlerContext channelhandlercontext) throws Exception {
		parent.channelInactive(channelhandlercontext);
	}

	@Override
	public void channelRead(ChannelHandlerContext arg0, Object arg1) throws Exception {
		parent.channelRead(arg0, arg1);
	}


	@Override
	public void channelReadComplete(ChannelHandlerContext arg0) throws Exception {
		parent.channelReadComplete(arg0);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext arg0) throws Exception {
		parent.channelRegistered(arg0);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext arg0) throws Exception {
		parent.channelUnregistered(arg0);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext arg0) throws Exception {
		parent.channelWritabilityChanged(arg0);
	}

	@Override
	public void close(IChatBaseComponent ichatbasecomponent) {
		parent.close(ichatbasecomponent);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) throws Exception {
		parent.exceptionCaught(channelhandlercontext, throwable);
	}

	@Override
	public boolean g() {
		return parent.g();
	}

	@Override
	public PacketListener getPacketListener() {
		return parent.getPacketListener();
	}

	@Override
	public SocketAddress getRawAddress() {
		return parent.getRawAddress();
	}

	@Override
	public SocketAddress getSocketAddress() {
		return parent.getSocketAddress();
	}

	@Override
	public boolean h() {
		return parent.h();
	}

	@Override
	public boolean isSharable() {
		return parent.isSharable();
	}

	@Override
	public IChatBaseComponent j() {
		return parent.j();
	}

	@Override
	public void k() {
		parent.k();
	}

	@Override
	public void l() {
		parent.l();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1) throws Exception {
		parent.userEventTriggered(arg0, arg1);
	}

}
