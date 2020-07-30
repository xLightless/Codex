package org.codex.economy;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class EconomyMain implements Listener {

	private static Map<String, Double> money = new HashMap<>();
	private Map<String, Double> priceMap = new HashMap<>();
	private static File moneyData = new File("plugins/CodexFactions/eco.txt");
	private static Map<Date, Map<String, String>> history = new HashMap<>();

	public EconomyMain(Map<String, Double> map) {
		Bukkit.getServer().getPluginManager().registerEvents(this, FactionsMain.getMain());
		this.priceMap = map;
	}

	public static Map<String, Double> getMap() {
		return money;
	}

	public static double getPlayerMoney(OfflinePlayer p) {
		return getPlayerMoney(p.getUniqueId());
	}

	public static double getPlayerMoney(UUID u) {
		return getPlayerMoney(u.toString());
	}

	public static double getPlayerMoney(String s) {
		return money.containsKey(s) ? money.get(s) : 0;
	}

	public static void setPlayerMoney(OfflinePlayer p, double d) {
		setPlayerMoney(p.getUniqueId(), d);
	}

	public static void setPlayerMoney(UUID u, double d) {
		setPlayerMoney(u.toString(), d);
	}

	public static void setPlayerMoney(String s, double d) {
		money.put(s, d);
		addHistory(s, d);
	}

	public static void changePlayerMoney(OfflinePlayer p, double d) {
		changePlayerMoney(p.getUniqueId(), d);
	}

	public static void changePlayerMoney(UUID u, double d) {
		changePlayerMoney(u.toString(), d);
	}

	public static void changePlayerMoney(String s, double d) {
		if (money.containsKey(s)) {
			money.put(s, money.get(s) + d);
		} else {
			money.put(s, d);
		}
		addHistory(s, d);
	}

	private static void addHistory(String s, double d) {
		Date date = new Date();
		Map<String, String> map = history.containsKey(date) ? history.get(date) : new HashMap<>();
		if (isNegative((int) d))
			map.put(s, formatString(EconomyEnum.TAKE, d));
		else
			map.put(s, formatString(EconomyEnum.GIVE, d));
		history.put(date, map);
	}

	public double getCost(ItemStack is) {
		Material m = is.getType();
		return priceMap.get(m.toString());
	}

	@SuppressWarnings("unchecked")
	public static void loadMoney() {
		EconomyMain.money = (Map<String, Double>) FactionsMain.loadData(moneyData) == null ? new HashMap<>()
				: (Map<String, Double>) FactionsMain.loadData(moneyData);
	}

	public static void saveMoney() {
		FactionsMain.save(moneyData, money);
	}

	public static final String formatString(EconomyEnum e, double d) {
		return e.getID() + "-" + d;
	}

	public static final EconomyEnum getTypeFromString(String s) {
		return EconomyEnum.getFromID(Byte.parseByte(s.split("-")[0]));
	}

	public static final double getAmountFromString(String s) {
		return Double.parseDouble(s.split("-")[1]);
	}

	private static boolean isNegative(int i) {
		return i >= 0 ? true : false;
	}

	public static ItemStack createWithdrawNote(double d, Player p) {
		ItemStack is = new ItemStack(Material.PAPER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Money Note : " + d);
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN + "Withdrawn by " + p.getName());
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@EventHandler
	public void onMoneyEquip(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if (is.getType().equals(Material.PAPER) && is.getItemMeta().getLore().get(0).contains("Withdrawn by")
				&& is.getItemMeta().getDisplayName().contains("Money Note : ")) {
			double d = Double.parseDouble(is.getItemMeta().getDisplayName().split(" ")[3]) * is.getAmount();
			EconomyMain.changePlayerMoney(p, d);
			p.setItemInHand(new ItemStack(Material.AIR));
			p.sendMessage(ChatColor.AQUA + "You have equiped a money note worth " + d);
		}
	}

}

enum EconomyEnum {

	GIVE((byte) 0), TAKE((byte) 1), SET((byte) 2);

	public byte id;

	EconomyEnum(byte i) {
		id = i;
	}

	public byte getID() {
		return id;
	}

	public static EconomyEnum getFromID(byte id) throws NullPointerException {
		for (EconomyEnum e : EconomyEnum.values())
			if (e.getID() == id)
				return e;
		throw new NullPointerException("not a valid ID");
	}

}
