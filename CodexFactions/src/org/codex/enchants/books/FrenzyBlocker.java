package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class FrenzyBlocker extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static final String c = Book.LEGENDARY;
	private static final ChatColor g = ChatColor.GRAY;
	private static final HashMap<Player, Vector2D<Boolean, Integer>> map = new HashMap<>();

	public FrenzyBlocker() {
		super(is, im, Book.of(g + "This enchant has the ability to block the addition of a frenzy stack"), 5,
				BookType.LEGENDARY_BOOK, "Frenzy Blocker", c + "Frenzy Blocker",
				Book.of(Material.DIAMOND_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET), 4);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		map.put(p, new Vector2D<Boolean, Integer>(true, Integer.parseInt(level)));

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		map.put(p, new Vector2D<Boolean, Integer>(false, Integer.parseInt(level)));

	}

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player ap = (Player) e.getEntity();
			Player dp = (Player) e.getDamager();
			HashMap<Player, Vector2D<Double, Integer>> stack = Frenzy.getStack();
			if (!stack.containsKey(dp))
				return;
			if (!map.containsKey(ap))
				return;
			if (!map.get(ap).getVectorOne())
				return;
			int level = map.get(ap).getVectorTwo();
			double currentStack = stack.get(dp).getVectorOne();
			Random r = new Random();
			int rand = r.nextInt(24);
			if (level >= rand) {
				double temp = (double) currentStack - (2 * (0.01 * (stack.get(dp).getVectorTwo() - 1)));
				if (temp <= 1)
					temp = 1;
				stack.put(dp, new Vector2D<Double, Integer>(temp, stack.get(dp).getVectorTwo()));
				dp.sendMessage(ChatColor.DARK_BLUE + "Your frenzy has been blocked -- minus two stacks");
				Frenzy.setStack(stack);
			}

		}

	}

}
