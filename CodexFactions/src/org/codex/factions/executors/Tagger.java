package org.codex.factions.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Tagger implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			try {
				
				if(args.length < 2) {
					p.sendMessage(ChatColor.RED + "Not enough arguments.");
					return false;
				}
				
				Player p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) {
					p.sendMessage(ChatColor.RED + "That player is not online or does not exist");
					return true;
				}
				FactionPlayer facp2 = FactionsMain.getPlayer(p2.getUniqueId());
				FactionPlayer facp = FactionsMain.getPlayer(p.getUniqueId());

				if (!facp2.getFactionName().equals(facp.getFactionName())) {
					p.sendMessage(ChatColor.RED + "That player is not in your faction");
					return true;
				}

				if (!(facp.getRank().getLevel() >= 2) || facp2.getRank().getLevel() >= facp.getRank().getLevel()) {
					p.sendMessage(ChatColor.RED + "You do not have proper permissions");
					return true;
				}
				
				
				facp2.setTag(args[2].replaceAll("&", "§"));
				facp2.getFaction().broadcast(ChatColor.AQUA +  p2.getName() + "'s tag has been changed to " + facp2.getTag());
				
			} catch (Throwable e) {
				p.sendMessage(e.getMessage());
			}
		}
		return false;
	}

}
