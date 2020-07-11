package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class SafeGuard extends Book implements Listener{

	private static List<String> lore = new ArrayList<>();
	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static String c = BookType.LEGENDARY_BOOK.getChatColor();
	private static ChatColor g = ChatColor.GRAY;
	private static HashMap<Player, HashMap<ArmorType, Integer>>  map = new HashMap<>();
	
	public SafeGuard() {
		super(is, im, lore, 0,	BookType.LEGENDARY_BOOK, "Safe Guard", c + "Safe Guard", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(g + "This enchant has the ability to completely block a hit. This enchant is stackable");
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
		m.add(Material.DIAMOND_CHESTPLATE);
		m.add(Material.DIAMOND_LEGGINGS);
		m.add(Material.DIAMOND_BOOTS);
		m.add(Material.DIAMOND_HELMET);
		this.setApplicableItems(m);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		HashMap<ArmorType, Integer> map = new HashMap<>();
		map.put((ArmorType) a, Integer.parseInt(level));
		SafeGuard.map.put(p, map);
		
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		HashMap<ArmorType, Integer> amap = map.containsKey(p) ? map.get(p) : null;
		amap.put((ArmorType) a, 0);
		map.put(p, amap); 
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player))return;
		Player p = (Player) e.getEntity();
		if(!map.containsKey(p))return;
		int stackedlevel = 0;
		if(map.get(p).containsKey(ArmorType.HELMET))stackedlevel += map.get(p).get(ArmorType.HELMET);
		if(map.get(p).containsKey(ArmorType.CHESTPLATE))stackedlevel += map.get(p).get(ArmorType.CHESTPLATE);
		if(map.get(p).containsKey(ArmorType.LEGGINGS))stackedlevel += map.get(p).get(ArmorType.LEGGINGS);
		if(map.get(p).containsKey(ArmorType.BOOTS))stackedlevel += map.get(p).get(ArmorType.BOOTS);
		Random r = new Random();
		if(stackedlevel/2 >= r.nextInt(100)) {
			e.setDamage(0);
		}
		
		
	}

}
