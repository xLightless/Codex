package org.codex.world.executor;

import org.bukkit.command.CommandSender;

public interface Executor {

	public boolean onCommand(CommandSender sender, String[] args);
	
}
