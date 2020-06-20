package org.codex.factions.executors;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Disbander implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		try {
			this.clearFaction(p.getUniqueId());
			return true;
		} catch (Throwable e) {
			p.sendMessage(e.getMessage());
		}
		return false;
	}
	
	private void clearFaction(UUID uuid) throws Throwable {
		if (!FactionsMain.Players.containsKey(uuid))
			throw new Throwable(ChatColor.RED + "You are not inside of a Faction");
		if (!FactionsMain.Factions.get(FactionsMain.Players.get(uuid).getFactionName()).getLeaderUUID().equals(uuid))
			throw new Throwable(ChatColor.RED + "You are not the leader of this Faction");
		FactionObject fac = FactionsMain.Players.get(uuid).getFaction();
		for (UUID u : fac.getPlayers())
			FactionsMain.Players.remove(u);
		FactionsMain.Factions.remove(fac.getFactionName());
		Bukkit.broadcastMessage(
				ChatColor.GRAY + fac.getFactionName() + " has been disbanded by " + Bukkit.getPlayer(uuid).getName());
	}

}
