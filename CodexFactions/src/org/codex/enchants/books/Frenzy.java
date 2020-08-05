package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.FactionsMain;
import org.codex.factions.Vector2D;
import org.codex.factions.Vector3D;

import net.md_5.bungee.api.ChatColor;

public class Frenzy extends Book implements Listener {

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dr = BookType.MYSTICAL_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector3D<Material, Integer, Boolean>> map = new HashMap<>();
	private static HashMap<Player, Vector2D<Double, Integer>> stack = new HashMap<>();
	private static HashMap<Player, Integer> decay = new HashMap<>();

	public Frenzy() {
		super(is, im, List.of(gy + "When attacking a player, your outgoing damage gets mutliplied"), 5,
				BookType.MYSTICAL_BOOK, "Frenzy", dr + "Frenzy", List.of(Material.DIAMOND_AXE, Material.DIAMOND_SWORD), 6);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		Vector3D<Material, Integer, Boolean> v = new Vector3D<>(p.getInventory().getItemInHand().getType(),
				Integer.parseInt(level), true);
		map.put(p, v);

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		Vector3D<Material, Integer, Boolean> v = new Vector3D<>(Material.AIR, Integer.parseInt(level), false);
		map.put(p, v);

	}

	public static HashMap<Player, Vector2D<Double, Integer>> getStack() {
		return stack;
	}

	public static void setStack(HashMap<Player, Vector2D<Double, Integer>> stack) {
		Frenzy.stack = stack;
	}

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (map.containsKey(p)) {
				if (map.get(p).getVectorThree()) {
					double damageMultiplier;
					if (!stack.containsKey(p)) {
						double temp = (double) 1.0 + (.01 * (map.get(p).getVectorTwo() - 1));
						damageMultiplier = temp;
						decay(p);
					} else {
						if (stack.get(p).getVectorOne() <= 1.3) {
							double temp = (double) stack.get(p).getVectorOne()
									+ (.01 * (map.get(p).getVectorTwo() - 1));
							if (temp > 1.3)
								temp = 1.3;
							damageMultiplier = temp;
							decay(p);
						} else {
							damageMultiplier = stack.get(p).getVectorOne();
							decay(p);
						}
					}
					stack.put(p, new Vector2D<Double, Integer>(damageMultiplier, map.get(p).getVectorTwo()));
					e.setDamage(e.getDamage() * damageMultiplier);
				}
			}
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (stack.containsKey(p)) {
				double temp = (double) stack.get(p).getVectorOne() - (2 * (0.01 * (map.get(p).getVectorTwo() - 1)));
				if (temp <= 1)
					temp = 1;
				stack.put(p, new Vector2D<Double, Integer>(temp, map.get(p).getVectorTwo()));
			}
		}
	}

	private void startDecay(Player p) {
		decay.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {

			@Override
			public void run() {
				stack.put(p, new Vector2D<Double, Integer>(1D, map.get(p).getVectorTwo()));

			}

		}, (long) (20 * 1.3)));
	}

	private void cancelDecay(Player p) {
		Bukkit.getScheduler().cancelTask(decay.get(p));
	}

	private void decay(Player p) {
		if (decay.containsKey(p)) {
			cancelDecay(p);
			startDecay(p);
		} else {
			startDecay(p);
		}
	}

}
