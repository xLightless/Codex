package org.codex.factions.commands;

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
import org.codex.factions.AuctionMain;

import net.md_5.bungee.api.ChatColor;

public class AuctionCommand implements CommandExecutor, Listener {

	//private static Map<Integer, Inventory> invs = new HashMap<>();

	String invAuctionHouse = "Auction House";
	String invAhSellName = ChatColor.WHITE + "Click a Inventory Item to Sell:";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player && (sender.isOp() || sender.hasPermission("ah.use"))) {
			
			Player player = (Player) sender;
			
			
			
			if (args.length == 0) {

				AuctionMain.createMenu(player);
				
			}else {
				switch (args[0]) {
				
					case "sell":
						
						if (args.length <= 1) {
							
							Inventory ahSell = null;
							AuctionMain.createMenu(ahSell, player);
							
						}
						
						if (args.length > 1) {
							sender.sendMessage(ChatColor.AQUA + "Type /ah sell, click on the Item you wish to List!");
							
						}
						break;
						
					case "help":
						sender.sendMessage(" ");
						sender.sendMessage(ChatColor.AQUA + "/ah " + ChatColor.WHITE + "- Primary Command to view Auctions");
						sender.sendMessage(ChatColor.AQUA + "/ah sell <Click Item> " + ChatColor.WHITE + "- Used to make a Listing");
						sender.sendMessage(" ");
						break;
						
					default:
						sender.sendMessage(ChatColor.RED + "Type " + ChatColor.UNDERLINE + "/ah help" + ChatColor.RESET + ChatColor.RED + " for commands!");
						
				}
				
			}

		} else if (!(sender instanceof Player)) {
			sender.sendMessage("You cannot use this command in console!");
		}else
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
		return false;
	}
	
	//Events below for Auction Inventory and anvil renaming
	
	@EventHandler
	public void onPlayerInventoryClick (InventoryClickEvent e) throws InterruptedException {
		
		Player player = (Player) e.getWhoClicked();
		
		if (e.getWhoClicked() == null) {
			return;
		}
		
		if(e.getInventory().getType() != InventoryType.PLAYER && e.getInventory().getType() != InventoryType.ENDER_CHEST && e.getInventory().getSize() >= 54) {
			e.setCancelled(true);
		}
		
		
		if (e.getInventory().getType() == InventoryType.ANVIL) {
			player.sendMessage(ChatColor.RED + "Please refrain from using XP in anvils!");
			player.sendMessage(ChatColor.RED + "Type " + ChatColor.UNDERLINE + ChatColor.ITALIC + "/anvil for a better experience.");
			player.closeInventory();
			e.setCancelled(true);
		}
		
		if (e.getInventory().getName().equals(invAhSellName)) {
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