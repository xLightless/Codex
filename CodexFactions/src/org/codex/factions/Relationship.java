package org.codex.factions;

import org.bukkit.ChatColor;

public enum Relationship {

	ALLY((byte) 0, "allies", ChatColor.LIGHT_PURPLE, true), 
	
	TRUCE((byte) 1, "truces", ChatColor.BLUE, true),
	
	NEUTRAL((byte) 2, "neutral", ChatColor.WHITE, false),
	
	ENEMY((byte) 3, "enemies", ChatColor.RED, false);
	
	private byte id;
	private String tense;
	private ChatColor color;
	private boolean friendly;
	
	Relationship(byte id, String tense, ChatColor color, boolean friendly) {
		this.id = id;
		this.tense = tense;
		this.color = color;
		this.friendly = friendly;
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
		return friendly;
	}

	public ChatColor getColor() {
		return color;
	}
}
