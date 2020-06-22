package org.codex.factions.executors;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Kicker implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length > 1) {
			if(sender instanceof Player) {
				@SuppressWarnings("deprecation")
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
				if(target == null) {
					sender.sendMessage("Could not find player");
					return false;
				}
				Player p = (Player) sender;
				UUID id = p.getUniqueId();
				try {
					FactionPlayer fp = FactionsMain.getPlayer(id);
				
					if(fp.getRank().getLevel() >= 2) {
						FactionPlayer tuuid = FactionsMain.getPlayer(target.getUniqueId());
						if(fp.getFaction().getPlayers().contains(target.getUniqueId())) {
							if(tuuid.getFaction() == fp.getFaction()) {
								if(tuuid.getRank().getLevel() < fp.getRank().getLevel()) {
									sender.sendMessage(ChatColor.DARK_AQUA + "You have kicked " + args[1]);
									fp.getFaction().kickPlayer(tuuid);
									if(target.isOnline())((Player) target).sendMessage(ChatColor.AQUA + "you have been kicked from your faction.");
									return true;
								}else {
									sender.sendMessage(ChatColor.RED + "You can not kick this user");
									return false;
								}
							}
						}else {
							sender.sendMessage(ChatColor.RED + "This player is not in your faction");
							return false;
						}
					}else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to do this");
						return false;
					}
				}catch(Throwable e) {
					sender.sendMessage(e.getMessage());
				}
				
			}
		}
		return false;
	}

}
