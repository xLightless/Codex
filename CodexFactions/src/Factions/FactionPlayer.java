package Factions;

import java.util.UUID;

public class FactionPlayer {
	private UUID UUID;
	private Rank Rank;
	private String FactionName;
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
		return FactionName;
	}
	public void setFactionName(String factionName) {
		FactionName = factionName;
	}
}
