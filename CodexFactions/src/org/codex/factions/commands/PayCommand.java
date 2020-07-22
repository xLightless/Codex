package org.codex.factions.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.economy.EconomyMain;

import net.md_5.bungee.api.ChatColor;

public class PayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player) {
			Player p = (Player) arg0;
			try {
			double d = Double.parseDouble(arg3[1]);
			
			if(arg3.length < 2) {
				p.sendMessage(ChatColor.RED + "Incorrect usuage.");
				return false;
			}
			@SuppressWarnings("deprecation")
			OfflinePlayer reciever = Bukkit.getOfflinePlayer(arg3[0]);
			if(reciever == null) {
				p.sendMessage(ChatColor.RED + "That player is invalid");
				return true;
			}
			if(EconomyMain.getMap().containsKey(p.getUniqueId().toString())) {
				if(EconomyMain.getPlayerMoney(p) >= d) {
				if(!EconomyMain.getMap().containsKey(reciever.getUniqueId().toString()))EconomyMain.setPlayerMoney(reciever, d);
				else EconomyMain.changePlayerMoney(reciever, d);
				EconomyMain.changePlayerMoney(p, -1 * d);
				p.sendMessage(ChatColor.GOLD + "You have payed " + arg3[0] + " " + d);
				if(reciever.isOnline())((Player) reciever).sendMessage(ChatColor.GOLD + "You have been payed " + d + " by " + p.getName());
				return true;
				}else p.sendMessage(ChatColor.RED + "You do not have enough money to pay this player");
			}else p.sendMessage(ChatColor.RED + "You do not have enough money to pay this player");
		}catch(NumberFormatException e) {
			p.sendMessage(ChatColor.RED + "Command invalid!");
		}
		}
		return false;
	}

}
