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
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class ChunkBusterMain implements Listener {

	private static ItemStack is = new ItemStack(Material.BEACON);

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (!e.getItemInHand().hasItemMeta())
			return;
		ItemMeta meta = e.getItemInHand().getItemMeta();
		if (!meta.hasLore())
			return;
		if (e.getBlock().getType().equals(Material.BEACON)
				&& meta.getDisplayName().equals(ChunkBusterMain.getChunkBuster().getItemMeta().getDisplayName())
				&& meta.getLore().equals(ChunkBusterMain.getChunkBuster().getItemMeta().getLore())) {
			Block b = e.getBlock();
			boolean temp = false;
			try {
				if (!(FactionsMain.getChunkOwner(b.getX() / 16, b.getZ() / 16) == null
						|| FactionsMain.getChunkOwner(b.getX() / 16, b.getZ() / 16)
								.equals(FactionsMain.getPlayerFaction(e.getPlayer().getUniqueId())))) {
					temp = true;
				}
			} catch (Throwable e1) {
				if (!(FactionsMain.getChunkOwner(b.getX() / 16, b.getZ() / 16) == null)) {
					temp = true;
				}
			}
			if (temp) {
				e.getPlayer().sendMessage(ChatColor.RED + "That land is claimed");
				return;
			}
			int startx = (b.getX() >> 4) * 16;
			int endx = startx + 16;
			int endz = ((b.getZ() >> 4) * 16) + 16;
			int endy = b.getY();
			for (; startx < endx; startx++) {
				for (int startz = (b.getZ() >> 4) * 16; startz < endz; startz++) {
					for (int starty = 1; starty < endy; starty++) {

						b.getWorld().getBlockAt(startx, starty, startz).setType(Material.AIR, false);

					}
				}
			}
			e.setCancelled(true);
			if (e.getPlayer().getItemInHand().getAmount() == 1)
				e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			else
				e.getPlayer()
						.setItemInHand(ChunkBusterMain.getChunkBuster(e.getPlayer().getItemInHand().getAmount() - 1));
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

	public static ItemStack getChunkBuster(int i) {
		ItemStack is = new ItemStack(Material.BEACON, i);
		ItemMeta im = ChunkBusterMain.is.getItemMeta();
		List<String> lore = new ArrayList<String>();
		im.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Chunk Buster");
		lore.add(ChatColor.GRAY + "place this block inside of a chunk to have the entire chunk removed");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

}
