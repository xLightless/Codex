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
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class WeedWacker extends Book implements Listener{

	
	
	private static List<String> lore = new ArrayList<>();
	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static final String green = BookType.RARE_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector2D<Boolean, Integer>> b = new HashMap<>();
	
	public WeedWacker() {
		 super(is, im, lore, 3, BookType.RARE_BOOK, "Weed Wacker ", green + "Weed Wacker", new ArrayList<>());
		 List<String> lore = new ArrayList<>();
		 lore.add(gy + "Ability to block a thorns attack");
		 lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		 lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		 lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		 im.setLore(lore);
		 im.setDisplayName(green + getBookName() + super.getRandomLevel(2));
		 is.setItemMeta(im);
			List<Material> lm = new ArrayList<>();
			lm.add(Material.DIAMOND_LEGGINGS);
			lm.add(Material.IRON_LEGGINGS);
			lm.add(Material.GOLD_LEGGINGS);
		this.setItemStack(is);
		this.setItemMeta(im);
		this.setLore(lore);
		this.setApplicableItems(lm);
	}
	
	
	




	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		b.put(p, new Vector2D<Boolean, Integer>(true, Integer.parseInt(level)));
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		b.remove(p);
	}


	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if(e.getCause().equals(DamageCause.THORNS) && b.containsKey(e.getEntity())) {
			Player p = (Player) e.getEntity(); //we do not have to check if it is instance of a player because it is in the hashmap which has already been tested.
			int level = b.get(p).getVectorTwo();
			double damage = e.getDamage();
			damage = 0.5 - ((level) * 0.25);
			if(damage <= 0) {
				damage=0;
				p.sendMessage(ChatColor.YELLOW + "protected against thorns");
				e.setDamage(damage);
				e.setCancelled(true);
				return;
			}
			p.sendMessage(ChatColor.YELLOW + "protected against thorns");
			e.setDamage(damage);
		}
	}

}
