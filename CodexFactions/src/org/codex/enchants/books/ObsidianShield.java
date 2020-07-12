package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class ObsidianShield extends Book{


	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dp = BookType.RARE_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	
	public ObsidianShield() {
		 super(is, im, lore, 6, BookType.RARE_BOOK, "Obsidian Shield", dp + "Obsidian Shield", new ArrayList<>());
		 List<String> lore = new ArrayList<>();
		 lore.add(gy + "When applied, you will be immune to fire");
		 lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		 lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		 lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		 im.setLore(lore);
		 im.setDisplayName(dp + getBookName() + " " + super.getRandomLevel(1));
		 is.setItemMeta(im);
		 this.setItemMeta(im);
		 this.setItemStack(is);
		 this.setLore(lore);
		 List<Material> lm = new ArrayList<>();
		 lm.add(Material.DIAMOND_LEGGINGS);
		 lm.add(Material.IRON_LEGGINGS);
		 this.setApplicableItems(lm);
	}
	
	
	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addPotionEffect(PotionEffectType.FIRE_RESISTANCE, p, level);	
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		super.removePotionEffect(PotionEffectType.FIRE_RESISTANCE, p, level);
	}

}
