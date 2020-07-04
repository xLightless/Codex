package org.codex.factions.executors;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.claims.ClaimType;
import org.codex.factions.claims.RegularClaim;

import net.md_5.bungee.api.ChatColor;

public class KingmoClaimer implements Execute {

	public boolean onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			try {
				FactionPlayer facp = FactionsMain.getPlayer(p.getUniqueId());
				if (facp.getRank().getLevel() >= 2) {
					Chunk c = p.getLocation().getChunk();
					FactionObject fac = facp.getFaction();
					if (fac.getPower() < fac.getClaimedLand().size() + 1) {
						p.sendMessage(ChatColor.RED + "You are out of faction power.");
						return true;
					}
					if(!FactionsMain.isChunkWilderness(c.getX(), c.getZ())) {
						p.sendMessage(ChatColor.RED + "This chunk has already been claimed");
						return true;
					}
					if (args.length >= 2)
						fac.addClaim(ClaimType.getClaim(Integer.parseInt(args[1]), c));
					else
						fac.addClaim(new RegularClaim(c));

					fac.broadcast(ChatColor.AQUA + p.getName() + " has claimed land at " + c.getX() + ", " + c.getZ());
					return true;
				}else {
					p.sendMessage(ChatColor.RED + "You do not have proper permissions");
					return false;
				}
			} catch (Throwable e) {
				p.sendMessage(e.getMessage());
			}
		}
		return false;
	}

}
