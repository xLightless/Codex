package org.codex.factions.executors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.Rank;
import org.codex.factions.claims.Claim;
import org.codex.factions.claims.ClaimType;
import org.codex.factions.claims.RegularClaim;

public class Claimer implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			try {
				FactionPlayer facp = FactionsMain.getPlayer(p.getUniqueId());
				FactionObject fac = facp.getFaction();
				if (facp != null || fac != null) {
					// check if claim is in wilderness
					// if not wilderness check if chunk is warzone or safezone
					// if regular claim check if can overclaim
					// must be moderator or higher to claim
					if (facp.getRank().getLevel() > Rank.MEMBER.getLevel()) {
						int chunkx = p.getLocation().getBlockX() >> 4;
						int chunkz = p.getLocation().getBlockZ() >> 4;
						if (FactionsMain.isChunkWilderness(chunkx, chunkz)) {
							if (fac.getPower() > fac.getClaimedLand().size()) {
								fac.addClaim(new RegularClaim(((Player) sender).getWorld().getChunkAt(chunkx, chunkz)));
								fac.broadcast(sender.getName() + " has claimed at X: " + (chunkx * 16) + " Z: "
										+ (chunkz * 16));
							} else {

							}
						} else {
							FactionObject original = FactionsMain.getChunkOwner(chunkx, chunkz);
							if (original.getClaimtype() == ClaimType.NORMAL) {
								Claim c = new RegularClaim(((Player) sender).getWorld().getChunkAt(chunkx, chunkz));
								if (original.getPower() < fac.getPower()) {
									original.removeClaim(c);
									original.broadcast(fac.getFactionName() + " has claimed you chunk at X: "
											+ (chunkx * 16) + " Z: " + (chunkz * 16));
									fac.addClaim(c);
									fac.broadcast(sender.getName() + " has claimed at X: " + (chunkx * 16) + " Z: "
											+ (chunkz * 16));
								} else {
									sender.sendMessage("Your faction does not have enough power to overclaim");
								}

							} else {
								sender.sendMessage("You can not claim overclaim this type of chunk");
							}

						}
					} else {
						sender.sendMessage("Must be moderator or higher to claim");
					}

				}
			} catch (Throwable e) {
				p.sendMessage(e.getMessage());
			}
		}
		return false;
	}

}
