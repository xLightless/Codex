package org.codex.factions.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;

public class AuctionCommand implements CommandExecutor, Listener {

	private static Map<Integer, Inventory> invs = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player && (sender.isOp() || sender.hasPermission("ah.use"))) {
			
			Player player = (Player) sender;
			
			if (args.length == 0) {

				Inventory i = invs.containsKey(1) ? invs.get(1) : Bukkit.createInventory(null, 54, "Auction House");
				player.openInventory(i);
				invs.put(1, i); // saves player modifications

			}else {
				switch (args[0]) {
				
					case "sell":
						
						if (args.length <= 1) {
							sender.sendMessage("Enter an amount to make a listing!");
						} 
						if (args.length == 2) {
							sender.sendMessage("Listing has been made!");
							
							
							
							// ah sell code here
							
							
							
						}
						if (args.length > 2) {
							sender.sendMessage("Enter a valid sell price!");
						}
						break;
					
					default:
						sender.sendMessage("Type /ah for menu or /ah help for commands!");
						
				}
				
			}

		} else if (!(sender instanceof Player)) {
			sender.sendMessage("You cannot use this command in console!");
		}else
			sender.sendMessage("You do not have permission to use this command!");
		return false;
	}
	
	//Events below for AH and anvil renaming
	
	@EventHandler
	public void onPlayerInventoryClick (InventoryClickEvent e) throws InterruptedException {
		
		Player player = (Player) e.getWhoClicked();
		
		if (e.getWhoClicked() == null) {
			return;
		}
		
		if(e.getInventory().getType() != InventoryType.PLAYER && e.getInventory().getType() != InventoryType.ENDER_CHEST && e.getInventory().getSize() >= 54) {
			Bukkit.broadcastMessage("click test");
			e.setCancelled(true);
		}
		
		
		if (e.getInventory().getType() == InventoryType.ANVIL) {
			player.sendMessage(ChatColor.RED + "Please refrain from using XP in anvils!");
			player.sendMessage(ChatColor.RED + "Type " + ChatColor.UNDERLINE + ChatColor.ITALIC + "/anvil for a better experience.");
			player.closeInventory();
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onPlayerInventoryDrag (InventoryDragEvent e2) {
		
		if (e2.getInventory().getType() != InventoryType.PLAYER && e2.getInventory().getSize() == 54) {
			e2.setCancelled(true);
		}
		
	}	
}