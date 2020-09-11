package org.codex.factions.auctions;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class AuctionEvents implements Listener {
	
	public static AuctionMain auctionMain;
	public AuctionEvents(AuctionMain main) {
		auctionMain = main;
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if (!(e.getClickedInventory().getTitle() == AuctionMain.TITLE)) return;
			ItemStack is = e.getCurrentItem();
			if (is == null ? true : is.getType().equals(Material.AIR))
			return;
			
			if (is.equals(AuctionItem.NEXT_PAGE.getItemStack())) {
				p.closeInventory();
				int i = auctionMain.getInventoryForPlayer(p) + 1;
				p.openInventory(auctionMain.getAuctionInventory(i));
				auctionMain.changeInventoryPlayer(i, p);
				e.setCancelled(true);
				return;
			}else if (is.equals(AuctionItem.PREVIOUS_PAGE.getItemStack())) {
				p.closeInventory();
				int i = auctionMain.getInventoryForPlayer(p) - 1 == 0 ? 0 : auctionMain.getInventoryForPlayer(p);
				p.openInventory(auctionMain.getAuctionInventory(i));
				auctionMain.changeInventoryPlayer(i, p);
				e.setCancelled(true);
				return;
			}
			
			e.setCancelled(true);
			
		}

	@EventHandler
	public void onPlayerDrag(InventoryDragEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (!(e.getInventory().getTitle() == AuctionMain.TITLE)) return;
		if ((e.getWhoClicked() instanceof Player) && (e.getInventory().getTitle() == AuctionMain.TITLE)) {
			e.setCancelled(true);
			}
		}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		if (e.getInventory().getTitle() == AuctionMain.TITLE) auctionMain.viewers.remove(e.getPlayer());
		
		}
	}