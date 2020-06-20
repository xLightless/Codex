package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Ferrite extends Book implements Listener{

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final ChatColor gy = ChatColor.GRAY;
	private static final String bl = BookType.MAJESTIC_BOOK.getChatColor();
	private static HashMap<Player, Boolean> a = new HashMap<>();
	private static HashMap<Player, Integer> b = new HashMap<>();
	
	public Ferrite() {
		super(is, im, lore, 5, BookType.MAJESTIC_BOOK, "Ferrite", bl + "Ferrite", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "This book has the ability to double ");
		lore.add(gy + "the output of a broken block.");
		lore.add(gy + "Higher levels add the chance for");
		lore.add(gy + "even more to be added");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setDisplayName(bl + getBookName() + " " + super.getRandomLevel(4));
		im.setLore(lore);
		is.setItemMeta(im);
		this.setItemStack(is);
		this.setItemMeta(im);
		this.setLore(lore);
		List<Material> l = new ArrayList<>();
		l.add(Material.DIAMOND_PICKAXE);
		l.add(Material.IRON_PICKAXE);
		this.setApplicableItems(l);
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
			double fortuneMultiplier = 1;
			if(p.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)){
				int fortuneLevel = p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
				fortuneMultiplier += fortuneLevel * 0.1;
			}
			Random r = new Random();
			for(ItemStack is : bl.getDrops()) {
			for(int x = 0; x <= r.nextInt((int) ((b.get(p) * 1.5 * fortuneMultiplier) + 1)); x++)p.getInventory().addItem(is);
			}
			bl.setType(Material.AIR);
		}
	}



	private boolean isAutominable(Block bl) {

		switch(bl.getType()) {
		
		case LAPIS_ORE:
			return true;
		case REDSTONE_ORE:
			return true;
		case COAL_ORE:
			return true;
		default:
			return false;
		
		}

		
	}

}
