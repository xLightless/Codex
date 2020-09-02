package org.codex.enchants.books;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Vec3D;

public class ReachModifier implements Listener {

	private static Map<Player, Double> reachMap = new HashMap<>();

	public static final double MINIMUM_REACH = 3.2;
	
	public ReachModifier() {
		
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!reachMap.containsKey(e.getPlayer()))
		return;
		Player p = e.getPlayer();
		double reach = reachMap.get(p);
		for (Entity entity : p.getNearbyEntities(reach, reach, reach)) {
			if (!(entity instanceof LivingEntity))
				continue;
			LivingEntity le = (LivingEntity) entity;
			if (!p.hasLineOfSight(le))
				continue;
			if (testReach(p, le, reach, ReachModifier.MINIMUM_REACH)) {
				if (le instanceof Player) {
					Player p1 = (Player) le;
					if (p.canSee(p1)) {
						le.damage(1);
					}
				} else {
					le.damage(1);
				}
			}
		}

	}

	public boolean testReach(Player p, LivingEntity p1, double reach, double minReach) {

		Location ploc = p.getLocation();
		Location p1loc = p1.getLocation();

		double pYaw = ((ploc.getYaw() * -1) * Math.PI) / 180;
		double pPitch = ((-1 * ploc.getPitch()) * Math.PI) / 180;

		if (ploc.distance(p1loc) > reach || ploc.distance(p1loc) < minReach)
			return false;

		double pX = ploc.getX();
		double pY = ploc.getY();
		double pZ = ploc.getZ();
		
		double pPitchRatio = Math.tan( pPitch); // h / sqrt(k^2 + t^2);
		AxisAlignedBB aabb = ((CraftLivingEntity) p1).getHandle().getBoundingBox();

		double distance = distance(ploc, p1loc);
		double distance2D = Math.cos(pPitch) * distance;
		double hpX = (Math.sin(pYaw) * distance2D) + pX;
		double hpZ = (Math.cos(pYaw) * distance2D) + pZ;
		double hpY = (pPitchRatio * (distance2D)) + pY + 1;
		Vec3D vec = new Vec3D(hpX, hpY, hpZ);
		p.sendMessage(aabb.a(vec) + "");
		return aabb.a(vec);

	}



	public static void addReach(Player p, double added) {
		reachMap.put(p, reachMap.containsKey(p) ? reachMap.get(p) + added : added);
	}

	public static void setReach(Player p, double d) {
		reachMap.put(p, d);
	}

	public static double getReach(Player p) {
		return reachMap.containsKey(p) ? reachMap.get(p) : 0;
	}
	
	public static void remove(Player p) {
		reachMap.remove(p);
	}
	
	private double distance(Location l, Location l2) {
		return Math.sqrt(sq(l.getX() - l2.getX()) + sq(l.getY() - l2.getY()) + sq(l.getZ() - l2.getZ()));
	}
	
	private double sq(double d) {
		return d * d;
	}
	

}
