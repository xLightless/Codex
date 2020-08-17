package org.codex.factions;

public enum FactionPermissions {

	BLOCK_BREAK(3),
	
	BLOCK_PLACE_OBSIDIAN(1),
	
	OPEN_CHESTS(3),
	
	BLOCK_PLACE_AVERAGE(3),
	
	BLOCK_PLACE_LAVA(1),
	
	BLOCK_PLACE_WATER(1),
	
	BLOCK_PLACE(3),
	
	BLOCK_BREAK_OBSIDIAN(1),
	
	BLOCK_BREAK_AVERAGE(3),
	
	BLOCK_BREAK_LAVA(1),
	
	BLOCK_BREAK_WATER(1),
	
	PLACE_CREEPERS(3),
	
	KILL_MOBS(3),
	
	ALLY(3),
	
	TRUCE(3),
	
	ENEMY(2),
	
	NEUTRAL(2),
	
	MANAGE_RELATIONSHIPS(3);
	
	private int priority;
	
	/**
	 * 
	 * @author Eddie
	 * @param Let priority equal the degree of rank a faction player must have in their faction to inherit this permission
	 */
	FactionPermissions(int priority){
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	
	
}
