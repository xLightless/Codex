package org.codex.factions.executors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.Rank;

import net.md_5.bungee.api.ChatColor;

public class Joiner implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Inviter.map.containsKey(p) && args[1].equalsIgnoreCase(Inviter.map.get(p).getFactionName())) {
				if (FactionsMain.Players.containsKey(p.getUniqueId())) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
							+ "You already are in a faction. You must leave to join");
				} else {
					FactionObject fac = Inviter.map.get(p);
					Inviter.map.remove(p);
					FactionPlayer facp = new FactionPlayer(p.getUniqueId(), Rank.RECRUIT, fac.getFactionName());
					FactionsMain.Players.put(p.getUniqueId(), facp);
					fac.broadcast(ChatColor.AQUA + p.getName() + " has joined the faction!");
					fac.invitePlayer(facp);
					p.sendMessage(ChatColor.AQUA + "You have succesfully joined " + fac.getFactionName());
				}
			}
		} else if (sender.hasPermission("Codex.Factions.Admin") || sender.isOp()) {

		}
		return false;
	}

}
