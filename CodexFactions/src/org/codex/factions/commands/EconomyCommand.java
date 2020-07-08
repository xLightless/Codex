package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.economy.executors.Give;

import net.md_5.bungee.api.ChatColor;

public class EconomyCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		switch(arg3[0]) {
		case "give":
			return new Give().onCommand(arg0, arg3);
		case "set":
			break;
		case "take":
			break;
		case "history":
			break;
		case "help":
			break;
		case "check":
			break;
		default :
			arg0.sendMessage(ChatColor.RED + "Command invalid. Please type /eco help");
			break;
		}
		return false;
	}

}
