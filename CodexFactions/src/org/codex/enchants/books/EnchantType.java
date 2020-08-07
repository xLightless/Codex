package org.codex.enchants.books;

import org.codex.factions.FactionsMain;

public enum EnchantType {
	
	AUTOSMELT(new AutoSmelt(), "AUTOSMELT", false),

	ADVANCED_FEATHER_FALLING(new AdvancedFeatherFalling(), "ADVANCED_FEATHER_FALLING", false),

	HARDENED(new Hardened(), "HARDENED", false),

	WEED_WACKER(new WeedWacker(), "WEED_WACKER", false),

	VAMPIRE(new Vampire(), "VAMPIRE", false),

	GEARS(new Gears(), "GEARS", false),

	HEALTH_BOOST(new HealthBoost(), "HEALTH_BOOST", true),

	OBSIDIAN_SHIELD(new ObsidianShield(), "OBSIDIAN_SHIELD", false),

	FERRITE(new Ferrite(), "FERRITE", false),

	CREEPER_ARMOR(new CreeperArmor(), "CREEPER_ARMOR", false),

	PHEONIX(new Pheonix(), "PHEONIX", false),

	SPRINGS(new Springs(), "SPRINGS", false),

	LUMBER_JACK(new LumberJack(), "LUMBER_JACK", false),

	KNIGHT(new Knight(), "KNIGHT", false),

	FRENZY(new Frenzy(), "FRENZY", false),

	FRENZY_BLOCKER(new FrenzyBlocker(), "FRENZY_BLOCKER", false),

	TANK(new Tank(), "TANK", false),

	ARMORED(new Armored(), "ARMORED", false),

	RAIN(new Rain(FactionsMain.getMain()), "RAIN", false),

	UMBRELLA(new Umbrella(), "UMBRELLA", false),

	FANGS(new Fangs(), "FANGS", false),

	FREEZE(new Freeze(), "FREEZE", false),

	HARVEST(new Harvest(), "HARVEST", false),
	
	CRIT(new Crit(), "CRIT", false),
	
	SAFE_GUARD(new SafeGuard(), "SAFE_GUARD", false),
	
	SKY_WALKER(new SkyWalker(), "SKY_WALKER", false),
	
	GROUNDED(new Grounded(), "GROUNDED", false),
	
	BLOCK(new Block(), "BLOCK", false),
	
	BENE(new Bene(), "BENE", false);

	private Book b;
	private String name;
	private boolean s;

	EnchantType(Book b, String name, boolean s) {
		this.b = b;
		this.name = name;
	}

	private Book b() {
		return b;
	}
	
	
	private String n() {
		return name;
	}

	public static Book getEnchantClass(EnchantType e) {
		return e.b();
	}

	public static Book[] getAllEnchantBooks() {
		Book[] bArray = { new Umbrella(), new Freeze(), new WeedWacker(), new Fangs(), new AutoSmelt(),
				new Rain(FactionsMain.getMain()), new Hardened(), new Armored(), new Tank(), new FrenzyBlocker(),
				new Frenzy(), new Knight(), new LumberJack(), new Vampire(), new Gears(), new HealthBoost(),
				new AdvancedFeatherFalling(), new ObsidianShield(), new Springs(), new Ferrite(), new CreeperArmor(),
				new Pheonix(), new Harvest(), new Grounded(), new Block(), new Crit(), new SafeGuard(), new SkyWalker()};

		return bArray;
	}

	public static Book getEnchantClass(String s) {
		String s1 = s.toUpperCase();
		for (EnchantType e : EnchantType.values()) {
			if (e.n().equals(s1)) {
				return e.b();
			}
		}
		throw new NullPointerException();

	}
	
	/**
	 * checks if it supports multiple stacks 
	 * Meant to stop dupe glitch
	 * @return
	 */
	
	public boolean canStack() {
		return s;
	}

}
