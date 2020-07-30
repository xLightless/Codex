package org.codex.economy.executors;

import org.bukkit.command.CommandSender;
import org.codex.factions.executors.Execute;

import net.md_5.bungee.api.ChatColor;

public class Help implements Execute {

	@Override
	public boolean onCommand(CommandSender arg0, String[] args) {
		
		arg0.sendMessage(" ");
		arg0.sendMessage(ChatColor.AQUA + "/eco " + ChatColor.WHITE + "- Primary Command for Economy");
		arg0.sendMessage(ChatColor.AQUA + "/eco <Give|Set|Take> " + ChatColor.WHITE + "- Make changes to a Players Balance");
		arg0.sendMessage(ChatColor.AQUA + "/eco <History> " + ChatColor.WHITE + "- View Economy History of a Player");
		arg0.sendMessage(ChatColor.AQUA + "/withdraw <Amount> " + ChatColor.WHITE + "- Withdraw your Balance as an Item");
		arg0.sendMessage(" ");
		
		return true;
	}

}
