package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class AdvancedFeatherFalling extends Book implements Listener {

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final ChatColor gy = ChatColor.GRAY;
	private static final String gr = BookType.RARE_BOOK.getChatColor();
	private static HashMap<Player, Boolean> a = new HashMap<>();
	private static HashMap<Player, Integer> b = new HashMap<>();
	
	public AdvancedFeatherFalling() {
		super(is, im, lore, 4, BookType.RARE_BOOK, "Advanced Feather Falling", 
				gr + "Advanced Feather Falling", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "This book protects you from fall damage and gives you health back");
		lore.add(gy + "For example, if you take 10 hearts and have");
		lore.add(gy + "Advanced Feather falling 5 you get back 1 heart");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setLore(lore);
		im.setDisplayName(gr + getBookName() + " " + super.getRandomLevel(5));
		is.setItemMeta(im);
		this.setLore(lore);
		this.setItemMeta(im);
		this.setItemStack(is);
		List<Material> m = new ArrayList<>();
		m.add(Material.DIAMOND_BOOTS);
		m.add(Material.IRON_BOOTS);
		m.add(Material.GOLD_BOOTS);
		this.setApplicableItems(m);
		
	}
	
	


	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		a.put(p, true);
		b.put(p, Integer.parseInt(level));
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
	
		a.put(p, false);
		b.remove(p);
	}
	

	@EventHandler
	public void onFallDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			DamageCause c = e.getCause();
			
			if(!(a.containsKey(p) && b.containsKey(p)))return;
			if(c.equals(DamageCause.FALL) && a.get(p)) {
				double d = e.getDamage();
				d *= (1 - (Double.parseDouble("0." + b.get(p)) * 2));
				double h = p.getHealth();
				h +=  0.5 * e.getDamage() * (Double.parseDouble("0." + b.get(p)) + .5);
				
				e.setDamage(d);
				try {
					p.setHealth(h);
					}catch(IllegalArgumentException e2) {
					p.setHealth(p.getMaxHealth());
					} 
				
			}
		}
	}


}
