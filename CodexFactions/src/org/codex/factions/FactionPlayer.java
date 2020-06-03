package org.codex.factions;

import java.io.Serializable;
import java.util.UUID;

public class FactionPlayer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private UUID UUID;
	private Rank Rank;
	private String factionName;
	
	public FactionPlayer(UUID uUID, Rank rank, String faction) {
		this.setUUID(uUID);
		this.setRank(rank);
		this.factionName = faction;
		
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
		return factionName;
	}

	public void setFactionName(String factionName) {
		this.factionName = factionName;
	} 
	public FactionObject getFaction() {
		return FactionsMain.getFactionFromName(factionName);
	}
	
	
}
