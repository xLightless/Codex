package org.codex.factions.Executors;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;

public class Kicker implements Execute {

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(args.length > 1) {
			if(sender instanceof Player) {
				@SuppressWarnings("deprecation")
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
				if(target == null) {
					sender.sendMessage("Could not find player");
					return;
				}
				Player p = (Player) sender;
				UUID id = p.getUniqueId();
				try {
					FactionPlayer fp = FactionsMain.getPlayer(id);
					if(fp.getRank().Level >= 2) {
						FactionPlayer tuuid;
						if(fp.getFaction().getPlayers().contains(target.getUniqueId()) && (tuuid = FactionsMain.getPlayer(target.getUniqueId())) != null) {
							if(tuuid.getFaction() == fp.getFaction()) {
								if(tuuid.getRank().Level < fp.getRank().Level) {
									sender.sendMessage("You have kicked" + args[1]);
									fp.getFaction().kickPlayer(fp);
									
								}else {
									sender.sendMessage("You can not kick this user");
								}
							}
						}else {
							sender.sendMessage("This player is not in your faction");
						}
					}else {
						sender.sendMessage("You do not have permission to do this");
					}
				}catch(Throwable e) {
					sender.sendMessage(e.getMessage());
				}
				
			}
		}
	}

}
