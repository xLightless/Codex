package org.codex.factions.auctions;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.Book;

public enum AuctionItem {

	
	NEXT_PAGE(Material.PAPER, ChatColor.AQUA + "Next Page", Book.of(ChatColor.GRAY + "Click this to go to the next page in the auction house.")),
	
	PREVIOUS_PAGE(Material.PAPER, ChatColor.AQUA + "Previous Page", Book.of(ChatColor.GRAY + "Click this to go to the previous page in the auction house.")), 
	
	REFRESH_AUCTION(Material.SNOW_BALL, ChatColor.GOLD + "Refresh", Book.of(ChatColor.GRAY + "Click to refresh Auction House.")),
	
	COLLECTION(Material.ENDER_CHEST, ChatColor.AQUA + "Collection Bin", Book.of(ChatColor.GRAY + "Items not Sold will be found here.", ChatColor.GRAY + "" + ChatColor.ITALIC + "Items NOT COLLECTED will be removed.")),
	
	PLAYER_VAULTS(Material.CHEST, ChatColor.AQUA + "Player Vaults", Book.of(ChatColor.GRAY + "Open Your Player Vaults from here..."));
	
	private ItemStack is;
	
	AuctionItem(Material m, String displayName, List<String> lore){
		ItemStack is = new ItemStack(m);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(lore);
		is.setItemMeta(im);
		this.is = is;
	}
	
	public ItemStack getItemStack() {
		return is;
	}
	
	
}
