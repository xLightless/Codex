package org.codex.factions.claims;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.codex.factions.FactionsMain;

public enum ClaimType {
	NORMAL(0, RegularClaim.class, ChatColor.WHITE),
	
	SAFEZONE(1, SafezoneClaim.class, ChatColor.GREEN),
	
	WARZONE(2, WarzoneClaim.class, ChatColor.RED),
	
	MONSTER(3, MonsterClaim.class, ChatColor.DARK_PURPLE),
	
	POCKET(4, PocketClaim.class, ChatColor.YELLOW);

	private int id;
	private Class<? extends Claim> clazz;
	private ChatColor color;
	
	ClaimType(int id, Class<? extends Claim> clazz, ChatColor color) {
		this.id = id;
		this.clazz = clazz;
		this.color = color;
	}

	public int getID() {
		return id;
	}

	public static Claim getClaim(int id, Chunk c) throws Throwable {
		ClaimType ct = ClaimType.getClaimType(id);
		Class<? extends Claim> c1 = ct.getClazz();
		Claim c2 = 	(Claim) c1.getConstructors()[1].newInstance(c);
		c2.setChunk(c);
		return c2;

	}

	public Class<? extends Claim> getClazz() {
		return clazz;
	}

	public static ClaimType getClaimType(int id) throws Throwable {

		for (ClaimType c : ClaimType.values()) {
			if (c.getID() == id)
				return c;
		}
		throw new Throwable("That Claim Type ID does not exist");
	}

	public static boolean followsRules(Claim cl) {
		Set<ClaimType> cs = ClaimType.getAdjacentClaimTypes(cl);
		if (!cs.contains(ClaimType.POCKET) && cl.getClaimType().equals(ClaimType.POCKET))
			return false;
		else if (!(cs.contains(ClaimType.POCKET) || cs.contains(ClaimType.MONSTER))
				&& cl.getClaimType().equals(ClaimType.MONSTER))
			return false;
		return true;
	}

	public static Set<ClaimType> getAdjacentClaimTypes(Claim c) {
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
	
	public static Set<Claim> getAdjacentClaims(Claim c) {
		Set<Claim> claims = new HashSet<>();
		int x = c.getChunk().getX();
		int z = c.getChunk().getZ();
		String w = c.getChunk().getWorld().getName();
		claims.add(FactionsMain.getClaim(x + 1, z, w));
		claims.add(FactionsMain.getClaim(x + 1, z + 1, w));
		claims.add(FactionsMain.getClaim(x, z + 1, w));
		claims.add(FactionsMain.getClaim(x - 1, z - 1, w));
		claims.add(FactionsMain.getClaim(x - 1, z, w));
		claims.add(FactionsMain.getClaim(x - 1, z + 1, w));
		claims.add(FactionsMain.getClaim(x + 1, z - 1, w));
		claims.add(FactionsMain.getClaim(x, z - 1, w));
		return claims;
	}

	public ChatColor getColor() {
		return color;
	}

}
