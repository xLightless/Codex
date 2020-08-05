package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class ObsidianShield extends Book {

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dp = BookType.RARE_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;

	public ObsidianShield() {
		super(is, im, List.of(gy + "When applied, you will be immune to fire"), 6, BookType.RARE_BOOK,
				"Obsidian Shield", dp + "Obsidian Shield", List.of(Material.DIAMOND_LEGGINGS, Material.IRON_LEGGINGS), 1);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addPotionEffect(PotionEffectType.FIRE_RESISTANCE, p, level);
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		super.removePotionEffect(PotionEffectType.FIRE_RESISTANCE, p, level);
	}

}
