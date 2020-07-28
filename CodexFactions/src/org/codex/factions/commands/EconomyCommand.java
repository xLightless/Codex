package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.economy.executors.Give;
import org.codex.economy.executors.Set;
import org.codex.economy.executors.Take;

import net.md_5.bungee.api.ChatColor;

public class EconomyCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(arg3.length >= 1) {
		switch(arg3[0]) {
		case "give":
			return new Give().onCommand(arg0, arg3);
		case "set":
			return new Set().onCommand(arg0, arg3);
		case "take":
			return new Take().onCommand(arg0, arg3);
		case "history":
			break;
		case "help":
			
			arg0.sendMessage(" ");
			arg0.sendMessage(ChatColor.AQUA + "/eco " + ChatColor.WHITE + "- Primary Command for Economy");
			arg0.sendMessage(ChatColor.AQUA + "/eco <Give/Set/Take> " + ChatColor.WHITE + "- Make changes to a Players Balance");
			arg0.sendMessage(ChatColor.AQUA + "/eco <History> " + ChatColor.WHITE + "- View Economy History of a Player");
			arg0.sendMessage(ChatColor.AQUA + "/withdraw <Amount> " + ChatColor.WHITE + "- Withdraw your Balance as an Item");
			arg0.sendMessage(" ");
			
			break;
		case "check":
			break;
		default :
			arg0.sendMessage(ChatColor.RED + "Command invalid. Please type /eco help");
			break;
		}
		return false;
		}else arg0.sendMessage(ChatColor.RED + "Command invalid. Please type /eco help");
		return false;
	}

}
