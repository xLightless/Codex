package org.codex.enchants.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.FactionsMain;
import org.codex.factions.Glow;

import net.md_5.bungee.api.ChatColor;

public class CollectionChest extends CustomItem<EntityDeathEvent>{

	public CollectionChest() {
		super(Material.CHEST, CustomItemType.COLLECTION_CHEST);
		
	}

	@Override
	public ItemStack createItemStack() {
		ItemStack is = new ItemStack(this.getMaterial());
		ItemMeta im = is.getItemMeta();
		im.addEnchant(new Glow(40), 1, true);
		im.setDisplayName(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Collection Chest");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Embued with the power to fix all items in your inventory");
		lore.add(ChatColor.GRAY + "Right click in order to activate this divine item");

		this.setEnergyCost(0);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public boolean isItem(ItemStack is) {
		ItemMeta im = is.getItemMeta();
		ItemStack cis = this.getItemStack();
		ItemMeta cim = cis.getItemMeta();
		return (im.hasDisplayName() ? im.getDisplayName().equals(cim.getDisplayName()) : false) 
				&& (im.hasLore() ? im.getLore().equals(cim.getLore()) : false);
	}

	@EventHandler
	@Override
	public void onActivation(EntityDeathEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override 
	public void blockPlace(BlockPlaceEvent e) {
		Chest c = (Chest) e.getBlock();
		FactionsMain.addChest(c);
		e.getPlayer().sendMessage(ChatColor.RED + "You have created a collection chest");
	}
	
	@Override
	public void blockBreak(BlockBreakEvent e) {
		Chest c = (Chest) e.getBlock();
		FactionsMain.removeChest(c);
		e.getPlayer().sendMessage(ChatColor.RED + "You have removed a collection chest");
	}
	
	

}
