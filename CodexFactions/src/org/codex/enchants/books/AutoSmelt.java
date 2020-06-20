package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class AutoSmelt extends Book implements Listener{

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String gy = BookType.COMMON_BOOK.getChatColor();
	private static HashMap<Player, Boolean> a = new HashMap<>();
	private static HashMap<Player, Integer> b = new HashMap<>();
	
	public AutoSmelt() {
		super(is, im, lore, 4, BookType.COMMON_BOOK, "AutoSmelt", gy + "AutoSmelt", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "This book has the ability to automaticly ");
		lore.add(gy + "smelt any ore when you break it. ");
		lore.add(gy + "Higher levels add the chance for");
		lore.add(gy + "multiple ingots to drop");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setDisplayName(gy + getBookName() + " " + super.getRandomLevel(3));
		im.setLore(lore);
		is.setItemMeta(im);
		this.setItemMeta(im);
		this.setItemStack(is);
		this.setLore(lore);
		List<Material> m = this.getApplicableItems();
		m.add(Material.DIAMOND_PICKAXE);
		m.add(Material.DIAMOND_SPADE);
		m.add(Material.IRON_PICKAXE);
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
	public void onBlockMine(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block bl = e.getBlock();
		if(!(a.containsKey(p) && b.containsKey(p)))return;
		if(a.get(p) && isAutominable(bl)) {
			e.setCancelled(true);
			
			Random r = new Random();
			for(int x = 0; x <= r.nextInt(b.get(p) + 1); x++)p.getInventory().addItem(new ItemStack(getIngot(bl)));
			bl.setType(Material.AIR);
		}
	}

	private Material getIngot(Block bl) {
		switch(bl.getType()) {
		case IRON_ORE:
			return Material.IRON_INGOT;
		case GOLD_ORE:
			return Material.GOLD_INGOT;
		default : 
			return Material.AIR;
		}
		
	}
	
	

	private boolean isAutominable(Block bl) {

		switch(bl.getType()) {
		
		case IRON_ORE:
			return true;
		case GOLD_ORE:
			return true;
		default:
			return false;
		
		}

		
	}

}

