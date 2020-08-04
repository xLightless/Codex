package org.codex.factions.executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;
import org.codex.factions.Vector2D;
import org.codex.factions.claims.Claim;
import org.codex.factions.claims.ClaimType;

public class Mapper implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {

			String[] chars = { "/", "\\", "|", "%", "&", "$", "(", ")", ":", "?", "}", "{", "]", "[", "#", "@", "!",
					"+", "a", "=", "-", "_", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
					"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
					"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", ">", "<", "√", "ç",
					"≈" };

			Player p = (Player) sender;
			FactionObject fac;
			try {
				fac = FactionsMain.getPlayerFaction(p.getUniqueId());
			} catch (Throwable e) {
				fac = null;
			}
			Location l = p.getLocation();
			List<Claim> claimList = this.getFormattedClaimList(l.getChunk());
			List<FactionObject> mappingList = this.getFormattedList(l.getChunk());
			Set<FactionObject> used = new HashSet<>();
			Map<FactionObject, String> finalMap = new HashMap<>();
			for (FactionObject obj : mappingList) {
				if (used.contains(obj))
					continue;
				Vector2D<String, String[]> v = this.getNextValue(chars);
				finalMap.put(obj, v.getVectorOne());
				chars = v.getVectorTwo();
				used.add(obj);
			}
			Map<Integer, String> message = new HashMap<>();
			for (int i = 0; i < claimList.size(); i++) {
				int row = (int) Math.floor(((double) i) / 10);
				ClaimType t = claimList.get(i).getClaimType();
				FactionObject fac2 = mappingList.get(i);
				String s = t.getColor() == ChatColor.WHITE ? fac2.getRelationshipWith(fac).getColor() + finalMap.get(fac2)
				: t.getColor() + finalMap.get(fac2);
				message.put(row,
						message.containsKey(row) ? message.get(row) + "   " + s : s);
			}
			p.sendMessage(ChatColor.AQUA + "----- Faction Map -----");
			for(int r: message.keySet()) 
				p.sendMessage(message.get(r));
			
			for(FactionObject obj: finalMap.keySet()) 
				p.sendMessage(obj.getRelationshipWith(fac).getColor() + obj.getFactionName() + " is " + finalMap.get(obj));
			
			

		}
		return false;
	}

	private Vector2D<String, String[]> getNextValue(String[] chars) {
		String returnable = chars[0];

		List<String> list = List.of(chars);
		list.remove(0);
		list.add(returnable);
		chars = (String[]) list.toArray();

		return new Vector2D<>(returnable, chars);
	}

	private List<FactionObject> getFormattedList(Chunk chunk) {
		World w = chunk.getWorld();
		List<FactionObject> list = new ArrayList<>();
		for (int x = chunk.getX() - 5; x <= chunk.getX() + 5; x++)
			for (int z = chunk.getZ() - 5; z <= chunk.getZ() + 5; z++) {
				Chunk c = w.getChunkAt(x, z);
				FactionObject fac = FactionsMain.getChunkOwner(c);
				list.add(fac);
			}

		return list;
	}

	private List<Claim> getFormattedClaimList(Chunk chunk) {
		World w = chunk.getWorld();
		List<Claim> list = new ArrayList<>();
		for (int x = chunk.getX() - 5; x <= chunk.getX() + 5; x++)
			for (int z = chunk.getZ() - 5; z <= chunk.getZ() + 5; z++) {
				Claim claim = FactionsMain.getClaim(x, z, w.getName());
				list.add(claim);
			}

		return list;
	}

}
