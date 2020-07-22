package org.codex.factions.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.economy.EconomyMain;

import net.md_5.bungee.api.ChatColor;

public class BalanceCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player && arg3.length == 0) {
			Player p = (Player) arg0;
			p.sendMessage(ChatColor.GOLD + "Your balance is " + EconomyMain.getPlayerMoney(p));
			return true;
		}else if (arg3.length == 1) {
			try {
				@SuppressWarnings("deprecation")
				OfflinePlayer p = Bukkit.getOfflinePlayer(arg3[0]);
				arg0.sendMessage(ChatColor.GOLD  + "" + p.getName() + "'s balance is " + EconomyMain.getPlayerMoney(p));
			}catch(NullPointerException e) {
				arg0.sendMessage(ChatColor.RED + "That player does not exist. Reminder - this is CaSe SeNsEtIvE");
			}
		}
		return false;
	}

}
