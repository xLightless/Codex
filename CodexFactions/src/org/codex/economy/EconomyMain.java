package org.codex.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class EconomyMain {

	private static Map<String, Double> money = new HashMap<>();
	private Map<String, Double> priceMap = new HashMap<>();	
	
	
	public EconomyMain(Map<String, Double> map) {
		this.priceMap = map;
	}

	public double getPlayerMoney(Player p) {
		return money.get(p.getUniqueId().toString());
	}

	public double getPlayerMoney(UUID u) {
		return money.get(u.toString());
	}
	
	public double getPlayerMoney(String s) {
		return money.get(s);
	}
	
	public void setPlayerMoney(Player p, double d) {
		money.put(p.getUniqueId().toString(), d);
	}

	public void setPlayerMoney(UUID u, double d) {
		money.put(u.toString(), d);
	}
	
	public void setPlayerMoney(String s, double d) {
		money.put(s, d);
	}
	
	public void changePlayerMoney(Player p, double d) {
		money.put(p.getUniqueId().toString(), money.get(p.getUniqueId().toString()) + d);
	}
	
	public void changePlayerMoney(UUID u, double d) {
		money.put(u.toString(), money.get(u.toString()) + d);
	}
	
	public void changePlayerMoney(String s, double d) {
		money.put(s, money.get(s) + d);
	}
	
	public double getCost(ItemStack is) {
		Material m = is.getType();
		return priceMap.get(m.toString());
	}
	
}
