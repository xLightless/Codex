package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class Umbrella extends EnergyBook implements Listener{


	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String da = BookType.ENERGY_BOOK.getChatColor();
	private static final ChatColor g = ChatColor.GOLD;
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector2D<Integer, Boolean>> a = new HashMap<>();
	
	
	public Umbrella() {
		super(is, im, Book.of(gy + "Blocks the rain enchant",
				g + "Energy Cost : " + 500), 6, BookType.ENERGY_BOOK, "Umbrella", da + "Umbrella", Book.of(Material.DIAMOND_HELMET), 3, 500);
	}
	

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		a.put(p, new Vector2D<Integer, Boolean>(Integer.parseInt(level), true));
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		a.remove(p);
	}	
	

	@EventHandler
	public void onRain(RainFallingEvent e) {
		Player p = e.getPlayer();
		if(a.containsKey(p)) {
		int level = a.get(p).getVectorOne();
		Random r = new Random();
		int rand = r.nextInt(20);
		if(rand <= level * 7) {
		double damage = e.getDamage() / (level + 1);
		if(level >= 3) damage = 0.3/level;
		boolean t = this.use(this, p.getInventory().getHelmet()).getVectorOne();
		if(t) {
		e.setCancelled(true);
		e.setDamage(damage);
		}else {
		return;
		}
		}
		}
	}

}
