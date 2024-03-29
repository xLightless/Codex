package org.codex.economy.executors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.economy.EconomyMain;
import org.codex.factions.executors.Execute;

import net.md_5.bungee.api.ChatColor;

public class Give implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission("Economy.Give")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this Command.");
			return false; 
		}
		if (args.length == 2 && sender instanceof OfflinePlayer) {
			OfflinePlayer p = (OfflinePlayer) sender;
			if (!EconomyMain.getMap().containsKey(p.getUniqueId().toString())) {
				try {
					EconomyMain.setPlayerMoney(p, Double.parseDouble(args[1]));
					return true;
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "Command usage improper. Please type /eco help.");
					return false;
				}
			} else {
				try {
					EconomyMain.changePlayerMoney(p, Double.parseDouble(args[1]));
					sender.sendMessage(ChatColor.GOLD + "Your balance has been changed to " + EconomyMain.getPlayerMoney(p));
					return true;
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "Command usage improper. Please type /eco help.");
					return false;
				}
			}
		} else if (args.length >= 3) {
			@SuppressWarnings("deprecation")
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
			if (p == null) {
				sender.sendMessage(ChatColor.RED + "That Player is invalid.");
				return false;
			}
			double d;
			try {
				d = Double.parseDouble(args[2]);
			}catch(NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Command usage improper. Please type /eco help.");
				return false;
			}
			if(!EconomyMain.getMap().containsKey(p.getUniqueId().toString()))EconomyMain.setPlayerMoney(p, d);
			else EconomyMain.changePlayerMoney(p, d);
			if(p.isOnline())((Player)p).sendMessage(ChatColor.GOLD + "Your Balance has been changed to " + EconomyMain.getPlayerMoney(p));
			sender.sendMessage(ChatColor.GOLD + p.getName() + "'s Balance has been changed to " + EconomyMain.getPlayerMoney(p));
			return true;
		}

		return false;
	}

}
