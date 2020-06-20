package org.codex.enchants.books;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public abstract class EnergyBook extends Book {

	public EnergyBook(ItemStack is, ItemMeta im, List<String> lore, int minArmorValue, BookType b, String bookName,
			String appliedBookName, List<Material> applicableItems) {
		super(is, im, lore, minArmorValue, b, bookName, appliedBookName, applicableItems);
	}

	private int energyPerUse = 0;
	private HashMap<Player, HashMap<ArmorType, Integer>> playerEnergyLevels = new HashMap<>();
	
	protected Vector2D<Boolean, ItemStack> use(EnergyBook b, ItemStack is) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
 		int slot;
 		try {
 		slot = BookManager.getEnergySlot(lore);
 		}catch(NullPointerException e) {
 			return new Vector2D<>(false, is);
 		}
		int energy = Integer.parseInt(lore.get(slot).split(" : ")[1]);		
 		energy -= b.getEnergyPerUse();
	 	if(energy <= 0)return new Vector2D<>(false, is);
	 	lore.set(slot, ChatColor.DARK_AQUA + "Energy : " + energy);
 		im.setLore(lore);
 		is.setItemMeta(im);
		return new Vector2D<>(true, is);
	}



	public int getEnergyPerUse() {
		return energyPerUse;
	}
	
	public void setEnergyPerUse(int energyPerUse) {
		this.energyPerUse = energyPerUse;
	}


	public HashMap<Player, HashMap<ArmorType, Integer>> getPlayerEnergyLevels() {
		return playerEnergyLevels;
	}

	public void setPlayerEnergyLevels(HashMap<Player, HashMap<ArmorType, Integer>> playerEnergyLevels) {
		this.playerEnergyLevels = playerEnergyLevels;
	}
	

}
