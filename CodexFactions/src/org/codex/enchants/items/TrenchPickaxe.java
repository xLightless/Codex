package org.codex.enchants.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.Glow;

import net.md_5.bungee.api.ChatColor;

public class TrenchPickaxe extends CustomItem<BlockBreakEvent> {

	private int number;
	
	public TrenchPickaxe(int number) {
		super(Material.DIAMOND_PICKAXE, CustomItemType.TRENCH_PICKAXE);
		this.number = number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	

	@Override
	public ItemStack createItemStack() {
		ItemStack is = new ItemStack(this.getMaterial());
		ItemMeta im = is.getItemMeta();
		im.addEnchant(new Glow(40), 1, true);
		im.setDisplayName(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Trench Pickaxe " + number);
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "After years of marinating in the cold depths of the dark realm");
		lore.add(ChatColor.GRAY + "This item has gained the ability to mine out " + number + " blocks around your");
		lore.add(ChatColor.GRAY + "original spot.");
		this.setEnergyCost(700);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public boolean isItem(ItemStack is) {
		if(!is.hasItemMeta())return false;
		if(!is.getItemMeta().hasLore())return false;
		if(is.getItemMeta().getDisplayName().contains("Trench Pickaxe ")) {
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			if(lore.get(0).equals(this.getItemStack().getItemMeta().getLore().get(0)))return true;
		}
		return false;
	}
	
	@EventHandler
	@Override
	public void onActivation(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(!this.isItem(p.getItemInHand()))return;
		destroyRegion(e.getBlock().getLocation(), Integer.parseInt(p.getItemInHand().getItemMeta().getDisplayName().split(" ")[2]) - 1, p.getWorld());
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
