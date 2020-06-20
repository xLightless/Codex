package org.codex.trenchitems;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TrenchMain implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		if (p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
			destroyRegion(event.getBlock().getLocation(), 0, p.getWorld());
		} else if (p.getItemInHand().getType().equals(Material.DIAMOND_SPADE)) {
			destroyRegion(event.getBlock().getLocation(), 0, p.getWorld());
		}
	}

	public void destroyRegion(Location pos, int radius, World w) {
		for (int minx = pos.getBlockX() - radius; minx <= pos.getBlockX() + radius; minx++) {
			for (int miny = pos.getBlockY() - radius; miny <= pos.getBlockY() + radius; miny++) {
				for (int minz = pos.getBlockZ() - radius; minz <= pos.getBlockZ() + radius; minz++) {
					if (miny < 1) {
						continue;
					}
					w.getBlockAt(minx, miny, minz).breakNaturally();
				}
			}
		}
	}
}
