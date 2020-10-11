package org.codex.factions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.codex.enchants.books.Book;

import net.md_5.bungee.api.ChatColor;

public class FactionsListener implements Listener {

	private static Map<EntityType, Double> grindable = new HashMap<>();
	private static List<DamageCause> dmgs = Book.of(DamageCause.FALL, DamageCause.DROWNING, DamageCause.CONTACT,
			DamageCause.FIRE, DamageCause.FIRE_TICK, DamageCause.LAVA, DamageCause.LIGHTNING, DamageCause.MAGIC,
			DamageCause.SUFFOCATION, DamageCause.WITHER);
	

	static {
		grindable.put(EntityType.CAVE_SPIDER, 10D);
		grindable.put(EntityType.COW, 100D);
		grindable.put(EntityType.CREEPER, 100D);
		grindable.put(EntityType.ENDERMAN, 100D);
		grindable.put(EntityType.GHAST, 5D);
		grindable.put(EntityType.IRON_GOLEM, 10D);
		grindable.put(EntityType.PIG, 10D);
		grindable.put(EntityType.SHEEP, 10D);
		grindable.put(EntityType.SKELETON, 10D);
		grindable.put(EntityType.SPIDER, 10D);
		grindable.put(EntityType.ZOMBIE, 10D);
	}

	@EventHandler
	public void onFactionMemberAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p1 = (Player) e.getDamager();
			Player p2 = (Player) e.getEntity();
			try {

				FactionPlayer facp1 = FactionsMain.getPlayer(p1.getUniqueId());
				FactionPlayer facp2 = FactionsMain.getPlayer(p2.getUniqueId());

				FactionObject fac1 = facp1.getFaction();
				FactionObject fac2 = facp2.getFaction();
				if (fac1.getRelationshipWith(fac2).isFriendly()) {
					e.setCancelled(true);
					p1.sendMessage(ChatColor.AQUA + "You cannot damage " + ChatColor.RESET + facp2.getTag() + " "
							+ p2.getName());
				}
			} catch (Throwable e1) {
			}

		}
	}

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(SpawnReason.SPAWNER)) {
			LivingEntity e1 = e.getEntity();
			for (Entity es : e1.getLocation().getChunk().getEntities()) {
				if (e1.getType().equals(es.getType())) {
					String shortName = ChatColor.GOLD + "" + ChatColor.BOLD + "" + es.getType().toString() + " : ";
					String origName = es.getCustomName() == null ? " " : es.getCustomName();
					String mobName = origName.contains(shortName)
							? shortName + (Integer.parseInt(origName.split(shortName)[1]) + 1)
							: shortName + "2";
					es.setCustomName(mobName);
					es.setCustomNameVisible(true);
					e.setCancelled(true);
					return;
				}
			}

			e1.setCustomName(ChatColor.GOLD + "" + ChatColor.BOLD + "" + e1.getType().toString() + " : 1");
			e1.setCustomNameVisible(true);

		}
	}

	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		Entity e1 = e.getEntity();
		String shortName = ChatColor.GOLD + "" + ChatColor.BOLD + "" + e1.getType().toString() + " : ";
		String origName = e1.getCustomName() == null ? " " : e1.getCustomName();
		int i = origName.contains(shortName) ? (Integer.parseInt(origName.split(shortName)[1])) : 0;
		int amount = FactionsListener.getNewMobStack(e1.getType(), i, e1);
		String mobName = shortName + amount;
		for (ItemStack drop : e.getDrops()) {
			drop.setAmount(drop.getAmount() * (i - amount));
			e1.getWorld().dropItem(e1.getLocation(), drop);
		}

		if (i != 0) {
			Entity ne = e1.getWorld().spawnEntity(e1.getLocation(), e1.getType());
			ne.setCustomName(mobName);
			ne.setCustomNameVisible(true);
		}
	}

	private static int getNewMobStack(EntityType type, int i, Entity e) {
		if (dmgs.contains(e.getLastDamageCause().getCause()) && grindable.containsKey(type))
			return (int) (i - (int) (i * (FactionsListener.grindable.get(type) / 100D)) <= 10 ? i - 1
					: i - (int) (i * (FactionsListener.grindable.get(type) / 100D)));

		return i - 1;
	}

}
