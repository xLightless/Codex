package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Springs extends Book{

	private static List<String> lore = new ArrayList<>();
	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static final String bl = BookType.MAJESTIC_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	
	public Springs() { 
		 super(is, im, lore, 4, BookType.MAJESTIC_BOOK, "Springs ", bl + "Springs", new ArrayList<>());
		 List<String> lore = new ArrayList<>();
		 lore.add(gy + "When applied to boots, the player gets extra jump boost.");
		 lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		 lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		 lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		 im.setLore(lore);
		 im.setDisplayName(bl + getBookName() + super.getRandomLevel(3));
		 is.setItemMeta(im);
		 List<Material> m = new ArrayList<>();
		 m.add(Material.DIAMOND_BOOTS);
		 m.add(Material.IRON_BOOTS);
		 m.add(Material.GOLD_BOOTS);
		 this.setItemMeta(im);
		 this.setItemStack(is);
		 this.setLore(lore);
		 this.setApplicableItems(m);
	}
	
	
	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addPotionEffect(PotionEffectType.JUMP, p, level);
		
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		super.removePotionEffect(PotionEffectType.JUMP, p, level);
	}



}
