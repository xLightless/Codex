package org.codex.factions.claims;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

public class RegularClaim implements Claim {

	private Chunk c;

	public RegularClaim() {

	}

	public RegularClaim(Chunk c) {
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
		return ClaimType.NORMAL;
	}

	@Override
	public void setChunk(Chunk c) {
		this.c = c;

	}

}
