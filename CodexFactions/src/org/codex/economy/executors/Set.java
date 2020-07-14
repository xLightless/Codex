package org.codex.economy.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.economy.EconomyMain;
import org.codex.factions.executors.Execute;

import net.md_5.bungee.api.ChatColor;

public class Set implements Execute{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission("Economy.Give")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission for that");
			return false;
		}
		if (args.length == 2 && sender instanceof Player) {
			Player p = (Player) sender;
			if (!EconomyMain.getMap().containsKey(p.getUniqueId().toString())) {
				try {
					EconomyMain.setPlayerMoney(p, Double.parseDouble(args[1]));
					return true;
				} catch (NumberFormatException e) {
					p.sendMessage(ChatColor.RED + "Command Usuage improper. Please type /eco help");
					return false;
				}
			} else {
				try {
					EconomyMain.setPlayerMoney(p, Double.parseDouble(args[1]));
					p.sendMessage(ChatColor.GOLD + "Your balance has been set to " + EconomyMain.getPlayerMoney(p));
					return true;
				} catch (NumberFormatException e) {
					p.sendMessage(ChatColor.RED + "Command Usuage improper. Please type /eco help");
					return false;
				}
			}
		} else if (args.length >= 3) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				sender.sendMessage(ChatColor.RED + "That player is invalid");
				return false;
			}
			double d;
			try {
				d = Double.parseDouble(args[2]);
			}catch(NumberFormatException e) {
				p.sendMessage(ChatColor.RED + "Command Usuage improper. Please type /eco help");
				return false;
			}
			EconomyMain.setPlayerMoney(p, d);
			p.sendMessage(ChatColor.GOLD + "Your balance has been set to " + EconomyMain.getPlayerMoney(p));
			sender.sendMessage(ChatColor.GOLD + p.getName() + "'s balance has been set to " + EconomyMain.getPlayerMoney(p));
			return true;
		}

		return false;
	}

}
