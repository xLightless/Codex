package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.enchants.energy.Energy;
import org.codex.enchants.energy.EnergyHarvesterType;

import net.md_5.bungee.api.ChatColor;



public class GiveHarvester implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(!(arg0.hasPermission("Enchanter.GiveHarvester") || arg0.isOp())) {
			arg0.sendMessage(ChatColor.RED + "You do not have proper permission to use this command");
			return true;
		}
		if(arg0 instanceof Player) {
			EnergyHarvesterType type = EnergyHarvesterType.value(arg3[0].toUpperCase());
			Energy.giveEnergyHarvester((Player) arg0, type);
			arg0.sendMessage(ChatColor.AQUA + "You have been given a " + type.getDisplayName());
		}
		return false;
	}

}
