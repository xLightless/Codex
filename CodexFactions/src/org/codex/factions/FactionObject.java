package org.codex.factions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.codex.factions.claims.Claim;
import org.codex.factions.claims.ClaimType;

public class FactionObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String FactionName;
	private UUID Leader;
	private Set<UUID> Players = new HashSet<>();
	private Set<String> allies = new HashSet<>();
	private Set<String> truces = new HashSet<>();
	private Set<UUID> alts = new HashSet<>();
	private Set<String> enemies = new HashSet<>();
	private int power = 20;
	private Map<Long, Vector2D<Integer, String>> claimedLand = new HashMap<>();
	private double value = 0D;
	private ClaimType claimtype = ClaimType.NORMAL;

	public Set<UUID> getPlayers() {
		return Players;
	}

	public FactionObject(String name, UUID uUID) {
		this.setFactionName(name);
		this.Leader = uUID;
		Players.add(uUID);
		FactionsMain.Players.put(uUID, new FactionPlayer(uUID, Rank.LEADER, name));

	}

	public FactionObject(String name, UUID uUID, ClaimType type) {
		this.setFactionName(name);
		this.Leader = uUID;
		Players.add(uUID);
		FactionsMain.Players.put(uUID, new FactionPlayer(uUID, Rank.LEADER, name));
		this.claimtype = type;
	}

	public UUID getLeaderUUID() {
		return Leader;
	}

	public boolean inFaction(UUID uuid) {
		return Players.contains(uuid);
	}

	public void invitePlayer(FactionPlayer fp) {
		Players.add(fp.getUUID());
	}

	public void kickPlayer(FactionPlayer fp) {
		Players.remove(fp.getUUID());
		FactionsMain.Players.remove(fp.getUUID());
	}

	public void promotePlayer(UUID player) throws Throwable {
		FactionPlayer p = FactionsMain.Players.get(player);
		if (p != null) {
			p.setRank(Rank.promote(p.getRank()));
		} else {
			throw new Throwable("Player not in faction");
		}
	}

	public void demotePlayer(UUID player) throws Throwable {
		FactionPlayer p = FactionsMain.Players.get(player);
		if (p != null) {
			p.setRank(Rank.demote(p.getRank()));
		} else {
			throw new Throwable("Player not in faction");
		}
	}

	public String getFactionName() {
		return FactionName;
	}

	public void setFactionName(String factionName) {
		FactionName = factionName;
	}

	public List<FactionObject> getAllies() {
		List<FactionObject> allies = new ArrayList<>();
		for (String name : this.allies) {
			allies.add(FactionsMain.getFactionFromName(name));
		}
		return allies;
	}

	public List<FactionObject> getTruces() {
		List<FactionObject> truces = new ArrayList<>();
		for (String name : this.truces) {
			truces.add(FactionsMain.getFactionFromName(name));
		}
		return truces;
	}

	public Set<UUID> getAlts() {
		return alts;
	}

	public Set<String> getAltNames() {
		Set<String> names = new HashSet<>();
		for (UUID id : alts) {
			names.add(Bukkit.getPlayer(id).getPlayerListName());
		}
		return names;
	}

	public List<FactionObject> getEnemies() {
		List<FactionObject> enemies = new ArrayList<>();
		for (String name : this.enemies) {
			enemies.add(FactionsMain.getFactionFromName(name));
		}
		return enemies;
	}

	public ClaimType getClaimtype() {
		return claimtype;
	}

	public void setClaimtype(ClaimType claimtype) {
		this.claimtype = claimtype;
	}

	public void addAlly(FactionObject fac) {
		allies.add(fac.getFactionName());
	}

	public void addTruce(FactionObject fac) {
		truces.add(fac.getFactionName());
	}

	public void addAlt(UUID id) {
		alts.add(id);
	}

	public void addEnemy(FactionObject fac) {
		enemies.add(fac.getFactionName());
	}

	@Override
	public String toString() {
		return this.getFactionName();
	}

	public int getPower() {
		return power;
	}

	public void changePower(int i) {
		power += i;
	}

	public List<Claim> getClaimedLand() {
		List<Claim> c = new ArrayList<>();
		for (long l : this.claimedLand.keySet()) {
			c.add(FactionsMain.getChunkFromLong(l, claimedLand.get(l).getVectorOne(),
					claimedLand.get(l).getVectorTwo()));
		}
		return c;
	}

	public void addClaim(Claim c) {
		Chunk a = c.getChunk();

		Long l = FactionsMain.chunkCoordsToLong(a.getX(), a.getZ());
		FactionsMain.ClaimedChunks.put(l, this.getFactionName());
		int i = c.getClaimType().getID();
		String s = a.getWorld().getName();
		claimedLand.put(l, new Vector2D<>(i, s));
	}

	public void removeClaim(Claim c) {
		Chunk a = c.getChunk();
		Long l = FactionsMain.chunkCoordsToLong(a.getX(), a.getZ());
		FactionsMain.ClaimedChunks.remove(l);
		claimedLand.remove(FactionsMain.chunkCoordsToLong(a.getX(), a.getZ()));
	}

	public boolean hasClaim(Claim c) {
		Chunk a = c.getChunk();
		return claimedLand.containsKey(FactionsMain.chunkCoordsToLong(a.getX(), a.getZ()));
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public List<Player> getOnlinePlayers() {
		List<Player> ps = new ArrayList<>();
		for (UUID u : this.Players)
			if (Bukkit.getOfflinePlayer(u).isOnline())
				ps.add(Bukkit.getPlayer(u));
		return ps;
	}

	public List<Player> getOfflinePlayers() {
		List<Player> ps = new ArrayList<>();
		for (UUID u : this.Players)
			if (!Bukkit.getOfflinePlayer(u).isOnline())
				ps.add(Bukkit.getPlayer(u));
		return ps;
	}

	public List<String> getOnlinePlayersName(){
		List<String> ps = new ArrayList<>();
		for(UUID uuid: this.Players) {
			if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
				FactionPlayer p = FactionsMain.Players.get(uuid);
				String tag = p.getTag();
				String status = p.getRank().getStatus();
				ps.add(tag + " " + status + Bukkit.getPlayer(uuid).getName());
			}
		}
		return ps;
	}

	public List<String> getOfflinePlayersName() {
		List<String> ps = new ArrayList<>();
		for (UUID u : this.Players) {
			if (!Bukkit.getOfflinePlayer(u).isOnline()) {
				FactionPlayer p = FactionsMain.Players.get(u);
				String tag = p.getTag();
				String status = p.getRank().getStatus();
				ps.add(tag + " " + status + Bukkit.getOfflinePlayer(u).getName());
			}
		}
			
		return ps;
	}

	public void broadcast(String string) {
		for(Player p : this.getOnlinePlayers()) {
			p.sendMessage(string);
		}
	}

}
