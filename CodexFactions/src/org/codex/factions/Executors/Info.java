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
			sender.sendMessage(ChatColor.GRAY + "Alts - " + fac.getAltNames().toString().replace("[", "").replace("]", ""));
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
		Player p = (Player) sender;
		if(FactionsMain.Players.containsKey(p.getUniqueId())){
			FactionObject fac;
			try {
				fac = FactionsMain.getPlayerFaction(p.getUniqueId());
			} catch (Throwable e) {
				p.sendMessage(e.getMessage());
				return false;
			}
			p.sendMessage(ChatColor.GREEN + "----------- " + fac.getFactionName() + " -----------");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "Allies - " + fac.getAllies().toString().replace("[", "").replace("]", ""));
			p.sendMessage(ChatColor.BLUE + "Truces - " + fac.getTruces().toString().replace("[", "").replace("]", ""));
			p.sendMessage(ChatColor.GRAY + "Alts - " + fac.getAltNames().toString().replace("[", "").replace("]", ""));
			p.sendMessage(ChatColor.RED + "Enemies - " + fac.getEnemies().toString().replace("[", "").replace("]", ""));
			p.sendMessage(ChatColor.GREEN + "Faction Power to Claimed Land - " + fac.getPower() + "/" + fac.getClaimedLand().size());
			p.sendMessage(ChatColor.GREEN + "Bank Value - " + fac.getValue());
			p.sendMessage(ChatColor.GREEN + "Online Players - " + fac.getOnlinePlayersName().toString().replace("[", "").replace("]", ""));
			p.sendMessage(ChatColor.GREEN + "Offline Players - " + fac.getOfflinePlayersName().toString().replace("[", "").replace("]", ""));
			return true;
		}else {
			p.sendMessage(ChatColor.RED + "You are not inside of a faction");
		}
		
		
		return false;
	}
		
	
	}

}
