package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.enchants.energy.Energy;

import net.md_5.bungee.api.ChatColor;

public class RemoveHarvester implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(!(arg0.hasPermission("Enchanter.RemoveHarvester") || arg0.isOp())) {
			arg0.sendMessage(ChatColor.RED + "You do not have proper permission to use this command");
			return true;
		}
		if(arg3.length == 1) {
			try {
		Energy.getYml().getMap().remove(arg3[0]);
		Energy.getYml().save();
		arg0.sendMessage(ChatColor.BLUE + "You have removed the harvester with ID " + arg3[0]);
			}catch(Exception e) {
				arg0.sendMessage(ChatColor.RED + "That is not a valid ID");
			}
		}
		return false;
	}

}
