package org.codex.enchants.books;

import org.codex.factions.FactionsMain;

public enum EnchantType {
	AUTOSMELT(new AutoSmelt(), "AUTOSMELT"),

	ADVANCED_FEATHER_FALLING(new AdvancedFeatherFalling(), "ADVANCED_FEATHER_FALLING"),

	HARDENED(new Hardened(), "HARDENED"),

	WEED_WACKER(new WeedWacker(), "WEED_WACKER"),

	VAMPIRE(new Vampire(), "VAMPIRE"),

	GEARS(new Gears(), "GEARS"),

	HEALTH_BOOST(new HealthBoost(), "HEALTH_BOOST"),

	OBSIDIAN_SHIELD(new ObsidianShield(), "OBSIDIAN_SHIELD"),

	FERRITE(new Ferrite(), "FERRITE"),

	CREEPER_ARMOR(new CreeperArmor(), "CREEPER_ARMOR"),

	PHEONIX(new Pheonix(), "PHEONIX"),

	SPRINGS(new Springs(), "SPRINGS"),

	LUMBER_JACK(new LumberJack(), "LUMBER_JACK"),

	KNIGHT(new Knight(), "KNIGHT"),

	FRENZY(new Frenzy(), "FRENZY"),

	FRENZY_BLOCKER(new FrenzyBlocker(), "FRENZY_BLOCKER"),

	TANK(new Tank(), "TANK"),

	ARMORED(new Armored(), "ARMORED"),

	RAIN(new Rain(FactionsMain.getMain()), "RAIN"),

	UMBRELLA(new Umbrella(), "UMBRELLA"),

	FANGS(new Fangs(), "FANGS"),

	FREEZE(new Freeze(), "FREEZE"),

	HARVEST(new Harvest(), "HARVEST"),
	
	CRIT(new Crit(), "CRIT"),
	
	SAFE_GUARD(new SafeGuard(), "SAFE_GUARD"),
	
	SAFE_WALK(new SafeWalk(), "SAFE_WALK");

	private Book b;
	private String name;

	EnchantType(Book b, String name) {
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
				new Pheonix(), new Harvest(), new Crit(), new SafeGuard(), new SafeWalk()};

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

}
