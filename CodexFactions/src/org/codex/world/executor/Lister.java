package org.codex.world.executor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class Lister implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		for (World w : Bukkit.getWorlds())list.add(w.getName());
			sender.sendMessage(ChatColor.GREEN + list.toString().replace("[", "").replace("]", ""));
		return true;
	}

}
