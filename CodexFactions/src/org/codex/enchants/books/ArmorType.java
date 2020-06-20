package org.codex.enchants.books;

public enum ArmorType implements NonStackableItemType{

	HELMET, CHESTPLATE, LEGGINGS, BOOTS, ELYTRA;
	
	public static int getDiamondArmorValue(ArmorType t) {
		switch(t) {
		case HELMET :
			return 3;
		case CHESTPLATE :
			return 8;
		case LEGGINGS :
			return 6;
		case BOOTS :
			return 3;
		default :
			return 0;
		}
	}

	public static String toString(ArmorType t) {
		switch(t) {
		case HELMET :
			return "helmet";
		case CHESTPLATE :
			return "chestplate";
		case LEGGINGS :
			return "leggings";
		case BOOTS :
			return "boots";
		default :
			return "does not exist";
		}
		
	}
	
	public static ArmorType value(String s) {
		s.toUpperCase();
		switch (s) {
		case "HELMET":
			return ArmorType.HELMET;
		case "CHESTPLATE":
			return ArmorType.CHESTPLATE;
		case "LEGGINGS":
			return ArmorType.LEGGINGS;
		case "BOOTS":
			return ArmorType.BOOTS;
		default :
			return ArmorType.CHESTPLATE;
		}
		
	}

	public static int getSlotNumber(ArmorType t) {
		
		switch(t) {
		case HELMET:
			return 3;
		case CHESTPLATE:
			return 2;
		case LEGGINGS:
			return 1;
		case BOOTS:
			return 0;
		default:
			break;
		
		}
		
		return 0;
	}
}
