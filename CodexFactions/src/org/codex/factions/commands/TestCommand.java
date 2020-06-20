package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.enchants.energy.Energy;


public class TestCommand implements CommandExecutor{

	@Override
	public boolean onCommand( CommandSender arg0,  Command arg1,  String arg2,
			 String[] arg3) {
		if(arg0.hasPermission("CustomEnchants.Test")) {
			arg0.sendMessage(Energy.getYml().getMap().toString());
			return true;
		}
		
		
		return false;
	}

}
