package org.codex.enchants.energy;

import org.bukkit.ChatColor;

public enum EnergyStorageCenterType {

	
	STONE_STORAGE(100, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Stone Energy Storage Center"),
	
	IRON_STORAGE(1000,  ChatColor.GRAY + "" + ChatColor.BOLD + "Iron Energy Storage Center"),
	
	DIAMOND_STORAGE(10000, ChatColor.BLUE + "" + ChatColor.BOLD + "Diamond Energy Storage Center"),
	
	QUANTUM_STORAGE(100000, ChatColor.RED + "" + ChatColor.BOLD + "Quantum Energy Storage Center");
	
	private final int maxEnergy;
	private final String name;
	
	EnergyStorageCenterType(int maxEnergy, String name){
		this.maxEnergy = maxEnergy;
		this.name = name;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public String getName() {
		return name;
	}
	
	
	
	
}
