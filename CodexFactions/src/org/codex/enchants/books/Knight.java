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
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class Knight extends Book implements Listener{

	private static List<String> lore = new ArrayList<>();
	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static final String red = BookType.MYSTICAL_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector2D<Boolean, Integer>> map = new HashMap<>();
	
	public Knight() {
		 super(is, im, lore, 4, BookType.MYSTICAL_BOOK, "Knight", red + "Knight", new ArrayList<>());
		 List<String> lore = new ArrayList<>();
		 lore.add(gy + "Increases all OUTGOING sword damage");
		 lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		 lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		 lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		 im.setLore(lore);
		 im.setDisplayName(red + getBookName() + " " + super.getRandomLevel(3));
		 is.setItemMeta(im);
		 this.setItemStack(is);
		 this.setItemMeta(im);
		 this.setLore(lore);
		 List<Material> lm = new ArrayList<>();
		 lm.add(Material.DIAMOND_SWORD);
		 this.setApplicableItems(lm);
	}
	
	


	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {	
		map.put(p, new Vector2D<>(true, Integer.parseInt(level)));	
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		map.put(p, new Vector2D<>(false, Integer.parseInt(level)));
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if(!map.containsKey(p))return;
			if(!map.get(p).getVectorOne())return;
			if(super.getWeaponType(p.getInventory().getItemInHand().getType()) != WeaponType.SWORD)return;
			int level = map.get(p).getVectorTwo();
			double dmg = e.getDamage();
			switch(level) {
		
			case 1 :
				e.setDamage(dmg * 1.05);
				break;
			case 2 :
				e.setDamage(dmg * 1.07);
				break;
			case 3 :
				e.setDamage(dmg * 1.10);
				break;
			default :
				e.setDamage(dmg * 1 + (0.5 * (level + 1)));
				break;
			}
			return;
		}
	}
	


}
