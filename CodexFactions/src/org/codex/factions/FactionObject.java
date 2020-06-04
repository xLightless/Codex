package org.codex.factions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.codex.factions.claims.Claim;


public class FactionObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private String FactionName;
	private UUID Leader;
	private Set<UUID> Players = new HashSet<>();
	private List<FactionObject> allies = new ArrayList<>();
	private List<FactionObject> truces = new ArrayList<>();
	private List<AltFactionObject> alts = new ArrayList<>();
	private List<FactionObject> enemies = new ArrayList<>();
	private int power = 20;
	private List<Claim> claimedLand = new ArrayList<>();
	private double value = 0D;
	
	public Set<UUID> getPlayers() {
		return Players;
	}

	public FactionObject(String name, UUID uUID) {
		this.setFactionName(name);
		this.Leader = uUID;
		Players.add(uUID);
		FactionsMain.Players.put(uUID, new FactionPlayer(uUID, Rank.LEADER, name));
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
		FactionName = factionName.toUpperCase();
	}

	public List<FactionObject> getAllies() {
		return allies;
	}
	
	public List<FactionObject> getTruces() {
		return truces;
	}

	public List<AltFactionObject> getAlts() {
		return alts;
	}
	
	public List<FactionObject> getEnemies(){
		return enemies;
	}
	
	
	public void addAlly(FactionObject fac) {
		allies.add(fac);
	}
	
	public void addTruce(FactionObject fac) {
		truces.add(fac);
	}
	
	public void addAlt(AltFactionObject fac) {
		alts.add(fac);
	}
	
	public void addEnemy(FactionObject fac) {
		enemies.add(fac);
	}
	

	@Override
	public String toString() {
		return this.getFactionName();
	}

	public int getPower() {
		return power;
	}

	public void changePower(int i) {
		power+=i;
	}

	public List<Claim> getClaimedLand() {
		return claimedLand;
	}
	
	public void addClaim(Claim c) {
		claimedLand.add(c);
	}
	
	public void removeClaim(Claim c) {
		claimedLand.remove(c);
	}
	
	public boolean hasClaim(Claim c) {
		return claimedLand.contains(c);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public List<Player> getOnlinePlayers() {
		List<Player> ps = new ArrayList<>();
		for(UUID u: this.Players) 
			if(Bukkit.getOfflinePlayer(u).isOnline())ps.add(Bukkit.getPlayer(u));
		return ps;
	}
	
	public List<Player> getOfflinePlayers(){
		List<Player> ps = new ArrayList<>();
		for(UUID u: this.Players) 
			if(!Bukkit.getOfflinePlayer(u).isOnline())ps.add(Bukkit.getPlayer(u));
		return ps;
	}
	
	public List<String> getOnlinePlayersName(){
		List<String> ps = new ArrayList<>();
		for(UUID u: this.Players) 
			if(Bukkit.getOfflinePlayer(u).isOnline())ps.add(Bukkit.getPlayer(u).getName());
		return ps;
	}
	
	public List<String> getOfflinePlayersName(){
		List<String> ps = new ArrayList<>();
		for(UUID u: this.Players) 
			if(!Bukkit.getOfflinePlayer(u).isOnline())ps.add(Bukkit.getPlayer(u).getName());
		return ps;
	}
	 
}
