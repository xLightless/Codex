package org.codex.factions.claims;

import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;
import org.codex.factions.Vector2D;
import org.codex.packetmanager.PacketMain;

import net.md_5.bungee.api.ChatColor;


public class ClaimManager implements Listener {

	@EventHandler
	public void onPlayerEnterChunk(PlayerMoveEvent e) {
		if (FactionsMain.getChunkOwner(e.getFrom().getChunk())
				!= FactionsMain.getChunkOwner(e.getTo().getChunk())) {
			if(FactionsMain.getChunkOwner(e.getTo().getChunk()) != null) 
				PacketMain.sendTitle(e.getPlayer(), ChatColor.GREEN + FactionsMain.getChunkOwner(e.getTo().getChunk()).getFactionName(), "", 3, 20, 3);
				else
				PacketMain.sendTitle(e.getPlayer(), ChatColor.GREEN + "Wilderness", "", 3, 20, 3);
		}
		if(FactionsMain.getChunkOwner(e.getTo().getChunk()) != null){
			FactionObject fac = FactionsMain.getChunkOwner(e.getTo().getChunk());
			ClaimManager.formClaim(fac.getLand(), e.getTo().getChunk()).onEnter(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Chunk c = e.getBlock().getChunk();
		FactionObject fac = FactionsMain.getChunkOwner(c);
		Player p = e.getPlayer();
		if(fac == null)return;
		else if(!fac.getOnlinePlayers().contains(p)) {
			p.sendMessage(ChatColor.RED + "you may not break blocks in this claim");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Chunk c = e.getBlock().getChunk();
		FactionObject fac = FactionsMain.getChunkOwner(c);
		Player p = e.getPlayer();
		if(fac == null)return;
		else if(!fac.getOnlinePlayers().contains(p)) {
			p.sendMessage(ChatColor.RED + "you may not place blocks in this claim");
			e.setCancelled(true);
		}
	}
	
	public static Claim formClaim(Map<Long, Vector2D<Integer, String>> map, Chunk c) {
		return FactionsMain.getClaim(c.getX(), c.getZ(), map.get(FactionsMain.chunkCoordsToLong(c.getX(), c.getZ())).getVectorTwo());
	}
	
	

}
