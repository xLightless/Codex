package org.codex.factions;

public enum Relationship {

	ALLY((byte) 0, "allies"), 
	
	TRUCE((byte) 1, "truces"),
	
	NEUTRAL((byte) 2, "neutral"),
	
	ENEMY((byte) 3, "enemies");
	
	private byte id;
	private String tense;
	
	Relationship(byte id, String tense) {
		this.id = id;
		this.tense = tense;
	}
	
	public byte getID() {
		return id;
	}
	
	public String getTense() {
		return tense;
	}
	
	public static Relationship getFromID(byte id) throws NullPointerException {
		for(Relationship r: Relationship.values()) {
			if(r.getID() == id)return r;
		}
		throw new NullPointerException();
	}

	public boolean isFriendly() {
		switch(this) {
		case ALLY:
			return true;
		case TRUCE:
			return true;
		case NEUTRAL:
			return false;
		case ENEMY:
			return false;
		}
		return false;
	}
}
