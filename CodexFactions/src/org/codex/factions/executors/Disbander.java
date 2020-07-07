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
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a Player to run this command");
			return false;
		}
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
		if (!FactionsMain.getPlayer(uuid).getFaction().getLeaderUUID().equals(uuid))
			throw new Throwable(ChatColor.RED + "You are not the leader of this Faction");
		FactionObject fac = FactionsMain.getPlayer(uuid).getFaction();
		for (UUID u : fac.getPlayers())
			FactionsMain.Players.remove(u);
		FactionsMain.Factions.remove(fac.getFactionName().toUpperCase());
		Bukkit.broadcastMessage(
				ChatColor.GRAY + fac.getFactionName() + " has been disbanded by " + Bukkit.getPlayer(uuid).getName());
		for(long l :fac.getLand().keySet()) {
			FactionsMain.ClaimedChunks.remove(l);
		}
		fac.getLand().clear();
	}

}
