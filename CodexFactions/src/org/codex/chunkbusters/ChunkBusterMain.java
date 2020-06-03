package org.codex.chunkbusters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ChunkBusterMain implements Listener {

	private static ItemStack is = new ItemStack(Material.BEACON);
	
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		ItemMeta meta = e.getItemInHand().getItemMeta();
		System.out.println("recieved event");
		if (e.getBlock().getType().equals(Material.BEACON) && 
				meta.getDisplayName().equals(ChunkBusterMain.getChunkBuster().getItemMeta().getDisplayName()) &&
					meta.getLore().equals(ChunkBusterMain.getChunkBuster().getItemMeta().getLore())) {
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
	
	
	public static ItemStack getChunkBuster() {
		ItemMeta im = ChunkBusterMain.is.getItemMeta();
		List<String> lore = new ArrayList<String>();
		im.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Chunk Buster");
		lore.add(ChatColor.GRAY + "place this block inside of a chunk to have the entire chunk removed");
		im.setLore(lore);
		is.setItemMeta(im);
		return is; 
	}

}
