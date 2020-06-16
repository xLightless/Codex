package org.codex.factions;

public enum Rank {
	LEADER(4),
	
	COLEADER(3),
	
	MODERATOR(2),
	
	MEMBER(1),
	
	RECRUIT(0);

	private int level;
	

	private Rank(int Level) {
		this.level = Level;
	}

	public static Rank promote(Rank r) throws Throwable {
		int target = r.level + 1;
		if (target >= 0 && target < 5) {
			return Rank.values()[4 - target];
		} else {
			throw new Throwable("Rank does not exist");

		}
	}
	
	public static Rank demote(Rank r) throws Throwable {
		int target = r.level - 1;
		if (target >= 0 && target < 5) {
			return Rank.values()[4 - target];
		} else {
			throw new Throwable("Rank does not exist");

		}
	}
	
	public int getLevel() {
		return level;
	}
	

}
