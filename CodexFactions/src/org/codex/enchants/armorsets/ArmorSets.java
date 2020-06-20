package org.codex.enchants.armorsets;

public enum ArmorSets {

	NETHER_ARMOR_SET, ENDER_ARMOR_SET, PHANTOM_KNIGHT_ARMOR_SET;
	
	public static ArmorSet getArmorSetFromString(String s) {
		
		switch(s) {
		
		case "NetherArmorSet":
			return new NetherArmorSet();
		case "EnderArmorSet":
			return new EnderArmorSet();
		case "PhantomKnightsSet":
			return new PhantomArmorSet();
		default :
			return new NetherArmorSet();
			
		}
		
	}
	
	public static ArmorSet getArmorSetFromSetType(ArmorSets e) {
		switch(e) {
		case NETHER_ARMOR_SET:
			return new NetherArmorSet();
		case ENDER_ARMOR_SET:
			return new EnderArmorSet();
		case PHANTOM_KNIGHT_ARMOR_SET:
			return new PhantomArmorSet();
		}
		return null;
	}
	
	
}
