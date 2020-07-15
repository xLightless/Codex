package org.codex.economy.executors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.economy.EconomyMain;
import org.codex.factions.executors.Execute;

import net.md_5.bungee.api.ChatColor;

public class Take implements Execute{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission("Economy.Give")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission for that");
			return false;
		}
		if (args.length == 2 && sender instanceof OfflinePlayer) {
			OfflinePlayer p = (OfflinePlayer) sender;
			if (!EconomyMain.getMap().containsKey(p.getUniqueId().toString())) {
				try {
					EconomyMain.setPlayerMoney(p, -1 * Double.parseDouble(args[1]));
					sender.sendMessage(ChatColor.RED + "Your balance has been set to " + EconomyMain.getPlayerMoney(p));
					return true;
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "Command Usuage improper. Please type /eco help");
					return false;
				}
			} else {
				try {
					EconomyMain.changePlayerMoney(p, -1 * Double.parseDouble(args[1]));
					sender.sendMessage(ChatColor.RED + "Your balance has been changed to " + EconomyMain.getPlayerMoney(p));
					return true;
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "Command Usuage improper. Please type /eco help");
					return false;
				}
			}
		} else if (args.length >= 3) {
			@SuppressWarnings("deprecation")
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
			if (p == null) {
				sender.sendMessage(ChatColor.RED + "That player is invalid");
				return false;
			}
			double d;
			try {
				d = -1 * Double.parseDouble(args[2]);
			}catch(NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Command Usuage improper. Please type /eco help");
				return false;
			}
			if(!EconomyMain.getMap().containsKey(p.getUniqueId().toString()))EconomyMain.setPlayerMoney(p, d);
			else EconomyMain.changePlayerMoney(p, d);
			((Player) p).sendMessage(ChatColor.GOLD + "Your balance has been changed to " + EconomyMain.getPlayerMoney(p));
			sender.sendMessage(ChatColor.GOLD + p.getName() + "'s balance has been changed to " + EconomyMain.getPlayerMoney(p));
			return true;
		}

		return false;
	}

}
