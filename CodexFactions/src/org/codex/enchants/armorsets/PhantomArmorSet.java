package org.codex.enchants.armorsets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.codex.enchants.books.ArmorEquipEvent;
import org.codex.enchants.books.ArmorType;
import org.codex.enchants.books.ArmorUnequipEvent;
import org.codex.enchants.books.Book;
import org.codex.enchants.books.BookManager;
import org.codex.factions.FactionsMain;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class PhantomArmorSet extends ArmorSet implements Listener {

	protected static HashMap<Player, HashMap<ArmorType, Boolean>> a = new HashMap<>();
	private static HashMap<Player, Integer> applied = new HashMap<>();
	private static Set<Player> fullSetApplied = new HashSet<>();

	public HashMap<Player, HashMap<ArmorType, Boolean>> getApplyMap() {
		return a;
	}

	public void setApplyMap(HashMap<Player, HashMap<ArmorType, Boolean>> a) {
		PhantomArmorSet.a = a;
	}

	public PhantomArmorSet() {
		this.setDefensePoints(50);
		this.setArmor_value(8);
		this.setDurabilityMultiplier(5);
		List<String> lore = new ArrayList<>();
		this.setLeather(true);
		this.setLeatherColor(Color.fromRGB(255, 251, 0));
		lore.add(this.getUAID());
		lore.add(ChatColor.YELLOW + "Phantom Knight's Armor: Armor worn by the legendary Phantom Knight");
		lore.add(ChatColor.YELLOW + "50 Defense Points");
		this.setName(ChatColor.BOLD + "" + ChatColor.YELLOW + "Phantom Knight's ");
		this.setLore(lore);
		this.setmHelmet(Material.LEATHER_HELMET);
		this.setmChestplate(Material.LEATHER_CHESTPLATE);
		this.setmLeggings(Material.LEATHER_LEGGINGS);
		this.setmBoots(Material.LEATHER_BOOTS);
	}

	public ItemStack getItemStack(ArmorType t) {
		return super.createItemStack(t, this, ChatColor.YELLOW);
	}

	@EventHandler
	public void onApply(ArmorEquipEvent e) {
		Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> b = super.getApplyMap(a, e, this);
		a = b.getVectorTwo();
		if (!b.getVectorOne())
			return;
		if (!applied.containsKey(e.getPlayer())) {
			applied.put(e.getPlayer(), 1);
			Bukkit.getPluginManager().callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(),
					ArmorSets.PHANTOM_KNIGHT_ARMOR_SET, applied.get(e.getPlayer())));
		} else {
			applied.put(e.getPlayer(), applied.get(e.getPlayer()) + 1);
			Bukkit.getPluginManager().callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(),
					ArmorSets.PHANTOM_KNIGHT_ARMOR_SET, applied.get(e.getPlayer())));
		}

	}

	@EventHandler
	public void onUnApply(ArmorUnequipEvent e) {
		Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> b = super.getUnapplyMap(a, e, this);
		a = b.getVectorTwo();
		if (!applied.containsKey(e.getPlayer()))
			return;
		if (b.getVectorOne()) {
			applied.put(e.getPlayer(), applied.get(e.getPlayer()) - 1);
			Bukkit.getPluginManager().callEvent(new ArmorSetChangeEvent(ArmorChangeType.UNAPPLY, e.getPlayer(),
					ArmorSets.PHANTOM_KNIGHT_ARMOR_SET, applied.get(e.getPlayer())));
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setDamage(super.getDamage(e, a, this));
	}

	@EventHandler
	public void onDurabilityLoss(PlayerItemDamageEvent e) {
		super.calculateDurability(e, this, ChatColor.GRAY);
	}

	@Override
	public String getUAID() {
		return ChatColor.BLACK + "Phantom";
	}

	@Override
	public int getAmountApplied(Player p, ArmorSet a) {
		return applied.get(p);
	}

	@EventHandler
	public void onArmorChange(ArmorSetChangeEvent e) {
		Player p = e.getPlayer();
		if (e.getAmountApplied() == 4 && e.getSet().equals(ArmorSets.PHANTOM_KNIGHT_ARMOR_SET)) {
			p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You have applied the phantom knight armor set");
			p.sendMessage(super.plus + ChatColor.YELLOW + "" + ChatColor.BOLD + " 20% more attack damage");
			p.sendMessage(super.minus + ChatColor.YELLOW + "" + ChatColor.BOLD + " 10% incoming damage");
			p.sendMessage(super.plus + ChatColor.YELLOW + "" + ChatColor.BOLD + " Gears 4");
			BookManager.unloadEnchants(p);
			Book.addPotionEffect(PotionEffectType.SPEED, p, "4");
			BookManager.loadEnchants(p);
			p.sendMessage(super.plus + ChatColor.YELLOW + "" + ChatColor.BOLD +  " Phantom Knight Ability");
			PhantomArmorSet.fullSetApplied.add(p);
		} else if (e.getAmountApplied() < 4 && e.getSet().equals(ArmorSets.PHANTOM_KNIGHT_ARMOR_SET)
				&& PhantomArmorSet.fullSetApplied.contains(p)) {
			
			Book.removePotionEffect(PotionEffectType.SPEED, p, "4");
			BookManager.unloadEnchants(p);
			Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {

				@Override
				public void run() {
					BookManager.loadEnchants(p);
				}
				
			}, 1);
			
			PhantomArmorSet.fullSetApplied.remove(p);
		}

	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player))return; 
		if(PhantomArmorSet.fullSetApplied.contains((Player) e.getEntity())) {
			e.setDamage(e.getDamage() * 0.9);
				Player l = (Player) e.getEntity();
				if(l.getHealth() != 0) {
					double temp = 10/(l.getHealth());
					Random r = new Random();
					int rand = r.nextInt(100);
					if(temp >= rand) {
						l.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Phantom");
						Collection<Entity> nearby = l.getWorld().getNearbyEntities(l.getLocation(), 20, 20, 20);
						for(Entity e1: nearby) {
							if(e1 instanceof Player) {
								Player p = (Player) e1;
								p.hidePlayer(l);
								p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "" + l.getName() + " has moved to the phantom realm");
							}
						}
						Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {

							@Override
							public void run() {
								for(Entity e1: nearby) {
									if(e1 instanceof Player) {
										Player p = (Player) e1;
										p.showPlayer(l);
										p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "" + l.getName() + " has come back from the phantom realm");
									}
								}	
								
							}
							
						}, 2 * 20);
					}
				}
			}
		if(PhantomArmorSet.fullSetApplied.contains(e.getDamager())) {
			e.setDamage(e.getDamage() * 1.2);
		}
	}
}
