package org.codex.factions.auctions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class AuctionMainEvents implements Listener {
	
	public AuctionMain auctionMain;
	public Set<Player> set = new HashSet<>();
	
	public AuctionMainEvents(AuctionMain main) {
		auctionMain = main;
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains(AuctionMain.TITLE))e.setCancelled(true);;
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getWhoClicked() == null) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
		Player p = (Player) e.getWhoClicked();
		if (!(e.getClickedInventory().getTitle().contains(AuctionMain.TITLE))) return;
			ItemStack is = e.getCurrentItem();
			if (is == null ? true : is.getType().equals(Material.AIR))
			return;
			
			if (is.equals(AuctionItem.NEXT_PAGE.getItemStack())) {
				set.add(p);
				p.closeInventory();
				int i = auctionMain.getInventoryForPlayer(p) + 1;
				p.openInventory(auctionMain.getAuctionInventory(i));
				auctionMain.changeInventoryPlayer(i, p);
				p.sendMessage(ChatColor.GOLD + "You have moved to auction house " + i);
			}else if (is.equals(AuctionItem.PREVIOUS_PAGE.getItemStack())) {
				set.add(p);
				p.closeInventory();
				int i = auctionMain.getInventoryForPlayer(p) - 1 <= 0 ? 0 : auctionMain.getInventoryForPlayer(p) - 1;
				p.openInventory(auctionMain.getAuctionInventory(i));
				auctionMain.changeInventoryPlayer(i, p);
				p.sendMessage(ChatColor.GOLD + "You have moved to auction house " + i);
			}
			
			else if (is.equals(AuctionItem.PLAYER_VAULTS.getItemStack())) {
				set.add(p);
				p.closeInventory();
				
			}
			
			e.setCancelled(true);
			
		}

	@EventHandler
	public void onPlayerDrag(InventoryDragEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getWhoClicked() == null) return;
		if (!(e.getInventory().getTitle().contains(AuctionMain.TITLE))) return;
		if ((e.getWhoClicked() instanceof Player) && (e.getInventory().getTitle().contains(AuctionMain.TITLE))) {
			e.setCancelled(true);
			}
		}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
	
		if (e.getInventory().getTitle().contains(AuctionMain.TITLE)) {
			if(!set.contains(e.getPlayer()))
				auctionMain.viewers.remove(e.getPlayer());
			else
				set.remove(e.getPlayer());
		
			
		}
		
		}
	}