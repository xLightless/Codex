package org.codex.factions.Executors;

import org.bukkit.command.CommandSender;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Info implements Execute{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(FactionsMain.Factions.containsKey(args[1])) { 
			FactionObject fac = FactionsMain.getFactionFromName(args[1]);
			sender.sendMessage(ChatColor.GREEN + "----------- " + fac.getFactionName() + " -----------");
			sender.sendMessage(ChatColor.GREEN + "Allies - " + fac.getAllies().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.BLUE + "Truces - " + fac.getTruces().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GRAY + "Alts - " + fac.getAlts().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.RED + "Enemies - " + fac.getEnemies().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GREEN + "Faction Power to Claimed Land - " + fac.getPower() + "/" + fac.getClaimedLand().size());
			sender.sendMessage(ChatColor.GREEN + "Bank Value - " + fac.getValue());
			sender.sendMessage(ChatColor.GREEN + "Online Players - " + fac.getOnlinePlayers().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GREEN + "Offline Players - " + fac.getOfflinePlayers().toString().replace("[", "").replace("]", ""));
			return true;
		}else {
			sender.sendMessage(ChatColor.RED + "That faction does not exist");
		}
		return false;
	}

}
