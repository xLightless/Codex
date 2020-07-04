package org.codex.factions.claims;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Chunk;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;

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

	public static boolean followsRules(Claim cl) {
		if(ClaimType.getAdjacentClaimTypes(cl).contains(ClaimType.POCKET) && cl.getClaimType().equals(ClaimType.POCKET))return true;
		return false;
	}
	
	private static Set<ClaimType> getAdjacentClaimTypes(Claim c) {
		Set<ClaimType> claims = new HashSet<>();
		int x = c.getChunk().getX();
		int z = c.getChunk().getZ();
		String w = c.getChunk().getWorld().getName();
		claims.add(FactionsMain.getClaim(x + 1, z, w).getClaimType());
		claims.add(FactionsMain.getClaim(x + 1, z + 1, w).getClaimType());
		claims.add(FactionsMain.getClaim(x, z + 1, w).getClaimType());
		claims.add(FactionsMain.getClaim(x - 1, z - 1, w).getClaimType());
		claims.add(FactionsMain.getClaim(x - 1, z, w).getClaimType());
		claims.add(FactionsMain.getClaim(x - 1, z + 1, w).getClaimType());
		claims.add(FactionsMain.getClaim(x + 1, z - 1, w).getClaimType());
		claims.add(FactionsMain.getClaim(x, z - 1, w).getClaimType());
		return claims;
	}
	
	
	
	
}
