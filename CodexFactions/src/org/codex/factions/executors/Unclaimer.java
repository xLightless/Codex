package org.codex.factions.executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.claims.Claim;
import org.codex.factions.claims.ClaimType;

import net.md_5.bungee.api.ChatColor;

public class Unclaimer implements Execute, Listener {

	private static Map<Player, Claim> confirm = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can execute this command");
			return false;
		}
		Player p = (Player) sender;
		Chunk c = p.getLocation().getChunk();
		FactionObject fac = FactionsMain.getChunkOwner(c);
		FactionPlayer facp;
		try {
			facp = FactionsMain.getPlayer(p.getUniqueId());
		} catch (Throwable t) {
			p.sendMessage(ChatColor.RED + "You are not in a faction");
			return true;
		}
		if (fac == null ? false : !fac.equals(facp.getFaction())) {
			p.sendMessage(ChatColor.RED + "You do not own this claim");
			return true;
		} else if (facp.getRank().getLevel() < 2) {
			p.sendMessage(ChatColor.RED + "You are not of high enough rank to unclaim");
			return true;
		}
		Claim claim = FactionsMain.getClaim(c.getX(), c.getZ(), c.getWorld().toString());

		switch (claim.getClaimType()) {

		case POCKET:
			if (facp.getRank().getLevel() <= 3) {
				p.sendMessage(ChatColor.RED + "Only leaders can remove pocket claims");
				return true;
			}
			p.sendMessage(ChatColor.AQUA + "Are you sure you want to unclaim a pocket claim? "
					+ "This will result in the deletion of all monster claims adjacent "
					+ "this chunk and the correlating faction chunks going through the portal will lead to. If this is your last pocket chunk, all claims in this faction will be deleted. Please Type confirm if you wish to go through with this. If not type anything else.");
			confirm.put(p, claim);
			break;
		case WARZONE:
			p.sendMessage(ChatColor.RED + "TODO");
			break;
		case NORMAL:
			fac.removeClaim(claim);
			fac.broadcast(ChatColor.AQUA + "The Claim at " +  claim.getChunk().getX() + ", " + claim.getChunk().getZ() + " has been unclaimed");
			break;
		case MONSTER:
			fac.removeClaim(claim);
			fac.broadcast(ChatColor.AQUA + "The Monster claim at " +  claim.getChunk().getX() + ", " + claim.getChunk().getZ() + " has been unclaimed");
			break;
		case SAFEZONE:
			p.sendMessage(ChatColor.RED + "TODO");
			break;
		}

		return false;
	}

	@EventHandler
	public void onConfirm(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!confirm.containsKey(p))
			return;
		if (e.getMessage().contains("confirm")) {
			Claim c = confirm.get(p);
			confirm.remove(p);
			p.sendMessage(ChatColor.AQUA + "you have unclaimed the pocket claim " + c.getChunk().getX() + ", "
					+ c.getChunk().getZ());
			FactionObject fac;
			try {
				fac = FactionsMain.getPlayerFaction(p.getUniqueId());
			} catch (Throwable e1) {
				e1.printStackTrace();
				return;
			}
			if(fac.getPocketClaims() - 1 == 0) {
				p.sendMessage(ChatColor.RED + "You have run out of pocket claims. All other claims will be removed");
				for(Claim cs: fac.getClaimedLand()) {
					fac.removeClaim(cs);
					fac.broadcast(ChatColor.AQUA + "The Claim at " +  cs.getChunk().getX() + ", " + cs.getChunk().getZ() + " has been unclaimed");
				}
				return;
			}
			
			List<Claim> deletedChunks = new ArrayList<>();
			deletedChunks.add(c);
			for (Claim cs : ClaimType.getAdjacentClaims(c)) {
					if (!(cs.getClaimType().equals(ClaimType.MONSTER) || !FactionsMain.getChunkOwner(cs.getChunk()).equals(fac)) )continue;
				deletedChunks.add(cs);
			}
			for(Claim cs: deletedChunks) {
				fac.removeClaim(cs);
				fac.broadcast(ChatColor.AQUA + "The Claim at " +  cs.getChunk().getX() + ", " + cs.getChunk().getZ() + " has been unclaimed");
			}
			return;
			
		}else {
			p.sendMessage(ChatColor.AQUA + "You have successfully canceled this operation.");
		}
	}

}
