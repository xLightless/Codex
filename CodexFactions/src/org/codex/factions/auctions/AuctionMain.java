package org.codex.factions.auctions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class AuctionMain {

	public static HashMap<Integer, Inventory> inv = new HashMap<>(); // Ah Slot/Inventory
	public static HashMap<ItemStack, Player> pdata = new HashMap<>(); //
	public static String title = ChatColor.AQUA + "Auction House";
	public static ItemStack nextPage;
	public static ItemStack prevPage;
	public static Map<Player, Integer> viewers = new HashMap<>();

	public Inventory getAuctionInventory(int i) {

		Inventory ahInv = inv.containsKey(i) ? inv.get(i) : this.createInventory();
		inv.put(i, ahInv);
		return ahInv;
	}

	public Inventory createInventory() {

		Inventory inv = Bukkit.createInventory(null, 54, title);
		inv.setItem(45, AuctionItem.COLLECTION.getItemStack());
		inv.setItem(50, AuctionItem.NEXT_PAGE.getItemStack());
		inv.setItem(48, AuctionItem.PREVIOUS_PAGE.getItemStack());
		inv.setItem(49, AuctionItem.REFRESH_AUCTION.getItemStack());
		inv.setItem(53, AuctionItem.PLAYER_VAULTS.getItemStack());

		return inv;
	}

	public void addItem(Player p, ItemStack is) {

		Inventory ahInv = this.getFirstEmptyInventory(0);
		ahInv.addItem(is);
		inv.put(this.getFirstEmptyInventorySlot(0), ahInv);
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
		p.openInventory(this.getAuctionInventory(0));
	}

	public int getInventoryForPlayer(Player p) {
		return viewers.get(p);
	}

	public void changeInventoryPlayer(int i, Player p) {
		viewers.put(p, viewers.containsKey(p) ? viewers.get(p) + i : i); 
	}
	
}
