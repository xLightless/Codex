package org.codex.world.executor;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class Lister implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.GREEN + Bukkit.getWorlds().toString().replace("[", "").replace("]", ""));
		return true;
	}

}
