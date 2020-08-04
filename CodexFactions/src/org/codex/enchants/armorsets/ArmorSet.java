package org.codex.enchants.armorsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.codex.enchants.books.ArmorEquipEvent;
import org.codex.enchants.books.ArmorListener;
import org.codex.enchants.books.ArmorType;
import org.codex.enchants.books.ArmorUnequipEvent;
import org.codex.enchants.books.BookManager;
import org.codex.enchants.books.EquipMethod;
import org.codex.enchants.books.Hardened;
import org.codex.factions.FactionsMain;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;



public abstract class ArmorSet {
	//Armor value is the ranking of the value. 5 = diamond.
	protected int armor_value = 1;
	protected Material mHelmet = Material.LEATHER_HELMET;
	protected Material mChestplate = Material.LEATHER_CHESTPLATE;
	protected Material mLeggings = Material.LEATHER_LEGGINGS;
	protected Material mBoots = Material.LEATHER_BOOTS;
	/**
	 * The lore applied to all of the items.
	 */
	protected List<String> lore = new ArrayList<>();
	/**
	 * The defense points for the new armor set.
	 */
	protected double defensePoints = 20;
	 
	/**
	 * full diamond set defense points.
	 */
	protected static final int DIAMOND_DEFENSE_POINTS = 20;
	protected String name = ChatColor.GRAY + "Default Armor Set";
	protected boolean isLeather = false;
	protected Color leatherColor = Color.RED;
	protected int durabilityMultiplier = 0;
	protected static final int DIAMOND_HELMET_DURABILITY = 363;
	protected static final int DIAMOND_CHESTPLATE_DURABILITY = 528;
	protected static final int DIAMOND_LEGGINGS_DURABILITY = 495;
	protected static final int DIAMOND_BOOTS_DURABILITY = 429;
	protected final String plus = ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.BLUE + "" + ChatColor.BOLD + "+" + ChatColor.GRAY + "" + ChatColor.BOLD + "]";
	protected final String minus = ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.BLUE + "" + ChatColor.BOLD + "-" + ChatColor.GRAY + "" + ChatColor.BOLD + "]";
	
	public int getDurabilityMultiplier() {
		return durabilityMultiplier;
	}
	public void setDurabilityMultiplier(int durability) {
		this.durabilityMultiplier = durability;
	}
	public boolean isLeather() {
		return isLeather;
	}
	public void setLeather(boolean isLeather) {
		this.isLeather = isLeather;
	}
	public Color getLeatherColor() {
		return leatherColor;
	}
	public void setLeatherColor(Color leatherColor) {
		this.leatherColor = leatherColor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDefensePoints() {
		return defensePoints;
	}
	public void setDefensePoints(double defensePoints) {
		this.defensePoints = defensePoints;
	}
	public int getArmor_value() {
		return armor_value;
	}
	public void setArmor_value(int armor_value) {
		this.armor_value = armor_value;
	}
	public Material getmHelmet() {
		return mHelmet;
	}
	public void setmHelmet(Material mHelmet) {
		this.mHelmet = mHelmet;
	}
	public Material getmChestplate() {
		return mChestplate;
	}
	public void setmChestplate(Material mChestplate) {
		this.mChestplate = mChestplate;
	}
	public Material getmLeggings() {
		return mLeggings;
	}
	public void setmLeggings(Material mLeggings) {
		this.mLeggings = mLeggings;
	}
	public Material getmBoots() {
		return mBoots;
	}
	public void setmBoots(Material mBoots) {
		this.mBoots = mBoots;
	}
	public List<String> getLore() {
		return lore;
	}
	public void setLore(List<String> lore) {
		this.lore = lore;
	}
	
	public abstract ItemStack getItemStack(ArmorType t);
	

	protected ItemStack createItemStack(ArmorType t, ArmorSet a, ChatColor c) {
		ItemStack is = null;
		int durability = 0;
		if(t == ArmorType.HELMET) {
			durability = a.getDurabilityMultiplier() * ArmorSet.DIAMOND_HELMET_DURABILITY;
			is = new ItemStack(a.mHelmet);
		}else if(t == ArmorType.CHESTPLATE) {
			durability = a.getDurabilityMultiplier() * ArmorSet.DIAMOND_CHESTPLATE_DURABILITY;
			is = new ItemStack(a.mChestplate);
		}else if(t == ArmorType.LEGGINGS) {
			durability = a.getDurabilityMultiplier() * ArmorSet.DIAMOND_LEGGINGS_DURABILITY;
			is = new ItemStack(a.mLeggings);
		}else if(t == ArmorType.BOOTS) {
			durability = a.getDurabilityMultiplier() * ArmorSet.DIAMOND_BOOTS_DURABILITY;
			is = new ItemStack(a.mBoots);
		} 
		
		
		List<String> templore = a.getLore();
		templore.add(ChatColor.RESET + "" + c + "Durability : " + durability);
		if(a.isLeather()) {
		LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
		im.setColor(a.getLeatherColor());
		im.setLore(templore);
		im.setDisplayName(ChatColor.RESET + "" + a.getName() + ArmorType.toString(t));
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	
		is.setItemMeta(im);
		}else {
		ItemMeta im = is.getItemMeta();
		im.setLore(templore);
		im.setDisplayName(ChatColor.RESET + "" + a.getName() + ArmorType.toString(t));
		is.setItemMeta(im);
		}
			
		return is;
	}
	
	
	
	
	public double dmg(double rawDmg, int defensePoints, ArmorType t) {
		defensePoints *= ArmorType.getDiamondArmorValue(t) / DIAMOND_DEFENSE_POINTS;
		double dmg = rawDmg * (1 - ((Math.min(20, Math.max(defensePoints/5, defensePoints - (rawDmg / 2)))) / 25));
		Bukkit.getLogger().info(dmg + " - 1");
		return dmg;
	}
	
	protected boolean testEquip(ArmorEquipEvent e, ArmorSet set) {
		
		ItemStack is = e.getNewArmor();
		try {
		if(set.getUAID().equals(getArmorSet(is).getUAID())) {
			
			return true;
		}
		}catch(NullPointerException e2){
			return false;
		}
		return false;
	}
	
	protected boolean testUnEquip(ArmorUnequipEvent e, ArmorSet set) {
		
		ItemStack is = e.getOldArmor();
		try {
			if(set.getUAID().equals(getArmorSet(is).getUAID())) {
	
				return true;
			}
			}catch(NullPointerException e2){
				return false;
			}
			return false;
	}

	public double dmg(double rawDmg, int defensePoints) {
	double test = ((Math.min(20, Math.max(defensePoints/5, defensePoints - (rawDmg / 2)))) / 25);
	double dmg = rawDmg * (1 - test);
	return dmg;
}
	public double dmg(double rawDmg, int defensePoints, int maximumDefence) {
		double test = ((Math.min(maximumDefence, Math.max(defensePoints/5, defensePoints - (rawDmg / 2)))) / 25);
		double dmg = rawDmg * (1 - test);
		return dmg;
	}
	public static boolean isArmor(ArmorSet as, ItemStack is) {
		if(!is.hasItemMeta())return false;
		if(!is.getItemMeta().hasLore())return false;
		
			for(String s : is.getItemMeta().getLore()) {
				if(s.equals(as.getUAID()))return true;
				
			}	
		return false;
	}
	
	public ArmorSet getArmorSet(ItemStack is) {
		for(ArmorSets a : ArmorSets.values()) {
			for(String s : is.getItemMeta().getLore()) {
				ArmorSet as = ArmorSets.getArmorSetFromSetType(a);
				if(as.getUAID().equals(s)) {
					return as;
				}
			}
		}
		return null;
		
		
	}
	
	public abstract String getUAID();
	
	
	public boolean damageCause(EntityDamageEvent e) {
		for(DamageCause d : this.getAcceptableCauses()) {
			if(e.getCause() == d) {
				return true;		
			}
			
		}
		return false;
	}
	private DamageCause[] getAcceptableCauses() {
		DamageCause[] d = {DamageCause.ENTITY_ATTACK, DamageCause.FIRE, DamageCause.PROJECTILE,  DamageCause.CONTACT, DamageCause.BLOCK_EXPLOSION, 
				DamageCause.ENTITY_EXPLOSION, DamageCause.LAVA, DamageCause.LIGHTNING,  DamageCause.WITHER, DamageCause.MELTING, DamageCause.THORNS};
		
		return d;
	}
	public boolean damageCause(EntityDamageEvent e, DamageCause[] added) {
		DamageCause[] list =  (DamageCause[]) add(this.getAcceptableCauses(), added);
	
		for(DamageCause d : list) {
			if(e.getCause() == d)return true;
		}
		return false;
	}
	
	 private Object[] add(Object[] arr, Object[] elements){
	        Object[] tempArr = new Object[arr.length+elements.length];
	        System.arraycopy(arr, 0, tempArr, 0, arr.length);
	        
	        for(int i=0; i < elements.length; i++)
	            tempArr[arr.length+i] = elements[i];
	        return tempArr;
	        
	    }

	 
	 protected Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> getApplyMap( HashMap<Player, HashMap<ArmorType, Boolean>> a, ArmorEquipEvent e, ArmorSet s){
		 
		 	boolean temp = this.testEquip(e, s);
			if(temp) {
			Player p = e.getPlayer();
			HashMap<ArmorType, Boolean> m = a.containsKey(p) ? a.get(p) : new HashMap<>();
			m.put(ArmorListener.getArmorType(e.getNewArmor().getType()), true);
			a.put(p, m);
			Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> v = new Vector2D<>(true, a);
				return v;
			} 
			Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> v = new Vector2D<>(false, a);
			return v;
		 
	 }
	 
	 protected Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> getUnapplyMap( HashMap<Player, HashMap<ArmorType, Boolean>> a, ArmorUnequipEvent e, ArmorSet s){
		 boolean temp = this.testUnEquip(e, s);
		 
		 if(temp) {
				Player p = e.getPlayer();
				HashMap<ArmorType, Boolean> m ;
				if(a.containsKey(p))m = a.get(p);
				else m = new HashMap<>();
				m.put(ArmorListener.getArmorType(e.getOldArmor().getType()), false);
				a.put(p, m);
				Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> v = new Vector2D<>(true, a);
				return v;
			} 
			Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> v = new Vector2D<>(false, a);
			return v;
	 }
	 
	 @EventHandler
	 public abstract void onApply(ArmorEquipEvent e);
	 @EventHandler
	 public abstract void onUnApply(ArmorUnequipEvent e);
	 @EventHandler
	 public abstract void onDamage(EntityDamageEvent e);
	 @EventHandler
	 public abstract void onDurabilityLoss(PlayerItemDamageEvent e);
	 public abstract HashMap<Player, HashMap<ArmorType, Boolean>> getApplyMap();
	 public abstract void setApplyMap(HashMap<Player, HashMap<ArmorType, Boolean>> m);
	 public abstract int getAmountApplied(Player p, ArmorSet a);
	 @EventHandler
	 public abstract void onArmorChange(ArmorSetChangeEvent e);
	 
	 protected double getDamage(EntityDamageEvent e,  HashMap<Player, HashMap<ArmorType, Boolean>> a, ArmorSet set) {
		 if(e.getEntity() instanceof Player) {
			  double defensePoints = 0;
				Player p = (Player) e.getEntity();	
				if(!this.damageCause(e))return e.getDamage();
				if(!a.containsKey(p))return e.getDamage();	
				if(a.get(p).containsKey(ArmorType.HELMET)) {
					if(a.get(p).get(ArmorType.HELMET)) {
						defensePoints += set.getDefensePoints() * ((double)  3/ (double) 20);
					}
				}
				if(a.get(p).containsKey(ArmorType.CHESTPLATE)) {
					if(a.get(p).get(ArmorType.CHESTPLATE)) {
						defensePoints += set.getDefensePoints() * ((double)  8/ (double) 20);
					}
				}
				if(a.get(p).containsKey(ArmorType.LEGGINGS)) {
					if(a.get(p).get(ArmorType.LEGGINGS)) {
			
						defensePoints += set.getDefensePoints() * ((double)  6/ (double) 20);
					}
				}
				if(a.get(p).containsKey(ArmorType.BOOTS)) {
					if(a.get(p).get(ArmorType.BOOTS)) {
					
						defensePoints += set.getDefensePoints() * ((double) 3/(double) 20);
					}
				}
				
				double nDmg = this.dmg(e.getDamage(), (int) defensePoints);
				return nDmg;
				}
		 return e.getDamage();
	 }
	 
	 
	 
	 
	protected void calculateDurability(PlayerItemDamageEvent e, ArmorSet as, ChatColor durabilityChat) {
		 Player p = e.getPlayer();
			ItemStack is = e.getItem();
			ItemMeta im = is.getItemMeta();
			
			if(ArmorSet.isArmor(as, is)) {
				int durability = 0;
				int temp = 0;
				for(String s: im.getLore()) {
					
					if(s.contains("Durability : ")) {
						durability = Integer.parseInt(s.split(" : ")[1]);
						durability -= e.getDamage();
						String newDurability = durabilityChat + "Durability : " + durability;
						if(Hardened.a().containsKey(p)) {
							if(ArmorListener.getArmorType(is.getType()).equals(Hardened.a().get(p).getVectorTwo())) {
								Random r = new Random();
								int rInt = r.nextInt(100);
								if(rInt <= Hardened.a().get(p).getVectorOne() * 11){
									durability += e.getDamage();
									return;
									
								}
								
							}
						}
						List<String> lore = is.getItemMeta().getLore();
						lore.set(temp, newDurability);
						im.setLore(lore);
						is.setItemMeta(im);
						if(durability <= 0) {
							if(ArmorListener.getArmorType(is.getType()) == ArmorType.HELMET) {
								p.getInventory().setHelmet(new ItemStack(Material.AIR));
								Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p, EquipMethod.BROKE, ArmorType.HELMET, is));
							}
							else if(ArmorListener.getArmorType(is.getType()) == ArmorType.CHESTPLATE) {
								p.getInventory().setChestplate(new ItemStack(Material.AIR));
								Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p, EquipMethod.BROKE, ArmorType.CHESTPLATE, is));
							}
							else if(ArmorListener.getArmorType(is.getType()) == ArmorType.LEGGINGS) {
								p.getInventory().setLeggings(new ItemStack(Material.AIR));
								Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p, EquipMethod.BROKE, ArmorType.LEGGINGS, is));
							}
							else if(ArmorListener.getArmorType(is.getType()) == ArmorType.BOOTS) {
								p.getInventory().setBoots(new ItemStack(Material.AIR));
								Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p, EquipMethod.BROKE, ArmorType.BOOTS, is));
							}
							return;
						}
						
					}
					
					temp++;
				}
				is.setDurability((short) (is.getType().getMaxDurability() - this.getDurabilityLoss(is.getType(), durability, as.getDurabilityMultiplier())));
				
				if(ArmorListener.getArmorType(is.getType()) == ArmorType.HELMET)p.getInventory().setHelmet(is);
				if(ArmorListener.getArmorType(is.getType()) == ArmorType.CHESTPLATE)p.getInventory().setChestplate(is);
				if(ArmorListener.getArmorType(is.getType()) == ArmorType.LEGGINGS)p.getInventory().setLeggings(is);
				if(ArmorListener.getArmorType(is.getType()) == ArmorType.BOOTS)p.getInventory().setBoots(is);
				
				p.updateInventory();
				
			}
	 }
	private short getDurabilityLoss(Material type, int durability, int durabilityMultiplier) {
		int maxDurability = 0;
		int setDurability = type.getMaxDurability();
		ArmorType t = ArmorListener.getArmorType(type);
		switch(t) {
		
		case HELMET:
			maxDurability = durabilityMultiplier * ArmorSet.DIAMOND_HELMET_DURABILITY;
			break;
		case CHESTPLATE:
			maxDurability = durabilityMultiplier * ArmorSet.DIAMOND_CHESTPLATE_DURABILITY;
			break;
		case LEGGINGS:
			maxDurability = durabilityMultiplier * ArmorSet.DIAMOND_LEGGINGS_DURABILITY;
			break;
		case BOOTS:
			maxDurability = durabilityMultiplier * ArmorSet.DIAMOND_BOOTS_DURABILITY;
			break;
		default:
			break;
		}
		short finalDurability = (short) ((setDurability * durability)/maxDurability);
				
				
		
		
		return finalDurability;
	}
	public static void loadSets() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			

				
				ItemStack[] armorContents = {p.getInventory().getHelmet(), p.getInventory().getChestplate(), p.getInventory().getLeggings(), p.getInventory().getBoots()};
				for(ItemStack is: armorContents) {
					
					for(ArmorSets as : ArmorSets.values()) {
						ArmorSet s = ArmorSets.getArmorSetFromSetType(as);
						if(is != null) {
						if(is.hasItemMeta()) { 
						if(is.getItemMeta().hasLore()) {
						for(String s1: is.getItemMeta().getLore()) {
					
							if(s1.contains(s.getUAID())) {
								HashMap<Player, HashMap<ArmorType, Boolean>> map = s.getApplyMap();
								HashMap<ArmorType, Boolean> map2 = map.get(p);
								if(map2 == null) {
									map2 = new HashMap<>();
								}
								map2.put(ArmorListener.getArmorType(is.getType()), true);
								map.put(p, map2 );
								s.setApplyMap(map);
							}
						}
						
						}
					}
					}
					}
				}
		}
		
	}
	
	public static void loadSets(Player p) {
		ItemStack[] armorContents = {p.getInventory().getHelmet(), p.getInventory().getChestplate(), p.getInventory().getLeggings(), p.getInventory().getBoots()};
		for(ItemStack is: armorContents) {
			
			for(ArmorSets as : ArmorSets.values()) {
				
				ArmorSet s = ArmorSets.getArmorSetFromSetType(as);
				if(is != null) {
				if(is.hasItemMeta()) { 
				if(is.getItemMeta().hasLore()) {
				for(String s1: is.getItemMeta().getLore()) {
			
					if(s1.contains(s.getUAID())) {
						HashMap<Player, HashMap<ArmorType, Boolean>> map = s.getApplyMap();
						HashMap<ArmorType, Boolean> map2 = map.get(p);
						if(map2 == null) {
							map2 = new HashMap<>();
						}
						map2.put(ArmorListener.getArmorType(is.getType()), true);
						map.put(p, map2 );
						s.setApplyMap(map);
					}
				}
				
				}
			}
			}
			}
			
		}
	}
	
	
	public static void unloadSets() {
		for(ArmorSets as: ArmorSets.values()) {
			ArmorSet s = ArmorSets.getArmorSetFromSetType(as);
			s.setApplyMap(new HashMap<Player, HashMap<ArmorType,Boolean>>());
		}
		
	}
	
	public static void unloadSets(Player p) {
		for(ArmorSets as: ArmorSets.values()) {
			ArmorSet s = ArmorSets.getArmorSetFromSetType(as);
			HashMap<Player, HashMap<ArmorType, Boolean>> map = s.getApplyMap() == null ? new HashMap<>() : s.getApplyMap();
			map.remove(p);
			s.setApplyMap(map);
		}
	}
	public static int getMaxDiamondDurability(ArmorType armorType) {
		switch(armorType) {
		case HELMET:
			return ArmorSet.DIAMOND_HELMET_DURABILITY;
		case CHESTPLATE:
			return ArmorSet.DIAMOND_CHESTPLATE_DURABILITY;
		case LEGGINGS:
			return ArmorSet.DIAMOND_LEGGINGS_DURABILITY;
		case BOOTS:
			return ArmorSet.DIAMOND_BOOTS_DURABILITY;
		default:
			break;
		}
		return 50;
	}
	
	protected void outPvEDamage(double damage, EntityDamageByEntityEvent e, Set<Player> fullSetApplied) {
		if(e.getDamager() instanceof Player && fullSetApplied.contains(e.getDamager()) && !(e.getEntity() instanceof Player)) {
			e.setDamage(e.getDamage() * damage);
		}
	}
	
	protected void outPvPDamage(double damage, EntityDamageByEntityEvent e, Set<Player> fullSetApplied) {
		if(e.getDamager() instanceof Player && fullSetApplied.contains(e.getDamager()) && (e.getEntity() instanceof Player)) {
			e.setDamage(e.getDamage() * damage);
		}
	}
	
	protected void inPvEDamage(double damage, EntityDamageByEntityEvent e, Set<Player> fullSetApplied) {
		if(!(e.getDamager() instanceof Player) && fullSetApplied.contains(e.getEntity()) && (e.getEntity() instanceof Player)) {
			e.setDamage(e.getDamage() * (1 - damage));
		}
	}
	
	protected void inPvPDamage(double damage, EntityDamageByEntityEvent e, Set<Player> fullSetApplied) {
		if(e.getDamager() instanceof Player && fullSetApplied.contains(e.getEntity()) && (e.getEntity() instanceof Player)) {
			e.setDamage(e.getDamage() * (1 - damage));
		}
	}
	public void reloadEffects(Player p) {
		BookManager.unloadEnchants(p);
		Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {

			@Override
			public void run() {
				BookManager.loadEnchants(p);
			}
			
		}, 1);
		
	}
}
