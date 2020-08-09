package org.codex.enchants.books;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class Grounded extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.QUANTUM_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();

	public Grounded() {
		super(is, im, Book.of(g + "This enchant gives reduced knockback."), 6, BookType.QUANTUM_BOOK, "Grounded",
				c + "Grounded", Book.of(Material.DIAMOND_BOOTS, Material.IRON_BOOTS), 3);
		// TODO Auto-generated constructor stub
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
	public void onPlayerKnockBack(EntityDamageEvent e) {
		if (!map.containsKey(e.getEntity()))
			return;

		Player p = (Player) e.getEntity();
		int level = map.get(p);
		Vector pVec = p.getVelocity();

		p.setVelocity(pVec.multiply(1 - (level / 10)));
		
		return;

	}

}
