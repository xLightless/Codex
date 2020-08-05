package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Fangs extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.LEGENDARY_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();

	public Fangs() {
		super(is, im,
				List.of(g + "This sword enchant will inflict players with the bleed effect.",
						g + "They will lose damage over time and remain combat tagged"),
				4, BookType.LEGENDARY_BOOK, "Fangs", c + "Fangs", List.of(Material.DIAMOND_SWORD, Material.DIAMOND_AXE),
				5);
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
	public void onAttack(EntityDamageByEntityEvent e) {
		if (map.containsKey(e.getDamager())) {
			Player p = (Player) e.getDamager();
			Random r = new Random();
			int random = r.nextInt(100);
			int level = map.get(p);
			if (level - 1 >= random - 1) {
				if (e.getEntity() instanceof LivingEntity)
					this.bleed((LivingEntity) e.getEntity(), level);
				else
					return;

			}
		}

	}

	public void bleed(LivingEntity entity, int level) {
		for (int i = 0; i <= 3 * level; i++) {
			Bukkit.getScheduler().runTaskLater(FactionsMain.getMain(), new Runnable() {

				@Override
				public void run() {
					entity.damage(0.15 * level);

				}

			}, (20 / (level) * i));
		}

	}

}
