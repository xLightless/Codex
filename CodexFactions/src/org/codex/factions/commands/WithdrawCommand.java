package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.economy.EconomyMain;

import net.md_5.bungee.api.ChatColor;

public class WithdrawCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player && arg3.length >= 1) {
			Player p = (Player) arg0;
			try {
				
				double withdrawAmount = Double.parseDouble(arg3[0]);
				if(EconomyMain.getPlayerMoney(p) >= withdrawAmount) {
					EconomyMain.changePlayerMoney(p, -1 * withdrawAmount);
					p.getInventory().addItem(EconomyMain.createWithdrawNote(withdrawAmount, p));
				}else p.sendMessage(ChatColor.RED + "You are too poor. So sad ツ. Please beg lightless, he'll give if you ask enough.");
				
			}catch(NumberFormatException e) {
				arg0.sendMessage(ChatColor.RED + "That is an invalid amount");
			}
		}
		return false;
	}
	
	

}
