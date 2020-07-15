package org.codex.auctionhouse;

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
import org.bukkit.inventory.Inventory;

public class AuctionCommand implements CommandExecutor, Listener {
	
	private static Map<Integer, Inventory> invs = new HashMap<>();
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player && (sender.isOp() || sender.hasPermission("ah.use"))) {
			Player player = (Player) sender;
			
			if (args.length == 0) {
				
				Inventory i = invs.containsKey(1) ? invs.get(1) : Bukkit.createInventory(null, 54, "Auction House");
				player.openInventory(i);
				invs.put(1, i); //saves player modifications
	
			}
			
			if (args[0].equalsIgnoreCase("sell")) {
				
				sender.sendMessage("You have executed /ah sell! KINGMO ADD ECO!");
				
				
				// add gui and sell confirm or other
				
			}else
				
				sender.sendMessage("Unknown arguments, use /ah [sell]");
			
		}else
			sender.sendMessage("You cannot use this command in console!");
		
		return false;

	}
	
	
	
	
	//Events below for AH
	
	@EventHandler
	public void onPlayerClick (InventoryClickEvent e) {
		
		if(e.getInventory().getTitle().equals("Auction House") && e.getClickedInventory().getTitle().equals("Auction House")) {
			e.setCancelled(true);
			
		}
			
	}
	
	@EventHandler
	public void onInventoryDrag (InventoryDragEvent e) {	
		
		if (e.getInventory().getTitle().equals("Auction House")) {
			e.setCancelled(true);

		}

		}
		
	}
