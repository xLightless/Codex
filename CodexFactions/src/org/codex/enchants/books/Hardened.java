package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class Hardened extends Book implements Listener {

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String p = BookType.LEGENDARY_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector2D<Integer, ArmorType>> a = new HashMap<>();

	public static HashMap<Player, Vector2D<Integer, ArmorType>> a() {
		return a;
	}

	public Hardened() {
		super(is, im, List.of(gy + "Unbreaking on crack"), 4, BookType.LEGENDARY_BOOK, "Hardened", p + "Hardened",
				List.of(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS,
						Material.DIAMOND_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS,
						Material.IRON_BOOTS),
				3);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		if (t instanceof ArmorType) {
			ArmorType a = (ArmorType) t;
			Vector2D<Integer, ArmorType> v = new Vector2D<>(Integer.parseInt(level), a);
			Hardened.a.put(p, v);
		}
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		a.remove(p);

	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e) {
		Player p = e.getPlayer();
		ItemStack is = e.getItem();
		if (a.containsKey(p) && ArmorListener.isArmor(is.getType())) {
			ArmorType at = ArmorListener.getArmorType(is.getType());
			if (a.get(p).getVectorTwo().equals(at)) {
				Random r = new Random();
				int rInt = r.nextInt(100);
				if (rInt <= a.get(p).getVectorOne() * 11) {
					e.setDamage(0);
					e.setCancelled(true);
				}
			}

		}

	}

}
