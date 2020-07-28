package org.codex.factions.executors;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.Rank;

import net.md_5.bungee.api.ChatColor;

public class Ranker implements Execute {

	private Rank rank;
	private String permission;
	
	public Ranker(Rank r, @Nullable String permission) {
		this.rank = r;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player && args.length >= 2) {
			Player p = (Player) sender;
			try {
				FactionObject fac = FactionsMain.getPlayerFaction(p.getUniqueId());
				FactionPlayer pfac = FactionsMain.getPlayer(p.getUniqueId());
				int rankLevel = rank.getLevel();
				OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[1]);
				if(permission != null) {
					if(!p.hasPermission(permission)) {
					p.sendMessage(ChatColor.RED + "You do not have propper permissions. Contact your faction leader if this is an issue");
					return false;
					}
				}
				if((rankLevel < pfac.getRank().getLevel() || pfac.getRank().equals(Rank.LEADER)) && fac.getPlayers().contains(p2.getUniqueId())) {
					 FactionPlayer p2fac = FactionsMain.getPlayer(p2.getUniqueId());
					 p2fac.setRank(rank);
					 FactionsMain.Players.put(p2fac.getUUID(), p2fac);
					 p.sendMessage(ChatColor.AQUA + "you have promoted " + p2.getName());
				}
			} catch (Throwable e) {
				e.printStackTrace();
				p.sendMessage(e.getMessage());
			}
		}
		return false;
	}

}
