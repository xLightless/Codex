package org.codex.enchants.items;

import org.bukkit.event.Event;

public enum CustomItemType {

	REPAIR_CRYSTAL(new RepairCrystal(), "Repair Crystal");
	
	private final CustomItem<? extends Event> item;
	private final String s;


	CustomItemType(CustomItem<? extends Event> i, String s){
		this.item = i;
		this.s = s;
	}
	
	
	public CustomItem<?> getItem() {
		return item;
	}

	public String getName() {
		return s;
	}
}
