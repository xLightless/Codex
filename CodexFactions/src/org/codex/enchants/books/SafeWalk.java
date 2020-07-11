package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class SafeWalk extends Book implements Listener{

	private static List<String> lore = new ArrayList<>();
	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.LEGENDARY_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, Integer> map = new HashMap<>();
	private static HashMap<Player, Long> decay = new HashMap<>();
	
	public SafeWalk() {
		super(is, im, lore, 0, BookType.LEGENDARY_BOOK, "Safe Walk", c + "Safe Walk", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(g
				+ "When walking over an edge not in warzone, a block will spawn underneath you for a certain amount of time.");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setLore(lore);
		im.setDisplayName(c + this.getBookName() + " " + this.getRandomLevel(3));
		is.setItemMeta(im);
		this.setItemMeta(im);
		this.setItemStack(is);
		this.setLore(lore);
		List<Material> m = new ArrayList<>();
		m.add(Material.CHAINMAIL_BOOTS);
		m.add(Material.LEATHER_BOOTS);
		m.add(Material.GOLD_BOOTS);
		m.add(Material.IRON_BOOTS);
		m.add(Material.DIAMOND_BOOTS);
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
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerWalk(PlayerMoveEvent e) {
		if(!map.containsKey(e.getPlayer()))return;
		Player p = e.getPlayer();
		int level = map.get(p);
		Location loc = p.getLocation();
		int y = loc.getBlockY();
		Block b = loc.getWorld().getBlockAt(loc.getBlockX(), y-1, loc.getBlockZ());
		if(!b.getType().equals(Material.AIR))return;
		Random r = new Random();
		int rand = r.nextInt(3) % 2;
		long l = System.currentTimeMillis();
		byte data = (byte) (rand == 0 ? 3 : rand == 1 ? 9 : 11); 
		if(!decay.containsKey(p)) {
			b.setType(Material.STAINED_GLASS);
			b.setData(data);
			decay.put(p, l + 100);
		}else if(l < decay.get(p)) 
			return;
		else {
			b.setType(Material.STAINED_GLASS);
			b.setData(data);
			decay.put(p, l + 100);
		}
		
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {

			@Override
			public void run() {
				b.setType(Material.AIR);
				
			}
			
		}, 8 * level);
		
	}
	
	
	

}
