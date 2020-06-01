package Factions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FactionObject {
	String FactionName;
	private UUID Leader;
	private Map<UUID, FactionPlayer> Players = new HashMap<>();

	public UUID getLeaderUUID() {
		return Leader;
	}
	public boolean inFaction(UUID uuid) {
		return Players.containsKey(uuid);
	}
	public void joinFaction(FactionPlayer fp) {
		
	}
	

}
