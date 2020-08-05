package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Freeze extends Book {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static final String red = BookType.MAJESTIC_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();

	public Freeze() {
		super(is, im, List.of(gy + "Adds a chance to freeze a player for a short amount of time"), 4,
				BookType.MAJESTIC_BOOK, "Freeze ", red + "Freeze", List.of(Material.DIAMOND_SWORD, Material.DIAMOND_AXE), 4);

	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		map.put(p, Integer.parseInt(level));

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		map.remove(p);
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {

		if (map.containsKey(e.getDamager())) {
			Player damager = (Player) e.getDamager();
			Entity entity = e.getEntity();
			int level = map.get(damager);
			Random r = new Random();
			if (entity instanceof Player) {
				if (level >= r.nextInt(240)) {
					super.addPotionEffect(PotionEffectType.SLOW, (Player) entity, 255, level * 20);
				}
			}

		}

	}

}
