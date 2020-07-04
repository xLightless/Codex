package org.codex.packetmanager;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.minecraft.server.v1_8_R3.Packet;

public class PacketEvent extends Event{

	private boolean cancelled = false;
	private Packet<?> packet;
	
	public PacketEvent(Packet<?> p ) {
		packet = p;
	}
	
	@Override
	public HandlerList getHandlers() {
		return null;
	}
	@Override
	public String getEventName() {
		return "CodexPacketEvent";
	}
	public void cancelEvent() {
		cancelled = true;
	}
	public boolean isCancelled() {
		return cancelled;
	}

	public Packet<?> getPacket() {
		return packet;
	}

	public void setPacket(Packet<?> packet) {
		this.packet = packet;
	}
	

}
