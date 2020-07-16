package org.codex.factions.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

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
	
	//Events below for AH
	
	
	@EventHandler
	public void onPlayerInventoryClick (InventoryClickEvent e) {
		
		if(e.getInventory().getType() != InventoryType.PLAYER && e.getInventory().getSize() == 54) {
			Bukkit.broadcastMessage("clicked");
			e.setCancelled(true);
		}
			
	}
	@EventHandler
	public void onPlayerInventoryDrag (InventoryDragEvent e2) {
		
		if (e2.getInventory().getType() != InventoryType.PLAYER && e2.getInventory().getSize() == 54) {
			Bukkit.broadcastMessage("dragged");
			e2.setCancelled(true);
		}		
	}

	}