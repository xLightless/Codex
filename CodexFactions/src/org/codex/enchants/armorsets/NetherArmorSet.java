package org.codex.enchants.armorsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.codex.enchants.books.ArmorEquipEvent;
import org.codex.enchants.books.ArmorType;
import org.codex.enchants.books.ArmorUnequipEvent;
import org.codex.factions.FactionsMain;
import org.codex.factions.Vector2D;

import net.md_5.bungee.api.ChatColor;

public class NetherArmorSet extends ArmorSet implements Listener {

	protected static HashMap<Player, HashMap<ArmorType, Boolean>> a = new HashMap<>();
	private static HashMap<Player, Integer> applied = new HashMap<>();
	private static Set<Player> fullSetApplied = new HashSet<>();
	private static Map<Block, Material> blocks = new HashMap<>();

	public HashMap<Player, HashMap<ArmorType, Boolean>> getApplyMap() {
		return a;
	}

	public void setApplyMap(HashMap<Player, HashMap<ArmorType, Boolean>> a) {
		NetherArmorSet.a = a;
	}

	public NetherArmorSet() {
		this.setDefensePoints(40);
		this.setArmor_value(6);
		this.setDurabilityMultiplier(4);
		List<String> lore = new ArrayList<>();
		this.setLeather(true);
		this.setLeatherColor(Color.fromRGB(230, 38, 125));
		lore.add(this.getUAID());
		lore.add(ChatColor.RED + "Nether Armor: The Armor made from the Nether Gods");
		lore.add(ChatColor.RED + "45 Defense Points");
		this.setName(ChatColor.BOLD + "" + ChatColor.RED + "Netheranian ");
		this.setLore(lore);
		this.setmHelmet(Material.LEATHER_HELMET);
		this.setmChestplate(Material.LEATHER_CHESTPLATE);
		this.setmLeggings(Material.LEATHER_LEGGINGS);
		this.setmBoots(Material.LEATHER_BOOTS);
	}

	public ItemStack getItemStack(ArmorType t) {
		return super.createItemStack(t, this, ChatColor.RED);
	}

	@EventHandler
	public void onApply(ArmorEquipEvent e) {
		Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> b = super.getApplyMap(a, e, this);
		a = b.getVectorTwo();
		if (!b.getVectorOne())
			return;
		if (!applied.containsKey(e.getPlayer())) {
			applied.put(e.getPlayer(), 1);
			Bukkit.getPluginManager().callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(),
					ArmorSets.NETHER_ARMOR_SET, applied.get(e.getPlayer())));
		} else {
			applied.put(e.getPlayer(), applied.get(e.getPlayer()) + 1);
			Bukkit.getPluginManager().callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(),
					ArmorSets.NETHER_ARMOR_SET, applied.get(e.getPlayer())));
		}

	}

	@EventHandler
	public void onUnApply(ArmorUnequipEvent e) {
		Vector2D<Boolean, HashMap<Player, HashMap<ArmorType, Boolean>>> b = super.getUnapplyMap(a, e, this);
		a = b.getVectorTwo();
		if (!applied.containsKey(e.getPlayer()))
			return;
		if (b.getVectorOne()) {
			applied.put(e.getPlayer(), applied.get(e.getPlayer()) - 1);
			Bukkit.getPluginManager().callEvent(new ArmorSetChangeEvent(ArmorChangeType.APPLY, e.getPlayer(),
					ArmorSets.NETHER_ARMOR_SET, applied.get(e.getPlayer())));
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setDamage(super.getDamage(e, a, this));
	}

	@EventHandler
	public void onDurabilityLoss(PlayerItemDamageEvent e) {
		super.calculateDurability(e, this, ChatColor.GRAY);
	}

	@Override
	public String getUAID() {
		return ChatColor.BLACK + "Netheranian";
	}

	@Override
	public int getAmountApplied(Player p, ArmorSet a) {
		return applied.get(p);
	}

	@EventHandler
	public void onArmorChange(ArmorSetChangeEvent e) {
		Player p = e.getPlayer();
		if(!e.getSet().equals(ArmorSets.NETHER_ARMOR_SET))return;
		if (e.getAmountApplied() == 4) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have equiped the netheranian armor set");
			p.sendMessage(super.plus + ChatColor.RED + " Fire Tick Damage heals you");
			p.sendMessage(super.plus + ChatColor.RED + " Nether Gods Passive Effect");
			p.sendMessage(super.plus + ChatColor.RED + " Lava Walker III");
			p.sendMessage(super.plus + ChatColor.RED + " OUTGOING 30% PvE damage");
			fullSetApplied.add(p);
		} else if (fullSetApplied.contains(p) && e.getAmountApplied() == 3) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have unequiped the netheranian armor set");
			fullSetApplied.remove(p);
		}
	}

	@EventHandler
	public void onFireDamage(EntityDamageEvent e) {
		if (fullSetApplied.contains(e.getEntity()))
			if (e.getCause().equals(DamageCause.FIRE_TICK)) {
				Player p = (Player) e.getEntity();
				p.damage(e.getDamage() * -1);
				e.setCancelled(true);
			}
	}

	@EventHandler
	public void onLavaWalk(PlayerMoveEvent e) {
		if (!fullSetApplied.contains(e.getPlayer()))
			return;
		if (this.checkLocation(e.getTo(), e.getFrom()))
			return;
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		placeBlocks(loc, 1, 3, Material.STATIONARY_LAVA, Material.STATIONARY_LAVA);
		placeBlocks(loc, 1, 3, Material.LAVA, Material.AIR);
	}

	@SuppressWarnings("deprecation")
	private void placeBlocks(Location loc, int i, int level, Material m, Material m1) {
		Random r = new Random();

		for (int x = loc.getBlockX() - i; x <= i + loc.getBlockX(); x++)
			for (int z = loc.getBlockZ() - i; z <= i + loc.getBlockZ(); z++) {
				Block b = loc.getWorld().getBlockAt(x, loc.getBlockY() - 1, z);

				if (!(b.getType().equals(m)))
					continue;
				int rand = r.nextInt(3) % 3;
				byte data = (byte) (rand == 0 ? 1 : rand == 1 ? 14 : 4);
				b.setType(Material.STAINED_GLASS);
				b.setData(data);
				Runnable r2 = new Runnable() {

					@Override
					public void run() {
						b.setType(m1);
						NetherArmorSet.blocks.remove(b);
					}

				};

				blocks.put(b, m1);
				Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), r2, 8 * level);

			}

	}

	private boolean checkLocation(Location loc1, Location loc2) {
		return loc1.getX() == loc2.getX() ? loc1.getZ() == loc2.getZ() ? true : false : false;
	}

	public static void clearActiveBlocks() {
		for (Block b : blocks.keySet()) {
			b.setType(blocks.get(b));
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if(fullSetApplied.contains(e.getDamager())) {
			super.outPvEDamage(1.3, e, fullSetApplied);
			if(e.getEntity() instanceof Player) {
				Player p = (Player) e.getDamager();
				Entity[] nearbyPlayers = (Entity[]) p.getLocation().getWorld().getNearbyEntities(p.getLocation(), 10, 10, 10).toArray();
				for(Entity e1: nearbyPlayers) {
					try {
						if(FactionsMain.getPlayerFaction(p.getUniqueId()).getRelationshipWith(FactionsMain.getPlayerFaction(e1.getUniqueId())).isFriendly())continue;
					} catch (Throwable e2) {
					}
					if(!(e1 instanceof LivingEntity))continue;
					for(int i = 10; i >= 0; i--)Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {

						@Override
						public void run() {
							LivingEntity le = (LivingEntity) e1;
							le.damage(2);	
						}
						
					}, i * 20);
				}
			}
		
		}
	}

}
