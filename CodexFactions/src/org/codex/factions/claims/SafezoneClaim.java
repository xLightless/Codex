package org.codex.factions.claims;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

public class SafezoneClaim implements Claim {

	private Chunk c;

	public SafezoneClaim() {

	}

	public SafezoneClaim(Chunk c) {
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
		return ClaimType.SAFEZONE;
	}

	@Override
	public void setChunk(Chunk c) {
		this.c = c;

	}

}
