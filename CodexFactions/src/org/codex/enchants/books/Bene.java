package org.codex.enchants.books;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Bene extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.DRAGON_EGG);
	private static final ChatColor g = ChatColor.GRAY;
	private static Set<Player> map = new HashSet<>();
	
	public Bene() {
		super(is, is.getItemMeta(), Book.of(g + "The benevolent codex gods have granted Benos's wish"), 0,
				BookType.QUANTUM_BOOK, "Bene", ChatColor.ITALIC + "" + BookType.QUANTUM_BOOK.getChatColor() + "Bene",
				Book.of(Material.DIAMOND_HELMET), 1);
	}

	@Override
	protected void onActivation(Player p, String level, NonStackableItemType a) {
		map.add(p);
		p.setMaxHealth(1);

	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType a) {
		map.remove(p);
		p.setMaxHealth(20);

	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		 if(map.contains(e.getEntity()) && e.getEntity().getUniqueId().equals(UUID.fromString("19cdcb26aaaf490095be61fdafc0f0f2")))e.setCancelled(true);
	}

}
