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

public class Tank extends Book implements Listener {

	private static ItemStack is = new ItemStack(Material.BOOK);
	private static ItemMeta im = is.getItemMeta();
	private static List<String> lore = new ArrayList<>();
	private static final String c = Book.MAJESTIC;
	private static final ChatColor g = ChatColor.GRAY;
	private static final HashMap<Player, Vector2D<Boolean, Integer>> map = new HashMap<>();

	public Tank() {
		super(is, im, lore, 5, BookType.MAJESTIC_BOOK, "Tank ", c + "Tank", new ArrayList<>());
		List<String> lore = new ArrayList<>();
		lore.add(g + "Reduced all incoming AXE damage");
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setLore(lore);
		im.setDisplayName(c + getBookName() + super.getRandomLevel(4));
		is.setItemMeta(im);
		this.setItemStack(is);
		this.setItemMeta(im);
		this.setLore(lore);
		List<Material> m = new ArrayList<>();
		m.add(Material.DIAMOND_CHESTPLATE);
		m.add(Material.IRON_CHESTPLATE);
		this.setApplicableItems(m);
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
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if (!map.containsKey(p))
				return;
			if (!map.get(p).getVectorOne())
				return;
			if (super.getWeaponType(d.getInventory().getItemInHand().getType()) != WeaponType.AXE)
				return;
			int level = map.get(p).getVectorTwo();
			double dmg = e.getDamage();
			switch (level) {
			case 1:
				e.setDamage(dmg / 1.25);
				break;
			case 2:
				e.setDamage(dmg / 1.30);
				break;
			case 3:
				e.setDamage(dmg / 1.35);
				break;
			case 4:
				e.setDamage(dmg / 1.35);
				break;
			default:
				e.setDamage(dmg / 1.5);
				break;
			}
			return;
		}

	}

}
