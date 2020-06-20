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

public class CreeperArmor extends Book implements Listener{

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final ChatColor gy = ChatColor.GRAY;
	private static final String dr = BookType.MYSTICAL_BOOK.getChatColor();
	private static HashMap<Player, Boolean> a = new HashMap<>();
	private static HashMap<Player, Integer> b = new HashMap<>();
	
	public CreeperArmor() {
		super(is, im, lore, 6, BookType.MYSTICAL_BOOK, "Creeper Armor", dr + "Creeper Armor", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "This book stops all TNT damage");
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
		l.add(Material.DIAMOND_LEGGINGS);
		this.setApplicableItems(l);
	}
	
	


	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		a.put(p, true);
		b.put(p, Integer.parseInt(level));
		
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType  t) {
		a.put(p, false);
		b.remove(p);
		
	}
	
	@EventHandler
	public void onPlayerDamager(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
			Player p = (Player) e.getEntity();
		if(e.getCause() == DamageCause.BLOCK_EXPLOSION || e.getCause() == DamageCause.ENTITY_EXPLOSION) {
			if(!(a.containsKey(p) && b.containsKey(p)))return;
			if(a.get(p)) {
				int level = b.get(p);
				double damage = e.getDamage();
				int h = 0;
				if(level == 1) {
					damage *= .5;
				}else if(level == 2) {
					damage *= .25;
				}else if(level == 3) {
					damage *= .05;
				}else if(level == 4) {
					damage *= 0;
				}else if(level == 5) {
					damage *= 0;
					h += (int) (e.getDamage() * 0.1);
				}
				e.setCancelled(true);
				p.damage(damage);
				try {
					p.setHealth(h + p.getHealth());
					}catch(IllegalArgumentException e2) {
					p.setHealth(p.getMaxHealth());
					} 
			}
			
		}
		
		
	}

}
