package org.codex.enchants.energy;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.ArmorListener;
import org.codex.enchants.books.BookManager;
import org.codex.enchants.items.CustomItem;
import org.codex.factions.Glow;
import org.codex.factions.SaveYaml;

import net.md_5.bungee.api.ChatColor;

public class Energy implements Listener{
	private ItemStack eis = new ItemStack(Material.CLAY_BALL);
	private ItemMeta eim = eis.getItemMeta();
	private static SaveYaml<EnergyHarvester> yml = new SaveYaml<>("harvesters.yml", "blocks", new TreeMap<String, EnergyHarvester>());
	private static ItemStack harvester = new ItemStack(Material.FURNACE);


	public Energy() {
		ItemMeta im = harvester.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Place this block in the world");
		lore.add(ChatColor.GRAY + "and it will farm the Galactic Energy nearby");
		im.setDisplayName("Energy Harvester");
		im.setLore(lore);
		
		harvester.setItemMeta(im);
	}
	
	public ItemStack getEnergyItemStack(int amount) {
		List<String> l = new ArrayList<>(); 
		l.add(ChatColor.BLUE + "This item is filled with Galactic Energy;");
		l.add(ChatColor.BLUE + "it will start to decay");
		l.add(ChatColor.BLUE + "if not added to an energy storage center (ESC)");
		l.add(ChatColor.BLACK + UUID.randomUUID().toString());
		eim.setLore(l);
		eim.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Energy : " + amount);
		eim.addEnchant(new Glow(40), 1, true);
		eis.setItemMeta(eim);

		return eis;
	}
	
	@EventHandler
	public void onEnergyApply(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(p.getGameMode() == GameMode.CREATIVE)return;
		ItemStack ci = e.getCurrentItem();
		ItemStack cu = e.getCursor();
		if(ci == null)return;
		if(ArmorListener.isArmor(ci.getType()) || BookManager.checkWeapon(ci) || CustomItem.isCustomItem(ci)) {
			if(cu == null)return;
			if(cu.getItemMeta() == null)return; 
			else if(cu.getItemMeta().getDisplayName() == null)return;
			else if(!cu.getItemMeta().getDisplayName().contains("Energy : "))return;
			List<String> l;
			if(ci.getItemMeta().hasLore()) {
			l = ci.getItemMeta().getLore();
			}else {
			l = new ArrayList<>();	
			}
			String ls = null;
			int x = 0;
			for(String s : l) {
				x++;
				if(s.contains("Energy : ")) {
					ls = s.split(" : ")[1];
					break;
				}
			}
			if(ls != null) {
				ItemMeta im = ci.getItemMeta();
				List<String> lore;
				if(im.hasLore()) {
					lore = im.getLore();
				}else {
					lore = new ArrayList<>();
				}
				int amount = Integer.parseInt(cu.getItemMeta().getDisplayName().split(" : ")[1]);
				int oldamount = Integer.parseInt(ls);
				lore.set(x - 1, ChatColor.DARK_AQUA + "Energy : " + (amount + oldamount));
				ItemStack nis = ci;
				im.setLore(lore);
				nis.setItemMeta(im);
				p.getInventory().remove(ci);
				p.setItemOnCursor(new ItemStack(Material.AIR));
				p.getInventory().setItem(e.getSlot(), nis);
			}else {
				ItemMeta im = ci.getItemMeta();
				List<String> lore;
				if(im.hasLore()) {
					lore = im.getLore();
				}else {
					lore = new ArrayList<>();
				}
				int amount = Integer.parseInt(cu.getItemMeta().getDisplayName().split(" : ")[1]);
				
				lore.add(ChatColor.DARK_AQUA + "Energy : " + amount);
				ItemStack nis = ci;
				im.setLore(lore);
				nis.setItemMeta(im);
				p.getInventory().remove(ci);
				p.setItemOnCursor(new ItemStack(Material.AIR));
				p.getInventory().setItem(e.getSlot(), nis);
			}
	}
	
}
	
	@EventHandler
	public void onEnergyCombine(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(p.getGameMode() == GameMode.CREATIVE)return;
		ItemStack ci = e.getCurrentItem();
		ItemStack cu = e.getCursor();
		if(ci == null || cu == null)return;
		if(cu.getItemMeta() == null || ci.getItemMeta() == null)return; 
		else if(cu.getItemMeta().getDisplayName() == null || ci.getItemMeta().getDisplayName() == null)return;
		else if(!cu.getItemMeta().getDisplayName().contains("Energy : ") || !ci.getItemMeta().getDisplayName().contains("Energy : "))return;
		int a1 = Integer.parseInt(cu.getItemMeta().getDisplayName().split(" : ")[1]);
		int a2 = Integer.parseInt(ci.getItemMeta().getDisplayName().split(" : ")[1]);
		int fa = a1 + a2;
		p.getInventory().remove(ci);
		p.setItemOnCursor(new ItemStack(Material.AIR));
		p.getInventory().setItem(e.getSlot(), this.getEnergyItemStack(fa));
	}
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer(); 
		
			Block b = e.getBlock();
			Location l = b.getLocation();
			ItemStack is = e.getItemInHand();
			ItemMeta im = is.getItemMeta();
			ItemMeta harvesterMeta = harvester.getItemMeta();
			
			if(!im.hasLore())return;
			if(im.getLore().equals(harvesterMeta.getLore()) && im.getDisplayName().contains(harvesterMeta.getDisplayName())) {
				for(EnergyHarvesterType type : EnergyHarvesterType.values()) {
					if(type.getDisplayName().equals(im.getDisplayName())) {
						
						Map<String, EnergyHarvester> map = yml.getMap();			
						EnergyHarvester harvester = new EnergyHarvester(p.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), type.getDisplayName(), 0D);
						if(!map.containsValue(harvester)) {
						Location loc = new Location(l.getWorld(), l.getX() + 0.5, l.getY() - 1, l.getZ() + 0.5);
						ArmorStand as = (ArmorStand) harvester.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
						harvester.setUUID(as.getUniqueId());
						as.setVisible(false);
						as.setCustomName(harvester.getType().getDisplayName());
						as.setCustomNameVisible(true);
						as.setGravity(false);
						p.sendMessage(ChatColor.YELLOW + "You have placed a harvester");
						yml.add(harvester.getId().getId() + "", harvester);
						yml.save();
						}else {
							p.sendMessage(ChatColor.RED + "There already is a harvester there!!!");
						}
					}
				}
			}
			
			
			
		}
	     
	
	@EventHandler
	public void onBlockDestroy(BlockBreakEvent e) {
		Block b = e.getBlock();
		if(b.getType().equals(Material.FURNACE)) {
			if(yml.getMap() != null) {
			if(yml.getMap().values() != null) {
			for(int i = 0; i < yml.getMap().values().size(); i++) {
				EnergyHarvester harvester = (EnergyHarvester) yml.getMap().values().toArray()[i];
				if(harvester.getxPos() == b.getX() && harvester.getyPos() == b.getY() && harvester.getzPos() == b.getZ()) {
					e.getPlayer().sendMessage(ChatColor.RED + "you have broken a harvester");
					yml.getMap().remove(harvester.getId().getId() + "");
					Bukkit.getScheduler().cancelTask(harvester.getTaskID());
					for(Entity entity: b.getWorld().getNearbyEntities(b.getLocation(), 1, 1, 1)) {
						if(harvester.getUUID() == entity.getUniqueId()) {
							entity.remove();
						}
					}
					e.setCancelled(true);
					b.setType(Material.AIR);
					if((int) harvester.getEnergy() > 0) {
					harvester.getWorld().dropItemNaturally(b.getLocation(), this.getEnergyItemStack((int) harvester.getEnergy()));
					}else {
						e.getPlayer().sendMessage(ChatColor.RED + "You have broken a harvester that did not have enough energy to be able to be extracted");
					}
					yml.save();
				}
			}
			}
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerInteractWithHarvester(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block b = e.getClickedBlock();
			if(b.getType().equals(Material.FURNACE)) {
				if(yml.getMap() != null) {
					if(yml.getMap().values() != null) {
					for(int i = 0; i < yml.getMap().values().size(); i++) {
						EnergyHarvester harvester = (EnergyHarvester) yml.getMap().values().toArray()[i];
						if(harvester.getxPos() == b.getX() && harvester.getyPos() == b.getY() && harvester.getzPos() == b.getZ()) {
							p.sendMessage(ChatColor.GREEN + "This harvester contains " + (Math.floor(harvester.getEnergy() * 100) / 100) + " galactic energy");
							e.setCancelled(true);
						}
					}
					}
			}
		}
		}
	}
	
	
	
	public static SaveYaml<EnergyHarvester> getYml() {
		return yml;
	}
	
	
	public static void giveEnergyHarvester(Player p, EnergyHarvesterType type) {
		ItemMeta harvesterMeta = harvester.getItemMeta();
		harvesterMeta.setDisplayName(type.getDisplayName());
		harvesterMeta.addEnchant(new Glow(40), 1, true);
		harvester.setItemMeta(harvesterMeta);
		p.getInventory().addItem(harvester);
	}

	public static void removeArmorStands() {
		for(World w: Bukkit.getWorlds()) {
			for(Entity e: w.getEntities()) {
				if(e.getType().equals(EntityType.ARMOR_STAND)) {
					for(EnergyHarvester h: yml.getMap().values()) {
						if(h.getUUID().equals(e.getUniqueId())) {
							Bukkit.getLogger().info("ArmorStand " + e.getName() + " has been removed!");
							e.remove();
						}
					}
				}
			}
			
			
		}
		
		
	}
	
	public static void loadArmorStands() {

		for(EnergyHarvester h: yml.getMap().values()) {
			Location l = new Location(h.getWorld(), h.getxPos(), h.getyPos(), h.getzPos());
			Location loc = new Location(l.getWorld(), l.getX() + 0.5, l.getY() - 1, l.getZ() + 0.5);
			ArmorStand as = (ArmorStand) h.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
			h.setUUID(as.getUniqueId());
			//as.setInvulnerable(true);
			as.setVisible(false);
			as.setCustomName(h.getType().getDisplayName());
			as.setCustomNameVisible(true);
			as.setGravity(false);
			yml.save();
		}
	}
	

}
