package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Gears extends Book {

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dp = BookType.MYSTICAL_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;

	public Gears() {
		super(is, im, List.of(gy + "When applied to boots, the player gets extra speed."), 5, BookType.LEGENDARY_BOOK,
				"Gears ", dp + "Gears", List.of(Material.DIAMOND_BOOTS, Material.IRON_BOOTS), 3);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addPotionEffect(PotionEffectType.SPEED, p, level);
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		super.forceRemoveEffect(PotionEffectType.SPEED, p);

	}

}
