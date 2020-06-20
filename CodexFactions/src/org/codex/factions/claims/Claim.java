package org.codex.factions.claims;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

public interface Claim {
	
	public void onEnter(Entity e);
	
	public void onLeave(Entity e);
	
	public Chunk getChunk();
	
	public ClaimType getClaimType();

	public void setChunk(Chunk c);
	
	public static long chunkCoordsToLong(int x, int z) {
			long posz = z;
			long posx = x;
			posx = posx << 32;
			long result = posx | posz;
			return result;
		}
}
