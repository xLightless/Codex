package org.codex.factions;

import java.util.UUID;

public class FactionPlayer{
	private UUID UUID;
	private Rank Rank;
	private FactionObject faction;
	
	public FactionPlayer(UUID uUID, Rank rank, FactionObject faction) {
		this.setUUID(uUID);
		this.setRank(rank);
		this.faction = faction;
		
	}
	
	public UUID getUUID() {
		return UUID;
	}
	public void setUUID(UUID uUID) {
		UUID = uUID;
	}
	public Rank getRank() {
		return Rank;
	}
	public void setRank(Rank rank) {
		Rank = rank;
	}
	public String getFactionName() {
		return faction.getFactionName();
	}

	public FactionObject getFaction() {
		return faction;
	}

	public void setFaction(FactionObject faction) {
		this.faction = faction;
	}
	
}
