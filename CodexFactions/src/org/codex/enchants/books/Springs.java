package org.codex.enchants.books;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Springs extends Book {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static final String bl = BookType.MAJESTIC_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;

	public Springs() {
		super(is, im, Book.of(gy + "When applied to boots, the player gets extra jump boost."), 4,
				BookType.MAJESTIC_BOOK, "Springs ", bl + "Springs",
				Book.of(Material.DIAMOND_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS), 6);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addPotionEffect(PotionEffectType.JUMP, p, level);

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		super.removePotionEffect(PotionEffectType.JUMP, p, level);
	}

}
