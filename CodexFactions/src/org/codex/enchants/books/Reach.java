package org.codex.enchants.books;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class Reach extends Book {

	private static ItemStack is = new ItemStack(Material.DIAMOND);

	public Reach() {
		super(is, is.getItemMeta(), Book.of(ChatColor.GRAY + "This book increases players reach server side."), 0,
				BookType.LEGENDARY_BOOK, "Reach", BookType.LEGENDARY_BOOK.getChatColor() + "Reach",
				Book.of(Material.DIAMOND_SWORD, Material.DIAMOND_AXE), 4);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		ReachModifier.setReach(p, (Integer.parseInt(level) * 0.5) + ReachModifier.MINIMUM_REACH);

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		ReachModifier.remove(p);

	}

}
