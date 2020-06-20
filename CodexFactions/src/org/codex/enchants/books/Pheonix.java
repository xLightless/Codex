package org.codex.enchants.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.codex.factions.FactionsMain;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;



public class Pheonix extends EnergyBook implements Listener{

	
	protected static List<String> lore = new ArrayList<>();
	protected static ItemStack is = new ItemStack(Material.BOOK);
	protected static ItemMeta im = is.getItemMeta();
	private static final String da = BookType.ENERGY_BOOK.getChatColor();
	private static final ChatColor g = ChatColor.GOLD;
	private static final ChatColor gy = ChatColor.GRAY;
	private static HashMap<Player, Vector2D<Integer, Boolean>> a = new HashMap<>();
	
	
	public Pheonix() {
		super(is, im, lore, 6, BookType.ENERGY_BOOK, "Pheonix", da + "Pheonix", new ArrayList<>());
		this.setEnergyPerUse(20 * 100);
		List<String> lore = new ArrayList<>();
		lore.add(gy + "If you die when this enchant is applied,");
		lore.add(gy + "you have a chance to be revived and live");
		lore.add(g + "Energy Cost : " + this.getEnergyPerUse());
		lore.add(ChatColor.GREEN + "Success Rate : " + super.getRandomSuccessChance());
		lore.add(ChatColor.RED + "Destroy Rate : " + super.getRandomDestroyChance());
		lore.add(ChatColor.BLACK + "" + super.getRandomNumberLore());
		im.setLore(lore);
		im.setDisplayName(da + getBookName() + super.getRandomLevel(3));
		is.setItemMeta(getItemMeta());
		this.setItemMeta(im);
		this.setItemStack(is);
		this.setLore(lore);
		List<Material> lm = new ArrayList<>();
		lm.add(Material.DIAMOND_HELMET);
		this.setApplicableItems(lm);
	}
	
	@Override
	protected void onActivation(Player p, String level, NonStackableItemType t) {
		a.put(p, new Vector2D<Integer, Boolean>(Integer.parseInt(level), true));
	}

	@Override
	protected void onDeactivation(Player p, String level, NonStackableItemType t) {
		a.put(p, new Vector2D<Integer, Boolean>(Integer.parseInt(level), false));
	}	
	

	@EventHandler
	public void onPlayerDeath(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player))return;
		Player p = (Player) e.getEntity();
		
		if(p.getHealth() <= 4) {
		if(!a.containsKey(p))return;
		if(!a.get(p).getVectorTwo())return;
		Random rand = new Random();
		int r1 = rand.nextInt(9);
		if(a.get(p).getVectorOne() >= r1) {
			Vector2D<Boolean, ItemStack> v = super.use(this, p.getInventory().getHelmet());
			if(v.getVectorOne()) {
			p.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "You have Pheonixed");
			PotionEffect pe = new PotionEffect(PotionEffectType.ABSORPTION, 30 * a.get(p).getVectorOne(), 3, true);
			p.addPotionEffect(pe);
			super.addPotionEffect(PotionEffectType.REGENERATION, p, "3");
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {
	            @Override
	            public void run() {
	            	Book.removePotionEffect(PotionEffectType.REGENERATION, p, "3");
	            }
	        },  30L * a.get(p).getVectorOne());
			p.getInventory().setHelmet(v.getVectorTwo());
			p.updateInventory();
			}else {
				p.sendMessage(ChatColor.DARK_PURPLE + "You do not have enough energy to pheonix");
			}
		}
		}
		
	}
	

}
