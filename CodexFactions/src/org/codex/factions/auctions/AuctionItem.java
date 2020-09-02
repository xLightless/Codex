package org.codex.factions.auctions;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.Book;

public enum AuctionItem {

	
	NEXT_PAGE(Material.PAPER, ChatColor.AQUA + "Next page", Book.of(ChatColor.AQUA + "Click this to go to the next page in the auction house."));
	
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
