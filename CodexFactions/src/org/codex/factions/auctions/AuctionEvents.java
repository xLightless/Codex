package org.codex.factions.auctions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class AuctionEvents implements Listener {
	
	public AuctionMain auctionMain;
	
	public AuctionEvents(AuctionMain main) {
		auctionMain = main;
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if (!(e.getClickedInventory().getTitle() == AuctionMain.title)) return;
		if ((e.getWhoClicked() instanceof Player) && (e.getClickedInventory().getTitle() == AuctionMain.title)) {
			ItemStack is = e.getCurrentItem();
			if (is == null ? true : is.getType().equals(Material.AIR))
			return;
			
			if (is.equals(AuctionItem.NEXT_PAGE.getItemStack())) {
				p.closeInventory();
				int i = auctionMain.getInventoryForPlayer(p) + 1;
				p.openInventory(auctionMain.getAuctionInventory(i));
				auctionMain.changeInventoryPlayer(i, p);
				return;
			}else if (is.equals(AuctionItem.PREVIOUS_PAGE.getItemStack())) {
				p.closeInventory();
				int i = auctionMain.getInventoryForPlayer(p) - 1 == 0 ? 0 : auctionMain.getInventoryForPlayer(p);
				p.openInventory(auctionMain.getAuctionInventory(i));
				auctionMain.changeInventoryPlayer(i, p);
				return;
			}
			
			e.setCancelled(true);
			}
		}

	@EventHandler
	public void onPlayerDrag(InventoryDragEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (!(e.getInventory().getTitle() == AuctionMain.title)) return;
		if ((e.getWhoClicked() instanceof Player) && (e.getInventory().getTitle() == AuctionMain.title)) {
			e.setCancelled(true);
			}
		}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		if (e.getInventory().getTitle() == AuctionMain.title) AuctionMain.viewers.remove(e.getPlayer());
		
		}
	}