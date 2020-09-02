package org.codex.factions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class AuctionMain {
	
	public static HashMap<Integer, Inventory> inv = new HashMap<Integer, Inventory>(); //Ah Slot/Inventory
	public static HashMap<Player, ItemStack> pdata = new HashMap<Player, ItemStack>(); //
	public static String title = ChatColor.AQUA + "Auction House";
	public static ItemStack nextPage;
	public static ItemStack prevPage;
	
	public Inventory getAuctionInventory(int i) {
		
		Inventory ahInv = inv.containsKey(i) ? inv.get(i) : this.createInventory();
		inv.put(i, ahInv);
		return ahInv;
	}
	
	public Inventory createInventory() {
		
		Inventory inv = Bukkit.createInventory(null, 54, title);
		return inv;	
	}

	public void addItem (Player p, ItemStack is) {
		
		Inventory ahInv = this.getFirstEmptyInventory(0);
		ahInv.addItem(is);
	}
	
	public Inventory getFirstEmptyInventory(int i) {
		
		return this.getAuctionInventory(i).firstEmpty() == -1 ? getFirstEmptyInventory(i + 1) : this.getAuctionInventory(i);
		
	}		
}
