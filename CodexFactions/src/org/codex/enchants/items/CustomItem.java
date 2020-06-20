package org.codex.enchants.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.BookManager;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public abstract class CustomItem<E extends Event> implements Listener{

	private final Material m;
	private int matID = 1;
	private int energyCost = 0;
	private final CustomItemType type;
	
	public CustomItem(Material m, CustomItemType t) {
		this.m = m;
		this.type = t;
	}
	
	public CustomItem(Material m, CustomItemType t, int matID) {
		this.m = m;
		this.setMaterialID(matID);
		this.type = t;
	}
	
	
	public abstract ItemStack createItemStack();
	public abstract boolean isItem(ItemStack is);
	@EventHandler
	public abstract void onActivation(E e);
	
	public int getMaterialID() {
		return matID;
	}

	public void setMaterialID(int matID) {
		this.matID = matID;
	}

	public Material getMaterial() {
		return m;
	}

	public int getEnergyCost() {
		return energyCost;
	}

	public void setEnergyCost(int energyCost) {
		this.energyCost = energyCost;
	}

	public ItemStack getItemStack() {
		return this.createItemStack();
	}


	public CustomItemType getType() {
		return type;
	}

	public static boolean isCustomItem(ItemStack is) {
		for(CustomItemType t : CustomItemType.values()) {
			CustomItem<?> i = t.getItem();
			if(i.isItem(is))return true;
		}
		return false;
	}
	
	public Vector2D<Boolean, ItemStack> use(ItemStack is) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
 		int slot;
 		try {
 		slot = BookManager.getEnergySlot(lore);
 		}catch(NullPointerException e) {
 			return new Vector2D<>(false, is);
 		}
		int energy = Integer.parseInt(lore.get(slot).split(" : ")[1]);		
 		energy -= this.getEnergyCost();
	 	if(energy <= 0)return new Vector2D<>(false, is);
	 	lore.set(slot, ChatColor.DARK_AQUA + "Energy : " + energy);
 		im.setLore(lore);
 		is.setItemMeta(im);
		return new Vector2D<>(true, is);
	}
	
	public void onCustomItemPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		for(CustomItemType t: CustomItemType.values()) {
		if(t.getItem().isItem(p.getItemInHand())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You cannot place that");
		}
			
		}
	}

	
	
	
	
	
}
