package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class HealthBoost extends Book implements Listener{

	protected static List<String> lore;
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dr = BookType.MYSTICAL_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	
	public HealthBoost() {
		super(is, im, lore, 4, BookType.LEGENDARY_BOOK, "Health Boost", dr + "Health Boost", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "This book has the ability to give you ");
		lore.add(gy + "extra health based on your level ");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setDisplayName(dr + getBookName() + " " + super.getRandomLevel(5));
		im.setLore(lore);
		is.setItemMeta(im);
		this.setItemStack(is);
		this.setItemMeta(im);
		this.setLore(lore);
		List<Material> l = new ArrayList<>();
		l.add(Material.DIAMOND_CHESTPLATE);
		l.add(Material.IRON_CHESTPLATE);
		this.setApplicableItems(l);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		super.addStackPotionEffect(PotionEffectType.HEALTH_BOOST, p, level);
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		PotionEffect p1 = BookManager.getPotionEffect(p.getActivePotionEffects(), PotionEffectType.HEALTH_BOOST);
		if(p1 == null)return;
		p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
		int amp = p1.getAmplifier() - (Integer.parseInt(level));
		if(amp > 0)
		p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, amp, true));
	}
	
	

}
