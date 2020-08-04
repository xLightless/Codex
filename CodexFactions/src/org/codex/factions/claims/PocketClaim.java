package org.codex.factions.claims;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

public class PocketClaim implements Claim {

	private Chunk c;

	public PocketClaim() {
		
	}

	public PocketClaim(Chunk c) {
		this.c = c;
	}

	@Override
	public void onEnter(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeave(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Chunk getChunk() {
		return c;
	}

	@Override
	public ClaimType getClaimType() {
		return ClaimType.POCKET;
	}

	@Override
	public void setChunk(Chunk c) {
		this.c = c;

	}

}
