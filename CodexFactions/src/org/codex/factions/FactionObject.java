package org.codex.factions;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FactionObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private String FactionName;
	private UUID Leader;
	private Set<UUID> Players = new HashSet<>();

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

}
