package org.codex.factions.Executors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Info implements Execute{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length == 2) {
		if(FactionsMain.Factions.containsKey(args[1])) { 
			FactionObject fac = FactionsMain.getFactionFromName(args[1]);
			sender.sendMessage(ChatColor.GREEN + "------------------ " + fac.getFactionName() + " ------------------");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "Allies - " + fac.getAllies().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.BLUE + "Truces - " + fac.getTruces().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GRAY + "Alts - " + fac.getAlts().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.RED + "Enemies - " + fac.getEnemies().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GREEN + "Faction Power to Claimed Land - " + fac.getPower() + "/" + fac.getClaimedLand().size());
			sender.sendMessage(ChatColor.GREEN + "Bank Value - " + fac.getValue());
			sender.sendMessage(ChatColor.GREEN + "Online Players - " + fac.getOnlinePlayersName().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GREEN + "Offline Players - " + fac.getOfflinePlayersName().toString().replace("[", "").replace("]", ""));
			return true;
		}else {
			sender.sendMessage(ChatColor.RED + "That faction does not exist");
		}
		return false;
	}else {
		if(FactionsMain.Factions.containsKey(((Player) sender).getUniqueId().toString())){
			FactionObject fac;
			try {
				fac = FactionsMain.getPlayerFaction(((Player) sender).getUniqueId());
			} catch (Throwable e) {
				sender.sendMessage(e.getMessage());
				return false;
			}
			sender.sendMessage(ChatColor.GREEN + "----------- " + fac.getFactionName() + " -----------");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "Allies - " + fac.getAllies().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.BLUE + "Truces - " + fac.getTruces().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GRAY + "Alts - " + fac.getAlts().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.RED + "Enemies - " + fac.getEnemies().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GREEN + "Faction Power to Claimed Land - " + fac.getPower() + "/" + fac.getClaimedLand().size());
			sender.sendMessage(ChatColor.GREEN + "Bank Value - " + fac.getValue());
			sender.sendMessage(ChatColor.GREEN + "Online Players - " + fac.getOnlinePlayersName().toString().replace("[", "").replace("]", ""));
			sender.sendMessage(ChatColor.GREEN + "Offline Players - " + fac.getOfflinePlayersName().toString().replace("[", "").replace("]", ""));
			return true;
		}
		
		
		return false;
	}
		
	
	}

}
