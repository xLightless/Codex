package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Crit extends Book {


	private static List<String> lore = new ArrayList<>();
	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.LEGENDARY_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();
	
	public Crit() {
		super(is, im, lore, 4, BookType.LEGENDARY_BOOK, "Crit", c + "Crit", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(g + "When attacking a play there is a slight chance to push the player in the air.");
		lore.add(g + "This allows you to get a large crit streak on the player");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setLore(lore);
		im.setDisplayName(c + this.getBookName() + " " + this.getRandomLevel(4));
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
				if(super.random(l/10D, 40)) {
					Entity e1 = e.getEntity();
					Location loc = e1.getLocation();
						e1.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY() + (1 * (l * 0.5)), loc.getZ()));
				}
			}
		}
	}


}
