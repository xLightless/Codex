package org.codex.enchants.items;

import org.bukkit.event.Event;

public enum CustomItemType {

	REPAIR_CRYSTAL(new RepairCrystal(), "Repair Crystal", false, false),
	
	TRENCH_PICKAXE(new TrenchPickaxe(0), "Trench Pickaxe", false, false),
	
	COLLECTION_CHEST(new CollectionChest(), "Collection Chest", true, true);
	
	private final CustomItem<? extends Event> item;
	private final String s;
	private final boolean placeable;
	private final boolean breakable;

	CustomItemType(CustomItem<? extends Event> i, String s, boolean placeable, boolean breakable){
		this.item = i;
		this.s = s;
		this.placeable = placeable;
		this.breakable = breakable;
	}
	
	
	public CustomItem<?> getItem() {
		return item;
	}

	public String getName() {
		return s;
	}
	
	public boolean isPlaceable() {
		return placeable;
	}


	public boolean isBreakable() {
		return breakable;
	}
	
}
