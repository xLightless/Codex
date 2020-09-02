package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.AuctionMain;

import net.md_5.bungee.api.ChatColor;

public class AuctionCommand implements CommandExecutor {

	public static AuctionMain am;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			if (args.length == 0) {
				am.getClass();
			}
			
			if (args.length != 0) {
				
				switch (args[0].toLowerCase()) {
					case "help":
						default:
							sender.sendMessage(ChatColor.RED + "Invalid Usage. Type /ah help.");
				}
				
			}
			
		}
		
		return false;
	}

}
