package org.codex.factions.executors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Info implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (args.length == 2) {
			if (FactionsMain.Factions.containsKey(args[1])) {
				FactionObject fac = FactionsMain.getFactionFromName(args[1]);
				for (int x = 0; x <= 3; x++)
					sender.sendMessage(
							"                                                                                                ");
				sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "-----------= " + fac.getFactionName()
						+ " =-----------");
				sender.sendMessage(ChatColor.AQUA + "  Allies - " + ChatColor.LIGHT_PURPLE
						+ fac.getAllies().toString().replace("[", "").replace("]", ""));
				sender.sendMessage(ChatColor.AQUA + "  Truces - " + ChatColor.AQUA
						+ fac.getTruces().toString().replace("[", "").replace("]", ""));
				sender.sendMessage(ChatColor.AQUA + "  Alts - " + ChatColor.GRAY
						+ fac.getAltNames().toString().replace("[", "").replace("]", ""));
				sender.sendMessage(ChatColor.AQUA + "  Enemies - " + ChatColor.GRAY
						+ fac.getEnemies().toString().replace("[", "").replace("]", ""));
				sender.sendMessage(ChatColor.AQUA + "  Online Players - " + ChatColor.GREEN
						+ fac.getOnlinePlayersName().toString().replace("[", "").replace("]", ""));
				sender.sendMessage(ChatColor.AQUA + "  Offline Players - " + ChatColor.DARK_GREEN
						+ fac.getOfflinePlayersName().toString().replace("[", "").replace("]", ""));
				sender.sendMessage(ChatColor.AQUA + "  Faction Claims - " + ChatColor.LIGHT_PURPLE
						+ fac.getClaimedLand().size() + "/" + fac.getPower());
				sender.sendMessage(ChatColor.AQUA + "  Bank Value - " + ChatColor.LIGHT_PURPLE + fac.getValue());

				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "That faction does not exist");
			}
			return false;
		} else {
			Player p = (Player) sender;
			if (FactionsMain.Players.containsKey(p.getUniqueId())) {
				FactionObject fac;
				try {
					fac = FactionsMain.getPlayerFaction(p.getUniqueId());
				} catch (Throwable e) {
					p.sendMessage(e.getMessage());
					return false;
				}
				for (int x = 0; x <= 3; x++)
					p.sendMessage(
							"                                                                                                ");
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "-----------= " + fac.getFactionName()
						+ " =-----------");
				p.sendMessage(ChatColor.AQUA + "  Allies - " + ChatColor.LIGHT_PURPLE
						+ fac.getAllies().toString().replace("[", "").replace("]", ""));
				p.sendMessage(ChatColor.AQUA + "  Truces - " + ChatColor.AQUA
						+ fac.getTruces().toString().replace("[", "").replace("]", ""));
				p.sendMessage(ChatColor.AQUA + "  Alts - " + ChatColor.GRAY
						+ fac.getAltNames().toString().replace("[", "").replace("]", ""));
				p.sendMessage(ChatColor.AQUA + "  Enemies - " + ChatColor.GRAY
						+ fac.getEnemies().toString().replace("[", "").replace("]", ""));
				p.sendMessage(ChatColor.AQUA + "  Online Players - " + ChatColor.GREEN
						+ fac.getOnlinePlayersName().toString().replace("[", "").replace("]", ""));
				p.sendMessage(ChatColor.AQUA + "  Offline Players - " + ChatColor.DARK_GREEN
						+ fac.getOfflinePlayersName().toString().replace("[", "").replace("]", ""));
				p.sendMessage(ChatColor.AQUA + "  Faction Claims - " + ChatColor.LIGHT_PURPLE
						+ fac.getClaimedLand().size() + "/" + fac.getPower());
				p.sendMessage(ChatColor.AQUA + "  Bank Value - " + ChatColor.LIGHT_PURPLE + fac.getValue());

				return true;
			} else {
				p.sendMessage(ChatColor.RED + "You are not inside of a faction");
			}

			return false;
		}

	}

}
