package org.codex.enchants.energy;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;

public enum EnergyHarvesterType {

	IRON(2, 1, ChatColor.GRAY + "" + ChatColor.BOLD + "Iron Energy Harvester", Material.IRON_BLOCK),
	GOLDEN(3, 2, ChatColor.YELLOW + "" + ChatColor.BOLD  + "Golden Energy Harvester", Material.GOLD_BLOCK),
	DIAMOND(4, 4, ChatColor.BLUE + "" + ChatColor.BOLD  + "Diamond Energy Harvester", Material.DIAMOND_BLOCK),
	NETHERITE(5, 8, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Netherite Energy Harvester", Material.DIAMOND_BLOCK),
	ADVANCED_IRON(6, 12, ChatColor.GRAY + "" + ChatColor.BOLD + "Advanced Iron Energy Harvester", Material.IRON_BLOCK),
	ADVANCED_GOLDEN(7, 20, ChatColor.YELLOW + "" + ChatColor.BOLD + "Advanced Golden Energy Harvester", Material.GOLD_BLOCK),
	ADVANCED_DIAMOND(8, 30, ChatColor.BLUE + "" + ChatColor.BOLD + "Advanced Diamond Energy Harvester", Material.DIAMOND_BLOCK),
	ADVANCED_NETHERITE(10, 40,ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Advanced Netherite Energy Harvester", Material.DIAMOND_BLOCK);
	
	private final int radius;
	private final int speed;
	private final String displayName;
	private final Material harvestingMaterial;
	

	EnergyHarvesterType(int radius, int speed, String displayName, Material m){
		this.radius = radius;
		this.speed = speed;
		this.displayName = displayName;
		this.harvestingMaterial = m;
		
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public int getRadius() {
		return radius;
	}

	public int getSpeed() {
		return speed;
	}
	
	
	public Material getHarvestingMaterial() {
		return harvestingMaterial;
	}

	public static EnergyHarvesterType value(String s) {
		for(EnergyHarvesterType type : EnergyHarvesterType.values()) {
			if(s.equals(type.getDisplayName())) {
				return type;
			}
		}
		return valueOf(s);
	}
	
	
}
