package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class SafeGuard extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.LEGENDARY_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, HashMap<ArmorType, Integer>> map = new HashMap<>();

	public SafeGuard() {
		super(is, im, List.of(g + "This enchant has the ability to completely block a hit. This enchant is stackable"),
				0, BookType.LEGENDARY_BOOK, "Safe Guard", c + "Safe Guard", List.of(Material.DIAMOND_HELMET,
						Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
				3);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		HashMap<ArmorType, Integer> map = new HashMap<>();
		map.put((ArmorType) a, Integer.parseInt(level));
		SafeGuard.map.put(p, map);

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		HashMap<ArmorType, Integer> amap = map.containsKey(p) ? map.get(p) : null;
		amap.put((ArmorType) a, 0);
		map.put(p, amap);
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if (!map.containsKey(p))
			return;
		int stackedlevel = 0;
		if (map.get(p).containsKey(ArmorType.HELMET))
			stackedlevel += map.get(p).get(ArmorType.HELMET);
		if (map.get(p).containsKey(ArmorType.CHESTPLATE))
			stackedlevel += map.get(p).get(ArmorType.CHESTPLATE);
		if (map.get(p).containsKey(ArmorType.LEGGINGS))
			stackedlevel += map.get(p).get(ArmorType.LEGGINGS);
		if (map.get(p).containsKey(ArmorType.BOOTS))
			stackedlevel += map.get(p).get(ArmorType.BOOTS);
		Random r = new Random();
		if (stackedlevel / 2 >= r.nextInt(100)) {
			e.setDamage(0);
		}

	}

}
