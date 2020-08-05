package org.codex.enchants.books;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class HealthBoost extends Book implements Listener {

	protected static List<String> lore;
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dr = BookType.MYSTICAL_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;

	public HealthBoost() {
		super(is, im, List.of(gy + "This book has the ability to give you ", gy + "extra health based on your level "),
				4, BookType.LEGENDARY_BOOK, "Health Boost", dr + "Health Boost",
				List.of(Material.DIAMOND_CHESTPLATE, Material.IRON_CHESTPLATE), 5);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addStackPotionEffect(PotionEffectType.HEALTH_BOOST, p, level);
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		PotionEffect p1 = BookManager.getPotionEffect(p.getActivePotionEffects(), PotionEffectType.HEALTH_BOOST);
		if (p1 == null)
			return;
		p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
		int amp = p1.getAmplifier() - (Integer.parseInt(level));
		if (amp > 0)
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, amp, true));
	}

}
