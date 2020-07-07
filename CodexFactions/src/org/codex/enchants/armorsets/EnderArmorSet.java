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

public class EnderArmorSet extends ArmorSet implements Listener{
	
	protected static HashMap<Player, HashMap<ArmorType, Boolean>> a = new HashMap<>();
	private static HashMap<Player, Integer> applied = new HashMap<>();	
	 
	public EnderArmorSet() {
		this.setDefensePoints(50);
		this.setArmor_value(6);
		this.setDurabilityMultiplier(10);
		List<String> lore = new ArrayList<>();
		this.setLeather(true);
		this.setLeatherColor(Color.fromRGB(220, 77, 242));
		lore.add(this.getUAID());
		lore.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "Ender Armor: The Armor made from the Ender Gods");
		lore.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "50 Defense Points");
		this.setName(ChatColor.RESET + "" + ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Ender " );
		this.setLore(lore);
		this.setmHelmet(Material.LEATHER_HELMET);
		this.setmChestplate(Material.LEATHER_CHESTPLATE);
		this.setmLeggings(Material.LEATHER_LEGGINGS);
		this.setmBoots(Material.LEATHER_BOOTS);
	}

	
	@EventHandler
	public void onApply(ArmorEquipEvent e) {
		Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> b = super.getApplyMap(a, e, this);
		a = b.getVectorTwo();

		if(!b.getVectorOne())return;
			if(!applied.containsKey(e.getPlayer())) {
				applied.put(e.getPlayer(), 1);
				Bukkit.getPluginManager()
				.callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(), ArmorSets.ENDER_ARMOR_SET, applied.get(e.getPlayer())));
			}else {
			applied.put(e.getPlayer(), applied.get(e.getPlayer())+1);
			Bukkit.getPluginManager()
				.callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(), ArmorSets.ENDER_ARMOR_SET, applied.get(e.getPlayer())));
			}
		
		
	}
	
	@Override
	public String getUAID() {
		return ChatColor.RESET + "" + ChatColor.BLACK + "Ender";
	}

	@Override
	public ItemStack getItemStack(ArmorType t) {
		return super.createItemStack(t, this, ChatColor.DARK_PURPLE);
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
		e.setDamage(this.getDamage(e, a, this));
	}
	
	@EventHandler
	public void onDurabilityLoss(PlayerItemDamageEvent e) {
		super.calculateDurability(e, this, ChatColor.GRAY);
	}


	@Override
	public HashMap<Player, HashMap<ArmorType, Boolean>> getApplyMap() {
		return a;
	} 


	@Override
	public void setApplyMap(HashMap<Player, HashMap<ArmorType, Boolean>> m) {
		EnderArmorSet.a = m;
		
	}


	@Override
	public int getAmountApplied(Player p, ArmorSet a) {
		return applied.get(p);
	}


	@EventHandler
	public void onArmorChange(ArmorSetChangeEvent e) {
		if(e.getAmountApplied() == 4 && e.getSet().equals(ArmorSets.ENDER_ARMOR_SET))e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "The ender set has been activated");
		
	}
	
	

}
