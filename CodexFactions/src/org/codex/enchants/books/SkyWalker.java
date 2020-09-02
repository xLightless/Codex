package org.codex.enchants.books;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.armorsets.NetherArmorSet;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class SkyWalker extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.LEGENDARY_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();
	private static Set<Block> blocks = new HashSet<>();

	public SkyWalker() {
		super(is, im,
				Book.of(g + "When walking over an edge not in warzone,",
						g + " A block below you will decay over time."),
				0, BookType.LEGENDARY_BOOK, "Sky Walker", c + "Sky Walker", Book.of(Material.CHAINMAIL_BOOTS,
						Material.LEATHER_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS),
				3);
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
	public void onPlayerWalk(PlayerMoveEvent e) {
		if (!map.containsKey(e.getPlayer()))
			return;
		if (this.checkLocation(e.getTo(), e.getFrom()))
			return;
		Player p = e.getPlayer();
		int level = map.get(p);
		Location loc = p.getLocation();
		if (p.isSneaking())
			return;
		placeBlocks(loc, 1, level);

	}

	@SuppressWarnings("deprecation")
	private void placeBlocks(Location loc, int i, int level) {
		Random r = new Random();

		for (int x = loc.getBlockX() - i; x <= i + loc.getBlockX(); x++)
			for (int z = loc.getBlockZ() - i; z <= i + loc.getBlockZ(); z++) {
				Block b = loc.getWorld().getBlockAt(x, loc.getBlockY() - 1, z);

				if (!b.getType().equals(Material.AIR))
					continue;
				int rand = r.nextInt(3) % 3;
				byte data = (byte) (rand == 0 ? 3 : rand == 1 ? 9 : 11);
				b.setType(Material.STAINED_GLASS);
				b.setData(data);
				Runnable r2 = new Runnable() {

					@Override
					public void run() {
						b.setType(Material.AIR);
						SkyWalker.blocks.remove(b);
					}

				};

				blocks.add(b);
				Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), r2, 8 * level);

			}

	}

	private boolean checkLocation(Location loc1, Location loc2) {
		return loc1.getX() == loc2.getX() ? loc1.getZ() == loc2.getZ() ? true : false : false;
	}

	public static void clearActiveBlocks() {
		for (Block b : blocks) {
			b.setType(Material.AIR);
		}
		NetherArmorSet.clearActiveBlocks();
	}

}
