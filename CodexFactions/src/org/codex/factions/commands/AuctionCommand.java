package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.auctions.AuctionMain;
import org.codex.factions.executors.auction.Sell;

import net.md_5.bungee.api.ChatColor;

public class AuctionCommand implements CommandExecutor {

	public AuctionMain am;
	
	public AuctionCommand(AuctionMain am) {
		this.am = am;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player p = (Player) sender;

			
			if (args.length == 0) {
				am.openInventory(p);
			}
			
			if (args.length != 0) {
				
				switch (args[0].toLowerCase()) {
					case "help":
						p.sendMessage("not yet");
						break;
					case "sell":
						new Sell().onCommand(sender, args);
						break;
						default:
							
							sender.sendMessage(ChatColor.RED + "Invalid Usage. Type /ah help.");
				}
				
			}
			
		}
		
		return false;
	}

}
