package org.codex.factions;

import java.io.Serializable;
import java.util.UUID;

public class FactionPlayer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private UUID UUID;
	private Rank Rank;
	private String factionName;
	private int power = 100;
	private String tag = "";
	
	public FactionPlayer(UUID uUID, Rank rank, String faction) {
		this.setUUID(uUID);
		this.setRank(rank);
		this.factionName = faction;
		setTag("");
	}
	
	public FactionPlayer(UUID uUID, Rank rank, String faction, String tag) {
		this.setUUID(uUID);
		this.setRank(rank);
		this.factionName = faction;
		this.setTag(tag);
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

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean hasPermission(FactionObject fac, FactionPermissions blockBreak) {
		//TODO: along with permissions
		return false;
	}
	
	
}
