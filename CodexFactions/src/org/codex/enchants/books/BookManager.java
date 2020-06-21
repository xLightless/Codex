package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.codex.enchants.armorsets.ArmorSet;
import org.codex.enchants.armorsets.ArmorSets;
import org.codex.factions.FactionsMain;

public class BookManager implements Listener {
	public ItemStack cb = new ItemStack(Material.BOOK);
	private ItemMeta cbim = cb.getItemMeta();
	private List<String> cbl = new ArrayList<>();
	public ItemStack rb = new ItemStack(Material.BOOK);
	private ItemMeta rbim = rb.getItemMeta();
	private List<String> rbl = new ArrayList<>();
	public ItemStack mb = new ItemStack(Material.BOOK);
	private ItemMeta mbim = mb.getItemMeta();
	private List<String> mbl = new ArrayList<>();
	public ItemStack lb = new ItemStack(Material.BOOK);
	private ItemMeta lbim = lb.getItemMeta();
	private List<String> lbl = new ArrayList<>();
	public ItemStack myb = new ItemStack(Material.BOOK);
	private ItemMeta mybim = myb.getItemMeta();
	private List<String> mybl = new ArrayList<>();

	protected List<String> lore;
	protected ItemStack is;
	protected ItemMeta im;
	private Random r = new Random();
	
	
	
	
	public BookManager() {
		cbim.setDisplayName(BookType.COMMON_BOOK.getChatColor() + "Common Enchantment Book");
		cbl.add(ChatColor.GRAY + "Right click to open");
		cbim.setLore(cbl);
		cb.setItemMeta(cbim);
		rbim.setDisplayName(BookType.RARE_BOOK.getChatColor() + "Rare Enchantment Book");
		rbl.add(ChatColor.DARK_GREEN + "Right click to open");
		rbim.setLore(rbl);
		rb.setItemMeta(rbim);
		mbim.setDisplayName(BookType.MAJESTIC_BOOK.getChatColor() + "Majestic Enchantment Book");
		mbl.add(ChatColor.GRAY + "Right click to open");
		mbim.setLore(mbl);
		mb.setItemMeta(mbim);
		lbim.setDisplayName(BookType.LEGENDARY_BOOK.getChatColor() + "Legendary Enchantment Book");
		lbl.add(ChatColor.GRAY + "Right click to open");
		lbim.setLore(lbl);
		lb.setItemMeta(lbim);
		mybim.setDisplayName(BookType.MYSTICAL_BOOK.getChatColor() + "Mystical Enchantment Book");
		mybl.add(ChatColor.GRAY + "Right click to open");
		mybim.setLore(mybl);
		myb.setItemMeta(mybim);
	}
	
	
	@EventHandler
	public void onBookOpen(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack it = e.getItem();
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(it == null || it.getType() == Material.AIR || it.getItemMeta() == null) return;
			
			else if(it.getItemMeta().equals(cb.getItemMeta())){
				p.getInventory().addItem(this.getRandomBook(BookType.COMMON_BOOK).getItemStack());
				p.getInventory().removeItem(cb);
				e.setCancelled(true);
				p.updateInventory();
				Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}else if(it.getItemMeta().equals(rb.getItemMeta())){
				p.getInventory().addItem(this.getRandomBook(BookType.RARE_BOOK).getItemStack());
				p.getInventory().removeItem(rb);
				e.setCancelled(true);
				p.updateInventory();
				Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}else if(it.getItemMeta().equals(mb.getItemMeta())){
				p.getInventory().addItem(this.getRandomBook(BookType.MAJESTIC_BOOK).getItemStack());
				p.getInventory().removeItem(mb);
				e.setCancelled(true);
				p.updateInventory();
				Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}else if(it.getItemMeta().equals(lb.getItemMeta())){
				p.getInventory().addItem(this.getRandomBook(BookType.LEGENDARY_BOOK).getItemStack());
				p.getInventory().removeItem(lb);
				e.setCancelled(true);
				p.updateInventory();
				Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}else if(it.getItemMeta().equals(myb.getItemMeta())){
				p.getInventory().addItem(this.getRandomBook(BookType.MYSTICAL_BOOK).getItemStack());
				p.getInventory().removeItem(myb);
				e.setCancelled(true);
				p.updateInventory();
				Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}else {
				return;
			}
		}else {
			return;
		}
		
		
	
		
	}
	
	
	@EventHandler
	public void onBookApply(InventoryClickEvent e) {
		int armorSetValue = 0;
		ArmorSet asHolder = null;
		if(e.getWhoClicked() instanceof Player) {
		Player p = (Player) e.getWhoClicked();
		ItemStack ci = e.getCurrentItem();
		ItemStack cu = e.getCursor();
		
		 
		
		if(ci == null || cu == null || ci.getType() == Material.AIR || cu.getType() == Material.AIR) return;
		List<String> nl = ci.getItemMeta().getLore();
		if(nl == null) {
			nl = new ArrayList<String>();
		}
		if(!(cu.getType() == Material.BOOK)) return;
		
		for(EnchantType e2: getAllEnchantTypes()) {

			if((cu.getItemMeta().getDisplayName().contains(EnchantType.getEnchantClass(e2).getBookName()) || cu.getItemMeta().getLore().equals(EnchantType.getEnchantClass(e2).getLore()))){
			 Book b = EnchantType.getEnchantClass(e2);
			if(b.specialRequirements(e)) {
			for(ArmorSets a : ArmorSets.values()) {
			ArmorSet as = ArmorSets.getArmorSetFromSetType(a);
			if(ArmorSet.isArmor(as, ci)) {
			
				asHolder = as;
				armorSetValue=as.getArmor_value();
				
			}
			}
			for(Material m : b.getApplicableItems()) {
				
				 ItemStack nit = ci;
				 
				 ItemMeta nim = ci.getItemMeta();
				 if(nim.getLore() != null) { for(String s : nim.getLore()) { if(s.contains(b.getAppliedBookName())) {
					 	int l1 = BookManager.getLevel(s);
					 	int l2 = BookManager.getLevel((cu.getItemMeta().getDisplayName()));
					 		if(l1>=l2) {
					 			p.sendMessage(ChatColor.RED + "You already have the enchant");
					 			return;
					 		}else 
					 			nl.remove(s);
				 		}
				 	}
				 }
				 
				 
					 boolean temp = false;
					 boolean temp2 = false;
				 if(b.getMinArmorValue() <= armorSetValue && ArmorListener.getArmorType(ci.getType()) == ArmorListener.getArmorType(m) && ArmorListener.getArmorType(ci.getType()) != null)temp = true;
				 if(b.getMinArmorValue() <= armorSetValue && ArmorListener.getWeaponType(ci.getType()) == ArmorListener.getWeaponType(m) &&  ArmorListener.getWeaponType(ci.getType()) != null)temp2 = true;
				 
		
				 if(ci.getType() == m || temp || temp2) {
					 if((p.getGameMode() == GameMode.CREATIVE)) {
							p.sendMessage(ChatColor.RED + "You cannot enchant an item in creative mode!");
							return;
					 }
				 if((apply(cu))) { 
					 if(destroy(cu)) {
				     p.sendMessage(ChatColor.RED + "Your enchant failed and broke the piece");
				     e.getWhoClicked().setItemOnCursor(null);
				     e.setCurrentItem(null);
				     Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.LAVA_POP, 1, 1);
				     return;
					 }else {
						 p.sendMessage(ChatColor.RED + "Your enchant did not apply");
						 Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.WOOD_CLICK, 3, 3);
						 return;
					 }
				 }else {
				
				 boolean temp3 = false;
				 int addedSlot = 0;
				 e.setCancelled(true);
				 if(armorSetValue <= 0) {
				 try{
					 String ls = "";
						
					 
					 
					 
						for(String s : nl) {
				
							if(s.contains("Energy : ")) {
								ls = s.split(" : ")[1];
								continue;
							}
						}
						int oldamount = Integer.parseInt(ls);
						nl.set(BookManager.getEnergySlot(nl), ChatColor.RESET + "" +  b.getAppliedBookName() + " " + Book.getRomanNumeral(BookManager.getLevel(cu.getItemMeta().getDisplayName())));
						nl.add(ChatColor.RESET + "" + ChatColor.DARK_AQUA + "Energy : " + (oldamount));
						addedSlot = nl.size() - 1;
						armorSetValue = -1;
						temp3 = true;
				 }catch(Exception e3) {
				   nl.add(ChatColor.RESET + "" + b.getAppliedBookName() + " " + Book.getRomanNumeral(BookManager.getLevel(cu.getItemMeta().getDisplayName())));
				 	temp3 = true;
					armorSetValue = -1;
					addedSlot = nl.size() - 1;
				 }
				
					 String ls = "";
					 int i = 0;
					 for(String s : nl) {
						 if(s.contains("|")) {
						ls = s;
						break;
						 }
						 i++;
					 }
					 if(ls != "") {
					 
						 nl.set(i, ChatColor.RESET + "" +  b.getAppliedBookName() + " " + Book.getRomanNumeral(BookManager.getLevel(cu.getItemMeta().getDisplayName())));
						if(temp3) nl.remove(addedSlot - 1);
					 nl.add(ls);
					 armorSetValue = -1;
					 }else {
						 if(!temp3) {
					 nl.add(ChatColor.RESET + "" + b.getAppliedBookName() + " " + Book.getRomanNumeral(BookManager.getLevel(cu.getItemMeta().getDisplayName())));
					 armorSetValue = -1;
						 }
					 }
				 
				 }
				 else {
					 
					 	nl = this.getArmorSetLore(nl, asHolder,ChatColor.RESET + "" +  b.getAppliedBookName() + " " + Book.getRomanNumeral(BookManager.getLevel(cu.getItemMeta().getDisplayName())));
					 
				 }
				 nim.setLore(nl);
				 nit.setItemMeta(nim);
				 
				 e.setCurrentItem(nit);
				 e.getWhoClicked().setItemOnCursor(null);
				
				 p.getInventory().setItem(e.getSlot(), e.getCurrentItem());
				 p.getItemOnCursor().setItemMeta(nim);
				 p.updateInventory();
				 Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.FIZZ, 1, 1);
				 return;
				 }
				 }
			 }
				 
			}else {
				p.sendMessage(ChatColor.RED + "You must fufill the special requirements for this enchant");
			}
			
			 }
			 }
		}
		}
	
	public static int getLevel(String s) {
		for(EnchantType t: BookManager.getAllEnchantTypes()) {
			String s1 = EnchantType.getEnchantClass(t).getAppliedBookName();
			if(s.contains(s1)) {
				try {
					return BookManager.getNumberFromNumeral(s.replace(s1 + " ", ""));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	public static Book getBook(String s) {
		for(EnchantType t: BookManager.getAllEnchantTypes()) {
			String s1 = EnchantType.getEnchantClass(t).getAppliedBookName();
			if(s.contains(s1)) {
				return EnchantType.getEnchantClass(t);
			}
		}
		return null;
	}

	private boolean destroy(ItemStack cu) {
		for(String s:  cu.getItemMeta().getLore()) {
			if(s.contains("Destroy Rate :")) {
				String[] ns = s.split(" : ");
				int pS = Integer.valueOf(ns[1]); // percent Destroy
				if(pS > r.nextInt(101)) { 
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}

	private boolean apply(ItemStack is) {
		
		for(String s:  is.getItemMeta().getLore()) {
			if(s.contains("Success Rate :")) {
				String[] ns = s.split(" : ");
				int pS = Integer.valueOf(ns[1]); // percent Success
				if(pS > r.nextInt(101)) {
					return false;
				}else {
					return true;
				}
			}
		}
		return false;
	}

	protected static EnchantType[] getAllEnchantTypes() {
		EnchantType[] et = EnchantType.values();
				
				return et;
	}
	
	private Book getRandomBook(BookType type) {
		
		Random rand = new Random();
		int ri = rand.nextInt(EnchantType.getAllEnchantBooks().length);
		Book b = EnchantType.getAllEnchantBooks()[ri];
		if(!(b.getBookType() == type)) return getRandomBook(type);
		return b;
	}

	protected int getRandomSuccessChance() {
		int rand = r.nextInt(101);
		if(rand > 50)
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
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent e) {
		Player p = e.getPlayer();
		
		ItemStack a = e.getNewArmor();
		if(a == null)return;
		if(a.getItemMeta() == null)return;
		if(!(a.hasItemMeta() || a.getItemMeta().hasLore())) return;
		if( a.getItemMeta().getLore() == null) return;
		for(String s : a.getItemMeta().getLore()) {
			for(EnchantType et: getAllEnchantTypes()) {
				
				try {
				if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
					Book b = EnchantType.getEnchantClass(et);
					b.onActivation(p, BookManager.getLevel(s) + "", e.getType());
					break;
				}
				}catch(NullPointerException | ArrayIndexOutOfBoundsException e2) {
					continue;
				}
			}
		}
	}
	@EventHandler
	public void onArmorUnEquip(ArmorUnequipEvent e) {
		Player p = e.getPlayer();
		ItemStack a = e.getOldArmor();
		if(a == null) return;
		if(!(a.hasItemMeta() || a.getItemMeta().hasLore())) return;
		if( a.getItemMeta().getLore() == null) return;
		for(String s : a.getItemMeta().getLore()) {
			for(EnchantType et: getAllEnchantTypes()) {
				
				try {
				if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
					
					Book b = EnchantType.getEnchantClass(et);
					b.onDeactivation(p, BookManager.getLevel(s) + "", e.getType());
				}
				}catch(NullPointerException | ArrayIndexOutOfBoundsException e2) {
					continue;
				}
			}
		}
		
		
	}

	public static int getNumberFromNumeral(String news) throws Throwable {
		String s = ChatColor.stripColor(news);
		int finalValue = 0;
		int[] is = new int[s.length()];
		for (int i = 0; i <= s.length() - 1; i++) {
			char c = s.charAt(i);
			is[i] = BookManager.value(c);
		}
		int t = 0;
		for (; t <= is.length - 1; t++) {
			if (t + 1 >= is.length) {
				finalValue += is[t];
				break;
			} else if (is[t] >= is[t + 1]) {
				finalValue += is[t];
			} else if (t + 2 >= is.length) {
				finalValue += (is[t + 1] - is[t]);
				break;
			} else {
				finalValue += (is[t + 1] - is[t]);
			}
		}

		return finalValue;
	}

	private static int value(char c) throws Throwable {
		switch (c) {
		case 'I':
			return 1;
		case 'V':
			return 5;
		case 'X':
			return 10;
		case 'L':
			return 50;
		case 'C':
			return 100;
		case 'D':
			return 500;
		case 'M':
			return 1000;
		default:
			throw new Throwable("Not a valid Roman Numeral");
		}
	}
	
	@EventHandler
	public void onItemSwap(ItemSwapEvent e) {
		ItemStack is = e.getWeapon();
		Player p = e.getPlayer();
		if(is == null)return;
		if(isWeapon(is.getType())) {
			if(!(is.hasItemMeta() || is.getItemMeta().hasLore())) return;
			if( is.getItemMeta().getLore() == null) return;
			for(String s : is.getItemMeta().getLore()) {
				for(EnchantType et: getAllEnchantTypes()) {
					try {
					if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
						
						Book b = EnchantType.getEnchantClass(et);
						b.onActivation(p, BookManager.getLevel(s) + "", e.getType());
					}
					}catch(NullPointerException e2) {
						continue;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onItemSwap(ItemUnuseEvent e) {
		ItemStack is = e.getWeapon();
		Player p = e.getPlayer();
		if(is == null)return;
		if(isWeapon(is.getType())) {
			if(!(is.hasItemMeta() || is.getItemMeta().hasLore())) return;
			if( is.getItemMeta().getLore() == null) return;
			for(String s : is.getItemMeta().getLore()) {
				for(EnchantType et: getAllEnchantTypes()) {
					try {
					if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
						Book b = EnchantType.getEnchantClass(et);
						b.onDeactivation(p, BookManager.getLevel(s) + "", e.getType());
					}
					}catch(NullPointerException | ArrayIndexOutOfBoundsException e2) {
						continue;
					}
				}
			}
		}
	}

	private static boolean isWeapon(Material type) {
		switch(type) {
		case DIAMOND_SWORD:
			return true;
		case IRON_SWORD:
			return true;
		case GOLD_SWORD:
			return true;
		case STONE_SWORD:
			return true;
		case WOOD_SWORD:
			return true;
		case DIAMOND_AXE:
			return true;
		case IRON_AXE:
			return true;
		case GOLD_AXE:
			return true;
		case STONE_AXE:
			return true;
		case WOOD_AXE:
			return true;
		case DIAMOND_SPADE:
			return true;
		case IRON_SPADE:
			return true;
		case GOLD_SPADE:
			return true;
		case STONE_SPADE:
			return true;
		case WOOD_SPADE:
			return true;
		case DIAMOND_PICKAXE:
			return true;
		case IRON_PICKAXE:
			return true;
		case GOLD_PICKAXE:
			return true;
		case STONE_PICKAXE:
			return true;
		case WOOD_PICKAXE:
			return true;
		default:
			break;
		}
		return false;
	}

 
	public static void loadEnchants() {
		for(Player p: Bukkit.getOnlinePlayers()) {
			for(ItemStack is : p.getInventory().getArmorContents()) {
				if(!(is == null)) {
				if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
					for(String s : is.getItemMeta().getLore()) {
						for(EnchantType et: getAllEnchantTypes()) {
							try {
							if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
								
								Book b = EnchantType.getEnchantClass(et);
								
								b.onActivation(p, BookManager.getLevel(s) + "", ArmorListener.getArmorType(is.getType()));
							}
						}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
							
						}
						}
					}
					
				}
			}
			}
			ItemStack is = p.getInventory().getItemInHand();
			if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
				for(String s : is.getItemMeta().getLore()) {
					for(EnchantType et: getAllEnchantTypes()) {
						try {
						if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
							
							Book b = EnchantType.getEnchantClass(et);
							b.onActivation(p, BookManager.getLevel(s) + "", FactionsMain.getWeaponType(is.getType()));
						}
						}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
							
						}
					}
				}
				}
			}
		}
		
	public static void unloadEnchants() {
		for(Player p: Bukkit.getOnlinePlayers()) {
			for(ItemStack is : p.getInventory().getArmorContents()) {
				if(!(is == null)) {
				if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
					for(String s : is.getItemMeta().getLore()) {
						for(EnchantType et: getAllEnchantTypes()) {
							try {
							if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
								
								Book b = EnchantType.getEnchantClass(et);
								b.onDeactivation(p, BookManager.getLevel(s) + "", ArmorListener.getArmorType(is.getType()));
							}
							}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
								
							}
						}
					}
					
				}
			}
			}
			ItemStack is = p.getItemInHand();
			
			if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
				for(String s : is.getItemMeta().getLore()) {
					for(EnchantType et: getAllEnchantTypes()) {
						try {
						if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
							
							Book b = EnchantType.getEnchantClass(et);
							b.onDeactivation(p, BookManager.getLevel(s) + "", ArmorListener.getWeaponType(is.getType()));
						}
						}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
							
						}
					}
				}
				}
			}
		}
	
	public static void unloadEnchants(Player p) {
		
			for(ItemStack is : p.getInventory().getArmorContents()) {
				if(!(is == null)) {
				if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
					for(String s : is.getItemMeta().getLore()) {
						for(EnchantType et: getAllEnchantTypes()) {
							try {
							if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
								
								Book b = EnchantType.getEnchantClass(et);
								b.onDeactivation(p, BookManager.getLevel(s) + "", ArmorListener.getArmorType(is.getType()));
							}
							}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
								
							}
						}
					}
					
				}
			}
			}
			ItemStack is = p.getItemInHand();
			if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
				for(String s : is.getItemMeta().getLore()) {
					for(EnchantType et: getAllEnchantTypes()) {
						try {
						if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
							
							Book b = EnchantType.getEnchantClass(et);
							b.onDeactivation(p, BookManager.getLevel(s) + "", ArmorListener.getWeaponType(is.getType()));
						}
						}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
							
						}
					}
				}
				}
			}
		
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		unloadEnchants(p);
	}
	
	
	


	public static boolean checkWeapon(ItemStack is) {
		if(isWeapon(is.getType())) {
			return true;
		}
		return false;
		
	}
	
	public static int getEnergySlot(List<String> lore) {
		int x = 0;
		for(String s : lore) {
			x++;
			if(s.contains("Energy : ")) {
				return x - 1;
			}
		}
		
		throw new NullPointerException();	
	}
	
	
	private List<String> getArmorSetLore(List<String> origLore, ArmorSet a, String added){
		List<String> returnable = new ArrayList<>();
		int x = 0;
		for(String s: origLore) {
			if(s.contains(a.getUAID())) {
				break;
			}
			x++;
		}

		for(int y = origLore.size(); y >= x; y--) {
			if(y == origLore.size())origLore.add(origLore.get(y-1));
			else {
				origLore.set(y + 1, origLore.get(y));
			}
		}
		
		origLore.set(x,added);
				
		returnable = origLore;
		return returnable;
	}

	
	public static PotionEffect getPotionEffect(Collection<PotionEffect> ps, PotionEffectType t) {
		for(PotionEffect p : ps) {
			if(p.getType().equals(t))return p;
		}
		return null;
		
	}


	public static void loadEnchants(Player p) {
		for(ItemStack is : p.getInventory().getArmorContents()) {
			if(!(is == null)) {
			if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
				for(String s : is.getItemMeta().getLore()) {
					for(EnchantType et: getAllEnchantTypes()) {
						try {
						if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
							
							Book b = EnchantType.getEnchantClass(et);
							b.onActivation(p, BookManager.getLevel(s) + "", ArmorListener.getArmorType(is.getType()));
						}
						}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
							
						}
					}
				}
				
			}
		}
		}
		ItemStack is = p.getItemInHand();
		if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
			for(String s : is.getItemMeta().getLore()) {
				for(EnchantType et: getAllEnchantTypes()) {
					try {
					if(BookManager.getBook(s).getAppliedBookName().equals(EnchantType.getEnchantClass(et).getAppliedBookName())){
						
						Book b = EnchantType.getEnchantClass(et);
						b.onActivation(p, BookManager.getLevel(s) + "", ArmorListener.getWeaponType(is.getType()));
					}
					}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
						
					}
				}
			}
			}
	}


	
}
