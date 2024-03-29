package org.codex.factions.auctions;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class AuctionMain {

	public static HashMap<Integer, Inventory> inv = new HashMap<>(); // Ah Slot/Inventory
	public static HashMap<ItemStack, Player> pdata = new HashMap<>(); //
	public static final String TITLE = ChatColor.AQUA + "Auction House";
	public HashMap<Player, Integer> viewers = new HashMap<>();
  
	public AuctionMain(HashMap<Player, Integer> viewers) {
		this.viewers = viewers;
	}
	
	
	public HashMap<Player, Integer> getViewers() {
		return viewers;
	}

	public AuctionMain() {
	}
	
	public Inventory getAuctionInventory(int i) {
		Inventory ahInv = inv.containsKey(i) ? inv.get(i) : this.createInventory(i);
		inv.put(i, ahInv);
		return ahInv;
	}

	public Inventory createInventory(int i) {

		Inventory inv = Bukkit.createInventory(null, 54, TITLE + " selection : " + i);
		inv.setItem(45, AuctionItem.COLLECTION.getItemStack());
		inv.setItem(50, AuctionItem.NEXT_PAGE.getItemStack());
		inv.setItem(48, AuctionItem.PREVIOUS_PAGE.getItemStack()); //48
		inv.setItem(49, AuctionItem.REFRESH_AUCTION.getItemStack());
		inv.setItem(53, AuctionItem.PLAYER_VAULTS.getItemStack());

		return inv;
	}

	public void addItem(Player p, ItemStack is) {

		Inventory ahInv = this.getFirstEmptyInventory(0);
		int slot = this.getFirstEmptyInventorySlot(0);
		ahInv.setItem(ahInv.firstEmpty(), is);
		inv.put(slot, ahInv);
	}

	public Inventory getFirstEmptyInventory(int i) {

		int empty = this.getAuctionInventory(i).firstEmpty();
		
		return empty == -1 || empty >= 45 ? getFirstEmptyInventory(i + 1) : this.getAuctionInventory(i);

	}
	
	public int getFirstEmptyInventorySlot(int i) {

		int empty = this.getAuctionInventory(i).firstEmpty();
		
		return empty == -1 || empty >= 45 ? this.getFirstEmptyInventorySlot(i + 1) : i;

	}

	public void openInventory(Player p) {
		viewers.put(p, 0);
		p.closeInventory();
		p.openInventory(this.getAuctionInventory(0));
	}

	public int getInventoryForPlayer(Player p) {
		return getViewers().get(p);
	}

	public void changeInventoryPlayer(int i, Player p) {
		viewers.put(p, i); 
	}
	
	public void addInventoryPlayer(int i, Player p) {
		viewers.put(p, viewers.containsKey(p) ? viewers.get(p) + i : i); 
	}
}
