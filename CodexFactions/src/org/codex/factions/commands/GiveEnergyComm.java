package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.codex.enchants.energy.Energy;

import net.md_5.bungee.api.ChatColor;

public class GiveEnergyComm implements CommandExecutor{

	Energy e = new Energy();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender.hasPermission("Enchanter.GiveEnergy") || sender.isOp())) {
			sender.sendMessage(ChatColor.RED + "You do not have proper permission to use this command");
			return true;
		}
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args == null)return false;
			if(args[0] !=  null) {
			if(!(p.hasPermission(new Permission("Player.Energy.Admin")) || p.isOp())) {
				p.sendMessage("You do not have permission to use this command :(");
				return false;
			}
			p.sendMessage(ChatColor.AQUA + "You have had " + args[0] + " energy added into your inventory");
			
			p.getInventory().addItem(e.getEnergyItemStack(Integer.parseInt(args[0])));
			}
		}
		return false;
	}

}
