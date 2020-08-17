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

import net.md_5.bungee.api.ChatColor;
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
			if (testReach(p, le, reach, this.MINIMUM_REACH)) {
				if (le instanceof Player) {
					Player p1 = (Player) le;
					if (p.canSee(p1)) {
						le.sendMessage(ChatColor.RED + "Test");
					}
				} else {
					le.sendMessage(ChatColor.YELLOW + "Test");
				}
			}
		}

	}

	public boolean testReach(Player p, LivingEntity p1, double reach, double minReach) {

		Location ploc = p.getEyeLocation();
		Location p1loc = p1.getLocation();

		float pYaw = ploc.getYaw();
		float pPitch = ploc.getPitch();

		if (ploc.distance(p1loc) > reach && ploc.distance(p1loc) < minReach)
			return false;

		double pX = ploc.getX();
		double pY = ploc.getY();
		double pZ = ploc.getZ();
		double p1X = p1loc.getX();
		double p1Y = p1loc.getY();
		double p1Z = p1loc.getZ();
		double k = p1X - pX; // 3 - 0
		double t = p1Z - pZ; // 3 - 0
		double h = p1Y - pY; // 130 - 129

		double angleYaw = Math.atan(k / t);
		double anglePitch = Math.atan(h / Math.hypot(k, t));

		if (!(plusOrMinus(pYaw, 20, angleYaw) && plusOrMinus(pPitch, 40, anglePitch)))
			return false;

		double pPitchRatio = Math.tan(pPitch); // h / sqrt(k^2 + t^2);
		AxisAlignedBB aabb = ((CraftLivingEntity) p1).getHandle().getBoundingBox();

		double distance = ploc.distance(p1loc);
		double distance2D = Math.cos(pPitch) * distance;
		double hpX = (Math.cos(pYaw) * distance2D) + pX;
		double hpZ = (Math.sin(pYaw) * distance2D) + pZ;
		double hpY = (pPitchRatio * (Math.hypot(hpX - pX, hpZ - pZ))) + pY;
		Vec3D vec = new Vec3D(hpX, hpY, hpZ);
		return aabb.a(vec);

	}

	private boolean plusOrMinus(float i, int j, double k) {
		if (i + j <= k && i - j >= k)
			return true;
		return false;
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

}
