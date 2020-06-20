package org.codex.enchants.books;

import net.md_5.bungee.api.ChatColor;

public enum BookType{
	
	COMMON_BOOK(ChatColor.GRAY),
	
	RARE_BOOK(ChatColor.LIGHT_PURPLE),
	
	MAJESTIC_BOOK(ChatColor.BLUE),
	
	LEGENDARY_BOOK(ChatColor.RED),
	
	MYSTICAL_BOOK(ChatColor.GOLD),
	
	ENERGY_BOOK(ChatColor.DARK_AQUA), 
	
	QUANTUM_BOOK(ChatColor.YELLOW),
	
	GODLY_BOOK(ChatColor.DARK_RED);
	
	private String color;
	
	BookType(ChatColor color){
		this.color = "" + color;
	}
	
	BookType(String s){
		this.color = s;
	}
	
	public String getChatColor() {
		return color;
	}
	
	
}
