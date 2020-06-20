package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Gears extends Book{

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dp = BookType.MYSTICAL_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	
	public Gears() {
		 super(is, im, lore, 5, BookType.LEGENDARY_BOOK, "Gears ", dp + "Gears", new ArrayList<>());
		 List<String> lore = new ArrayList<>();
		 lore.add(gy + "When applied to boots, the player gets extra speed.");
		 lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		 lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		 lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		 im.setLore(lore);
		 im.setDisplayName(dp + getBookName() + super.getRandomLevel(3));
		 is.setItemMeta(im);
		 this.setItemStack(is);
		 this.setItemMeta(im);
		 this.setLore(lore);
		 List<Material> lm = new ArrayList<>();
		 lm.add(Material.DIAMOND_BOOTS);
		 lm.add(Material.IRON_BOOTS);
		 this.setApplicableItems(lm);
	}
	




	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addPotionEffect(PotionEffectType.SPEED, p, level);
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		super.forceRemoveEffect(PotionEffectType.SPEED, p);
		
	}

}
