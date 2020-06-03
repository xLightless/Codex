package org.codex.factions.Executors;

import org.bukkit.command.CommandSender;

public interface Execute {
	//ignore args[0]
	public void onCommand(CommandSender sender, String[] args);
}
