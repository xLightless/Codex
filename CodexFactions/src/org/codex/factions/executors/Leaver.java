package org.codex.factions.executors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Leaver implements Execute{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			try {
				FactionPlayer facp = FactionsMain.getPlayer(p.getUniqueId());
				FactionObject fac = facp.getFaction();
				if(facp.getRank().getLevel() == 4) {
					p.sendMessage(ChatColor.RED + "You must disband in order to leave this fac");
					return false;
				}
				fac.kickPlayer(facp);
				p.sendMessage(ChatColor.AQUA + "You have left " + fac.getFactionName() + ".");
				fac.broadcast(ChatColor.AQUA + p.getName() + " has left the facton.");
			} catch (Throwable e) {
				p.sendMessage(e.getMessage());
			}
		}
		return false;
	}

}
