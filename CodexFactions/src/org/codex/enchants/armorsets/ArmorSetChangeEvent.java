package org.codex.enchants.armorsets;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArmorSetChangeEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private ArmorChangeType type;
	private Player player;
	private ArmorSets set;
	private HashMap<Player, Integer> amountApplied = new HashMap<>();
	
	public ArmorSetChangeEvent(ArmorChangeType t, Player p, ArmorSets set, int applied){
		this.type = t;
		this.player = p;
		this.set = set;
		this.amountApplied.put(p, applied);
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static final HandlerList getHandlerList() {

		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancel = arg0;
	}

	public ArmorChangeType getType() {
		return type;
	}


	public void setType(ArmorChangeType type) {
		this.type = type;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public ArmorSets getSet() {
		return set;
	}


	public void setSet(ArmorSets set) {
		this.set = set;
	}


	public int getAmountApplied() {
		return amountApplied.get(player);
	}



	
}
