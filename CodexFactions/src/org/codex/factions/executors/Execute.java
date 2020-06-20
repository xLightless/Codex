package org.codex.factions.executors;


import org.bukkit.command.CommandSender;



public interface Execute {
	
	/**
	* ignore args[0]
	*/
	public boolean onCommand(CommandSender sender, String[] args);
}
