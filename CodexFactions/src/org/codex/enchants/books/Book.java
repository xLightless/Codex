package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.codex.factions.Vector2D;

public abstract class Book implements Listener {

	protected static final String COMMON = BookType.COMMON_BOOK.getChatColor();
	protected static final String RARE = BookType.RARE_BOOK.getChatColor();
	protected static final String MAJESTIC = BookType.MAJESTIC_BOOK.getChatColor();
	protected static final String LEGENDARY = BookType.LEGENDARY_BOOK.getChatColor();
	protected static final String MYSTICAL = BookType.MYSTICAL_BOOK.getChatColor();
	protected static final String QUANTUM = BookType.QUANTUM_BOOK.getChatColor();
	/**
	 * This is the map dealing with potion effects. The two integers go as follows.
	 * the first integer is the Level of the effect the second integer is the time
	 * the integer is applied
	 */
	private static HashMap<Player, HashMap<PotionEffectType, Vector2D<Integer, Integer>>> potionMap = new HashMap<>();

	private Random r = new Random();
	private ItemStack itemStack;
	private ItemMeta itemMeta;
	private List<String> lore;
	private int minArmorValue;
	private BookType bookType;
	private String bookName;
	private String appliedBookName;
	private List<Material> applicableItems;
	// private static HashMap<Player, HashMap<PotionEffectType, Vector2D<Integer,
	// Integer>>> map = new HashMap<>();

	public Book(ItemStack is, ItemMeta im, List<String> lore, int minArmorValue, BookType b, String bookName,
			String appliedBookName, List<Material> applicableItems) {
		this.itemStack = is;
		this.itemMeta = im;
		this.lore = lore;
		this.minArmorValue = minArmorValue;
		this.bookType = b;
		this.bookName = bookName;
		this.appliedBookName = appliedBookName;
		this.applicableItems = applicableItems;

	}
	
	public Book(ItemStack is, ItemMeta im, String[] lore, int minArmorValue, BookType b, String bookName,
			String appliedBookName, Material[] applicableItems) {
		this.itemStack = is;
		this.itemMeta = im;
		this.lore = List.of(lore);
		this.minArmorValue = minArmorValue;
		this.bookType = b;
		this.bookName = bookName;
		this.appliedBookName = appliedBookName;
		this.applicableItems = List.of(applicableItems);

	}

	protected abstract void onActivation(Player p, String level, NonStackableItemType a);

	protected abstract void onDeactivation(Player p, String level, NonStackableItemType a);

	public ItemStack getItemStack(int x) {
		ItemMeta m = this.getItemMeta();
		m.setDisplayName(this.getAppliedBookName() + " " + Book.getRomanNumeral(x));
		ItemStack is = this.getItemStack();
		is.setItemMeta(m);
		return is;
	}

	protected int getRandomSuccessChance() {
		int rand = r.nextInt(101);
		if (rand > 50)
			return rand;
		else {
			return getRandomSuccessChance();
		}
	}

	protected int getRandomDestroyChance() {
		return r.nextInt(101);
	}

	protected UUID getRandomNumberLore() {
		return UUID.randomUUID();
	}

	protected String getRandomLevel(int maxLevel) {
		switch (r.nextInt(maxLevel) + 1) {

		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		case 11:
			return "XI";
		case 12:
			return "XII";
		case 13:
			return "XIII";
		case 14:
			return "XIV";
		case 15:
			return "XV";
		default:
			return "I";

		}

	}

	public static Book createDefaultBook() {
		ItemStack is = new ItemStack(Material.AIR);
		return new Book(is, is.getItemMeta(), is.getItemMeta().getLore(), 0, BookType.COMMON_BOOK, "DEFAULT BOOK",
				"DEFAULT BOOK", null) {

			@Override
			protected void onActivation(Player p, String level, NonStackableItemType t) {
				// TODO Auto-generated method stub

			}

			@Override
			protected void onDeactivation(Player p, String level, NonStackableItemType t) {
				// TODO Auto-generated method stub

			}

		};
	}

	public static void addPotionEffect(PotionEffectType t, Player p, String level) {
		if (!p.hasPotionEffect(t)) {
			p.removePotionEffect(t);
			p.addPotionEffect(new PotionEffect(t, Integer.MAX_VALUE, Integer.parseInt(level) - 1, true));
		} else {
			if (BookManager.getPotionEffect(p.getActivePotionEffects(), t).getAmplifier() >= Integer.parseInt(level))
				return;
			else {
				p.removePotionEffect(t);
				p.addPotionEffect(new PotionEffect(t, Integer.MAX_VALUE, Integer.parseInt(level) - 1, true));
			}
		}

	}

	public static void addStackPotionEffect(PotionEffectType t, Player p, String level) {
		PotionEffect p1 = BookManager.getPotionEffect(p.getActivePotionEffects(), t);
		if (p1 == null) {
			p.addPotionEffect(new PotionEffect(t, Integer.MAX_VALUE, Integer.parseInt(level) - 1, true));
		} else {
			p.removePotionEffect(t);
			p.addPotionEffect(
					new PotionEffect(t, Integer.MAX_VALUE, p1.getAmplifier() + (Integer.parseInt(level)), true));
		}

	}

	public static void addPotionEffect(PotionEffectType t, Player p, int level, int time) {

		PotionEffect p1 = BookManager.getPotionEffect(p.getActivePotionEffects(), t);
		if (p1 == null) {
			p.addPotionEffect(new PotionEffect(t, time, level - 1, true));
		} else {
			p.removePotionEffect(t);
			p.addPotionEffect(new PotionEffect(t, time, p1.getAmplifier() + level), true);
		}

	}

	public static void removePotionEffect(PotionEffectType t, Player p, String level) {
		PotionEffect p1 = BookManager.getPotionEffect(p.getActivePotionEffects(), t);
		if (p1 == null)
			return;

		p.removePotionEffect(t);
		int amp = p1.getAmplifier() - (Integer.parseInt(level));
		if (amp >= 0) {
			p.addPotionEffect(new PotionEffect(t, Integer.MAX_VALUE, amp, true));
		}
	}

	public static void removePotionEffect(PotionEffectType t, Player p) {
		PotionEffect p1 = BookManager.getPotionEffect(p.getActivePotionEffects(), t);
		if (p1 == null)
			return;
		p.removePotionEffect(t);

	}

	protected void setMinArmorValue(int minArmorValue) {
		this.minArmorValue = minArmorValue;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemMeta getItemMeta() {
		return itemMeta;
	}

	public void setItemMeta(ItemMeta itemMeta) {
		this.itemMeta = itemMeta;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAppliedBookName() {
		return appliedBookName;
	}

	public void setAppliedBookName(String appliedBookName) {
		this.appliedBookName = appliedBookName;
	}

	public List<Material> getApplicableItems() {
		return applicableItems;
	}

	public void setApplicableItems(List<Material> applicableItems) {
		this.applicableItems = applicableItems;
	}

	public int getMinArmorValue() {
		return minArmorValue;
	}

	public static String getRomanNumeral(int number) {
		LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
		roman_numerals.put("M", 1000);
		roman_numerals.put("CM", 900);
		roman_numerals.put("D", 500);
		roman_numerals.put("CD", 400);
		roman_numerals.put("C", 100);
		roman_numerals.put("XC", 90);
		roman_numerals.put("L", 50);
		roman_numerals.put("XL", 40);
		roman_numerals.put("X", 10);
		roman_numerals.put("IX", 9);
		roman_numerals.put("V", 5);
		roman_numerals.put("IV", 4);
		roman_numerals.put("I", 1);
		String res = "";
		for (Entry<String, Integer> entry : roman_numerals.entrySet()) {
			int matches = number / entry.getValue();
			res += repeat(entry.getKey(), matches);
			number = number % entry.getValue();
		}
		return res;
	}

	private static String repeat(String s, int n) {
		if (s == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	public boolean specialRequirements(InventoryClickEvent e) {
		return true;
	}

	protected WeaponType getWeaponType(Material type) {
		switch (type) {
		case DIAMOND_SWORD:
			return WeaponType.SWORD;
		case IRON_SWORD:
			return WeaponType.SWORD;
		case GOLD_SWORD:
			return WeaponType.SWORD;
		case STONE_SWORD:
			return WeaponType.SWORD;
		case WOOD_SWORD:
			return WeaponType.SWORD;
		case DIAMOND_AXE:
			return WeaponType.AXE;
		case IRON_AXE:
			return WeaponType.AXE;
		case GOLD_AXE:
			return WeaponType.AXE;
		case STONE_AXE:
			return WeaponType.AXE;
		case WOOD_AXE:
			return WeaponType.AXE;
		case DIAMOND_SPADE:
			return WeaponType.SHOVEL;
		case IRON_SPADE:
			return WeaponType.SHOVEL;
		case GOLD_SPADE:
			return WeaponType.SHOVEL;
		case STONE_SPADE:
			return WeaponType.SHOVEL;
		case WOOD_SPADE:
			return WeaponType.SHOVEL;
		case DIAMOND_PICKAXE:
			return WeaponType.PICKAXE;
		case IRON_PICKAXE:
			return WeaponType.PICKAXE;
		case GOLD_PICKAXE:
			return WeaponType.PICKAXE;
		case STONE_PICKAXE:
			return WeaponType.PICKAXE;
		case WOOD_PICKAXE:
			return WeaponType.PICKAXE;
		default:
			break;
		}
		return null;

	}

	public static HashMap<Player, HashMap<PotionEffectType, Vector2D<Integer, Integer>>> getPotionMap() {
		return potionMap;
	}

	public static void setPotionMap(HashMap<Player, HashMap<PotionEffectType, Vector2D<Integer, Integer>>> potionMap) {
		Book.potionMap = potionMap;
	}
	
	protected void forceRemoveEffect(PotionEffectType type, Player player) {
		player.removePotionEffect(type);
	}
	
	protected boolean random(double d, int j) {
		return r.nextInt(j) <= d;
	}

}
