package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class Hardened extends Book implements Listener{

	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String p = BookType.LEGENDARY_BOOK.getChatColor();
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector2D<Integer, ArmorType>> a = new HashMap<>();
	
	public static HashMap<Player, Vector2D<Integer, ArmorType>> a(){
		return a;
	}


	public Hardened() {
		super(is, im, lore, 4, BookType.LEGENDARY_BOOK, "Hardened", p + "Hardened", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(gy + "Unbreaking on crack");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setDisplayName(p + getBookName() + super.getRandomLevel(3));
		im.setLore(lore);
		is.setItemMeta(im);
		this.setItemMeta(im);
		this.setItemStack(is);
		this.setLore(lore);
		List<Material> lm = new ArrayList<>();
		lm.add(Material.DIAMOND_HELMET);
		lm.add(Material.DIAMOND_CHESTPLATE);
		lm.add(Material.DIAMOND_LEGGINGS);
		lm.add(Material.DIAMOND_BOOTS);
		lm.add(Material.IRON_HELMET);
		lm.add(Material.IRON_CHESTPLATE);
		lm.add(Material.IRON_LEGGINGS);
		lm.add(Material.IRON_BOOTS);
		this.setApplicableItems(lm);
	}
	

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		if(t instanceof ArmorType) {
		ArmorType a = (ArmorType) t;
		Vector2D<Integer, ArmorType> v = new Vector2D<>(Integer.parseInt(level), a);
		Hardened.a.put(p, v);
		}
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		a.remove(p);
		
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e) {
		Player p = e.getPlayer();
		ItemStack is = e.getItem();
		if(a.containsKey(p) && ArmorListener.isArmor(is.getType())) {
			ArmorType at = ArmorListener.getArmorType(is.getType());
			if(a.get(p).getVectorTwo().equals(at)) {
				Random r = new Random();
				int rInt = r.nextInt(100);
				if(rInt <= a.get(p).getVectorOne() * 11){
					e.setDamage(0);
					e.setCancelled(true);
				}
			}
			
		}
		
		
	}
	
	
	

	
	
}
