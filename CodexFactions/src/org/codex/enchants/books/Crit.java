package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Crit extends Book {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.LEGENDARY_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();

	public Crit() {
		super(is, im,
				Book.of(g + "When attacking a play there is a slight chance to push the player in the air.",
						g + "This allows you to get a large crit streak on the player"),
				4, BookType.LEGENDARY_BOOK, "Crit", c + "Crit", Book.of(Material.DIAMOND_SWORD), 4);
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
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (map.containsKey(p)) {
				double l = map.get(p);
				if (super.random(l, 40)) {
					Entity e1 = e.getEntity();
					Location loc = e1.getLocation();
					e1.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY() + (1 * (l * 0.5)), loc.getZ()));
				}
			}
		}
	}

}
