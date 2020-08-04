package org.codex.factions.executors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.Rank;

import net.md_5.bungee.api.ChatColor;

public class Renamer implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			FactionPlayer facp;
			try {
				facp = FactionsMain.getPlayer(p.getUniqueId());
			}catch(Throwable t) {
				p.sendMessage(ChatColor.RED + "you are not inside of a faction.");
				return false;
			}
			FactionObject fac = facp.getFaction();
			if(facp.getRank().getLevel() < Rank.COLEADER.getLevel()) {
				p.sendMessage(ChatColor.RED + "You are not a high enough rank to use this command");
				return true;
			}
			else if(args.length != 2) {
				p.sendMessage(ChatColor.RED + "Command Invalid.");
				return false;
			}
			String newName = args[1];
			FactionsMain.Factions.remove(fac.getFactionName().toUpperCase());
			FactionsMain.Factions.put(newName.toUpperCase(), fac);
			fac.setFactionName(newName);
			fac.broadcast(ChatColor.AQUA + p.getDisplayName() + " has changed the faction name to " + newName);
			return true;
		}
		return false;
	}

}
