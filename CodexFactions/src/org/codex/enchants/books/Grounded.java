package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Grounded extends Book {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.QUANTUM_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();

	public Grounded() {
		super(is, im, List.of(g + "This enchant gives reduced knockback."), 6, BookType.QUANTUM_BOOK,
				"Grounded", c + "Grounded", List.of(Material.DIAMOND_BOOTS, Material.IRON_BOOTS), 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		// TODO Auto-generated method stub

	}

}
