package org.codex.enchants.books;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class Block extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static final ChatColor g = ChatColor.GRAY;
	private static Map<Player, Integer> set = new HashMap<>();
	
	public Block() {
		super(is, is.getItemMeta(),
				Book.of(g + "This enchant has the chance to block attacks",
						g + "it also, at higher levels, may return damage to the sender."),
				0, BookType.MYSTICAL_BOOK, "Block", BookType.MYSTICAL_BOOK.getChatColor() + "Block", Book.of(Material.DIAMOND_SWORD), 8);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		set.put(p, Integer.parseInt(level));
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		set.remove(p);
	}
	
	@EventHandler 
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!set.containsKey(e.getEntity())) return;
			int level = set.get(e.getEntity());
			double damage = e.getDamage();
			if(!(e.getDamager() instanceof LivingEntity))e.setDamage(damage * (1 - (level/20)));
			else {
				LivingEntity le = (LivingEntity) e.getDamager();
				if(super.random(level, 75)){
				e.setDamage(damage * (1 - (level / 10)));
				if(level >= 6)le.damage(0.25 * (level - 6) * damage);
				}
			}
	}

}
