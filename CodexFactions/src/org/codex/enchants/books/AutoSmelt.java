package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AutoSmelt extends Book implements Listener {

	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String gy = BookType.COMMON_BOOK.getChatColor();
	private static HashMap<Player, Boolean> a = new HashMap<>();
	private static HashMap<Player, Integer> b = new HashMap<>();

	public AutoSmelt() {
		super(is, im,
				Book.of(gy + "This book has the ability to automaticly ", gy + "smelt any ore when you break it. ",
						gy + "Higher levels add the chance for", gy + "multiple ingots to drop"),
				4, BookType.COMMON_BOOK, "AutoSmelt", gy + "AutoSmelt",
				Book.of(Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE), 3);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		a.put(p, true);
		b.put(p, Integer.parseInt(level));

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		a.put(p, false);
		b.remove(p);

	}

	@EventHandler
	public void onBlockMine(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block bl = e.getBlock();
		if (!(a.containsKey(p) && b.containsKey(p)))
			return;
		if (a.get(p) && isAutominable(bl)) {
			e.setCancelled(true);

			Random r = new Random();
			for (int x = 0; x <= r.nextInt(b.get(p) + 1); x++)
				p.getInventory().addItem(new ItemStack(getIngot(bl)));
			bl.setType(Material.AIR);
		}
	}

	private Material getIngot(Block bl) {
		switch (bl.getType()) {
		case IRON_ORE:
			return Material.IRON_INGOT;
		case GOLD_ORE:
			return Material.GOLD_INGOT;
		default:
			return Material.AIR;
		}

	}

	private boolean isAutominable(Block bl) {

		switch (bl.getType()) {

		case IRON_ORE:
			return true;
		case GOLD_ORE:
			return true;
		default:
			return false;

		}

	}

}
