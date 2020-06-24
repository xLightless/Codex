package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Harvest extends Book implements Listener{

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String dr = BookType.MYSTICAL_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();

	public Harvest() {
		super(is, im, lore, 0, BookType.MYSTICAL_BOOK, "Harvest", dr + "Harvest", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "When attacking a player, you have a chance to gain back a portion of the damage given.");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setLore(lore);
		im.setDisplayName(dr + getBookName() + super.getRandomLevel(10));
		is.setItemMeta(im);
		this.setItemMeta(im);
		this.setItemStack(is);
		this.setLore(lore);
		List<Material> m = new ArrayList<>();
		m.add(Material.DIAMOND_SWORD);
		this.setApplicableItems(m);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		map.put(p, Integer.parseInt(level));
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		map.remove(p);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if(map.containsKey(p)) {
				double l = map.get(p);
				if(super.random(l/100D, 40)) {
					double h = p.getHealth();
					h += l/10 > 40 ? 40 : l/10;
					p.setHealth(h);
				}
			}
		}
	}

}