package org.codex.economy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.codex.factions.FactionsMain;


public class EconomyMain {

	private static Map<String, Double> money = new HashMap<>();
	private Map<String, Double> priceMap = new HashMap<>();	
	private static File moneyData = new File("plugins/CodexFactions/eco.txt");
	
	public EconomyMain(Map<String, Double> map) {
		this.priceMap = map;
	}
	
	public static Map<String, Double> getMap(){
		return money;
	}

	public static double getPlayerMoney(Player p) {
		return money.get(p.getUniqueId().toString());
	}

	public static double getPlayerMoney(UUID u) {
		return money.get(u.toString());
	}
	
	public static double getPlayerMoney(String s) {
		return money.get(s);
	}
	
	public static void setPlayerMoney(Player p, double d) {
		money.put(p.getUniqueId().toString(), d);
	}

	public static void setPlayerMoney(UUID u, double d) {
		money.put(u.toString(), d);
	}
	
	public static void setPlayerMoney(String s, double d) {
		money.put(s, d);
	}
	
	public static void changePlayerMoney(Player p, double d) {
		money.put(p.getUniqueId().toString(), money.get(p.getUniqueId().toString()) + d);
	}
	
	public static void changePlayerMoney(UUID u, double d) {
		money.put(u.toString(), money.get(u.toString()) + d);
	}
	
	public static void changePlayerMoney(String s, double d) {
		money.put(s, money.get(s) + d);
	}
	
	public double getCost(ItemStack is) {
		Material m = is.getType();
		return priceMap.get(m.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static void loadMoney() {
		EconomyMain.money = (Map<String, Double>) FactionsMain.loadData(moneyData);
	}
	
	public static void saveMoney() {
		FactionsMain.save(moneyData, money);
	}
	
}
