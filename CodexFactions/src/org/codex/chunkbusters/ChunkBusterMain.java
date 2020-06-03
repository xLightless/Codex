package org.codex.chunkbusters;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ChunkBusterMain implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		System.out.println("recieved event");
		if (e.getBlock().getType().equals(Material.BEACON)) {
			System.out.println("received request");
			Block b = e.getBlock();
			int startx = (b.getX() >> 4) * 16;
			int endx = startx + 16;
			int endz = ((b.getZ() >> 4) * 16) + 16;
			int endy = b.getY();
			for (; startx < endx; startx++) {
				for (int startz = (b.getZ() >> 4) * 16; startz < endz; startz++) {
					for (int starty = 1; starty < endy; starty++) {

						b.getWorld().getBlockAt(startx, starty, startz).setType(Material.AIR);

					}
				}
			}
		}
	}

}
