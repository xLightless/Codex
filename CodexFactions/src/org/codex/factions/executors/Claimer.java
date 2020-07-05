package org.codex.factions.executors;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.claims.Claim;
import org.codex.factions.claims.ClaimType;
import org.codex.factions.claims.PocketClaim;
import org.codex.factions.claims.RegularClaim;

import net.md_5.bungee.api.ChatColor;

public class Claimer implements Execute {

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
					if (!FactionsMain.isChunkWilderness(c.getX(), c.getZ())) {
						p.sendMessage(ChatColor.RED + "This chunk has already been claimed");
						return true;
					}
					if (args.length >= 2 && fac.getClaimedLand().size() != 0) {
						Claim cl = ClaimType.getClaim(Integer.parseInt(args[1]), c);
						if (ClaimType.followsRules(cl)) {
							fac.addClaim(cl);
						}

						else
							p.sendMessage(ChatColor.RED
									+ "Your claim does not follow the Claiming Regulations. Please type /f help claiming to find out where you went wrong.");
					} else if (fac.getClaimedLand().size() != 0)
						fac.addClaim(new RegularClaim(c));
					else
						fac.addClaim(new PocketClaim(c));

					fac.broadcast(ChatColor.AQUA + p.getName() + " has claimed land at " + c.getX() + ", " + c.getZ());
					return true;
				} else {
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
