package org.codex.factions.claims;

import org.bukkit.Chunk;

public enum ClaimType {
	NORMAL(0, RegularClaim.class),
	SAFEZONE(1, SafezoneClaim.class),
	WARZONE(2, WarzoneClaim.class),
	MONSTER(3, MonsterClaim.class),
	POCKET(4, PocketClaim.class);
	
	private int id;
	private Class<? extends Claim> clazz;
	
	ClaimType(int id, Class<? extends Claim> clazz) {
		this.id = id;
		this.clazz = clazz;
	}
	
	public int getID() {
		return id;
	}
	
	public static Claim getClaim(int id, Chunk c) throws Throwable{
		ClaimType ct = ClaimType.getClaimType(id);
		Class<? extends Claim> c1 = ct.getClazz();
		Claim c2 = c1.newInstance();
		c2.setChunk(c);
		return c2;
		 
	}
	
	public Class<? extends Claim> getClazz(){
		return clazz;
	}
	
	public static ClaimType getClaimType(int id) throws Throwable {
	
		for(ClaimType c: ClaimType.values()) {
			if(c.getID() == id)return c;
		}
		throw new Throwable("That Claim Type ID does not exist");
	}
	
	
}
