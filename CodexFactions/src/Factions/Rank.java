package Factions;

public enum Rank {
	Leader(4), CoLeader(3), Moderater(2), Member(1), Recruit(0);

	int Level;

	private Rank(int Level) {
		this.Level = Level;
	}

	public static Rank promote(Rank r) throws Throwable {
		int target = r.Level + 1;
		if (target >= 0 && target < 5) {
			return Rank.values()[4 - target];
		} else {
			throw new Throwable("Rank does not exist");

		}
	}
	
	public static Rank demote(Rank r) throws Throwable {
		int target = r.Level - 1;
		if (target >= 0 && target < 5) {
			return Rank.values()[4 - target];
		} else {
			throw new Throwable("Rank does not exist");

		}
	}

}
