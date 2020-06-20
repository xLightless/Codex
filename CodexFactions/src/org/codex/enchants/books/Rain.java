package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class Rain extends Book{

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String c = Book.QUANTUM;
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector2D<Boolean, Integer>> map = new HashMap<>();
	private JavaPlugin plugin;
	public static HashMap<Player, Boolean> isRaining = new HashMap<>();
	
	public Rain(JavaPlugin plugin) {
		super(is, im, lore, 4, BookType.QUANTUM_BOOK, "Rain ", c + "Rain", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "Everytime you are attacked there is a chance that natures rain will");
		lore.add(gy + "Start to fall down and deal massive damage to people nearby");
		 lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		 lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		 lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		 im.setLore(lore);
		 im.setDisplayName(c + getBookName() + super.getRandomLevel(2));
		 is.setItemMeta(im);
		 List<Material> m = new ArrayList<Material>();
		m.add(Material.DIAMOND_HELMET);
		m.add(Material.IRON_HELMET);
		m.add(Material.GOLD_HELMET);
		this.setApplicableItems(m);
		this.setItemMeta(im);
		this.setItemStack(is);
		this.setLore(lore);
		 this.plugin = plugin;
	}


	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		map.put(p, new Vector2D<Boolean, Integer>(true, Integer.parseInt(level)));
		
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		map.put(p, new Vector2D<Boolean, Integer>(false, Integer.parseInt(level)));
		
	}


	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getCause() == DamageCause.CUSTOM)return;
			if(e.getCause().equals(DamageCause.THORNS) || e.getCause().equals(DamageCause.STARVATION))return;
			Player p = (Player) e.getEntity();
			if(!map.containsKey(p))return;
			if(!map.get(p).getVectorOne())return;
			int level = map.get(p).getVectorTwo();
			int radius = level * 10;
			Random r = new Random();
			int rand = r.nextInt(400);
			if(level >= rand) {
				if(!isRaining.containsKey(p))isRaining.put(p, false);
				if(!isRaining.get(p)) {
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "You have caused it to rain");
			for(Player o: Bukkit.getOnlinePlayers()) {
				Location ol = o.getLocation();
				Location pl = p.getLocation();
				if(o.getName() != p.getName()) {
				if((radius * radius) >= (square(ol.getBlockX() - pl.getBlockX())) + (square(ol.getBlockZ() - pl.getBlockZ()))) {
					
					o.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "acidic rain is falling");
					rain(o, level, p);
					}
				}
			}
			}
			}
		}
	}
	
	private void rain(Player o, int level, Player p) {
		isRaining.put(p, true);
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		int time = 0;
		switch(level) {
		case 1 : 
			time = 6;
			break;
		case 2 : 
			time = 8;
			break;
		default: 
		time = 20;
		break;
		}
		
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	Rain.isRaining.put(p, false);
            }
        }, time * 20L);
		for(int x = 0; x < time; x++) {
	    scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	
            	RainFallingEvent e = new RainFallingEvent(o, p, 2.5D * level);
            	 Bukkit.getServer().getPluginManager().callEvent(e);
            	 if(!e.isCancelled()) {
                o.damage(e.getDamage());
                if (e.getDamage() != 0)o.sendMessage(ChatColor.GOLD + "rained");
            	 }
            }
        }, x * 20L);
	    
		}
    }
		
	

	private int square(int y) {
		return y*y;
	}
	

}
