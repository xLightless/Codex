package org.codex.enchants.books;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RainFallingEvent extends PlayerEvent implements Cancellable{

	private Player sender;
	private boolean cancel = false;
	private static final HandlerList handlers = new HandlerList();
	private double damage;
	
	public RainFallingEvent(@Nonnull Player who, Player sender, double damagePerSecond) {
		super(who);
		this.setSender(sender);
		this.setDamage(damagePerSecond);
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean arg0) {
		arg0 = cancel;
		
	}

	@Override
	public @Nonnull HandlerList getHandlers() {
		return handlers;
	}
	
	public static final HandlerList getHandlerList() {
		  return handlers;
		  }
	public Player getSender() {
		return sender;
	}

	public void setSender(Player sender) {
		this.sender = sender;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

}
