package org.codex.enchants.armorsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.codex.enchants.books.ArmorEquipEvent;
import org.codex.enchants.books.ArmorType;
import org.codex.enchants.books.ArmorUnequipEvent;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;


public class NetherArmorSet extends ArmorSet implements Listener{

	
	protected static HashMap<Player, HashMap<ArmorType, Boolean>> a = new HashMap<>();
	private static HashMap<Player, Integer> applied = new HashMap<>();	
	
	public HashMap<Player, HashMap<ArmorType, Boolean>> getApplyMap() {
		return a;
	}


	public void setApplyMap(HashMap<Player, HashMap<ArmorType, Boolean>> a) {
		NetherArmorSet.a = a;
	}


	public NetherArmorSet() {
		this.setDefensePoints(30);
		this.setArmor_value(6);
		this.setDurabilityMultiplier(4);
		List<String> lore = new ArrayList<>();
		this.setLeather(true);
		this.setLeatherColor(Color.fromRGB(230, 38, 125));
		lore.add(this.getUAID());
		lore.add(ChatColor.RED + "Nether Armor: The Armor made from the Nether Gods");
		lore.add(ChatColor.RED + "30 Defense Points");
		this.setName(ChatColor.BOLD + "" + ChatColor.RED + "Netheranian " );
		this.setLore(lore);
		this.setmHelmet(Material.LEATHER_HELMET);
		this.setmChestplate(Material.LEATHER_CHESTPLATE);
		this.setmLeggings(Material.LEATHER_LEGGINGS);
		this.setmBoots(Material.LEATHER_BOOTS);
	}


	public ItemStack getItemStack(ArmorType t) {
		return super.createItemStack(t, this, ChatColor.RED);
	}
	
	@EventHandler
	public void onApply(ArmorEquipEvent e) {
		Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> b = super.getApplyMap(a, e, this);
		a = b.getVectorTwo();
		if(!b.getVectorOne())return;
		if(!applied.containsKey(e.getPlayer())) {
			applied.put(e.getPlayer(), 1);
			Bukkit.getPluginManager()
			.callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(), ArmorSets.NETHER_ARMOR_SET, applied.get(e.getPlayer())));
		}else {
		applied.put(e.getPlayer(), applied.get(e.getPlayer())+1);
		Bukkit.getPluginManager()
			.callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(), ArmorSets.NETHER_ARMOR_SET, applied.get(e.getPlayer())));
		}
		
	}
	
	@EventHandler 
	public void onUnApply(ArmorUnequipEvent e) {
		Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> b = super.getUnapplyMap(a, e, this);
		a = b.getVectorTwo();
		if(!applied.containsKey(e.getPlayer()))return;
		if(b.getVectorOne())applied.put(e.getPlayer(), applied.get(e.getPlayer())-1);
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
		return ChatColor.BLACK + "Netheranian";
	}


	@Override
	public int getAmountApplied(Player p, ArmorSet a) {
		return applied.get(p);
	}

	
	@EventHandler
	public void onArmorChange(ArmorSetChangeEvent e) {
		if(e.getAmountApplied() == 4 && e.getSet().equals(ArmorSets.NETHER_ARMOR_SET))e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have equiped the netheranian armor set");
	}
	

}
