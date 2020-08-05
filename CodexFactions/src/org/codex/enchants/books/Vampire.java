package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Vampire extends Book implements Listener {

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String bl = BookType.MAJESTIC_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Boolean> a = new HashMap<>();
	private static HashMap<Player, Integer> b = new HashMap<>();

	public Vampire() {
		super(is, im,
				List.of(gy + "When you wield a sword with this enchant, ",
						gy + "you will be imbued with the power of the Vampire.",
						gy + "When holding the item you will recieve a hunger for blood",
						gy + "and additional damage. Lucky for you when you attack your ",
						gy + "Hunger will be replenished"),
				5, BookType.MAJESTIC_BOOK, "Vampire ", bl + "Vampire", List.of(Material.DIAMOND_SWORD, Material.DIAMOND_AXE), 3);
	
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addPotionEffect(PotionEffectType.HUNGER, p, 5 - Integer.parseInt(level) + "");
		super.addPotionEffect(PotionEffectType.REGENERATION, p, "1");
		a.put(p, true);
		b.put(p, Integer.parseInt(level));
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		super.removePotionEffect(PotionEffectType.HUNGER, p, 5 - Integer.parseInt(level) + "");
		super.removePotionEffect(PotionEffectType.REGENERATION, p, "1");
		a.put(p, false);
		b.remove(p);
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player))
			return;
		Player p = (Player) e.getDamager();
		if (!(a.containsKey(p) && b.containsKey(p)))
			return;
		if (a.get(p)) {
			e.setDamage(e.getDamage() * 1.05);
			p.setFoodLevel(p.getFoodLevel() + 2);
		}
	}

}
