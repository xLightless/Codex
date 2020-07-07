package org.codex.obsidiandestroyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class TNTHandler implements Listener{

	private final int ObsidianHealth;
	private final int MaxObsidianDestroyed;
	private final List<Material> ExtraMaterials;
	private HashMap<Block, Integer> a = new HashMap<>();
	private final Material Clicker;
	private final Material ProtectionBlock;
	private Random r = new Random();
	
	public TNTHandler(int obsidianHealth, int maxObby, List<String> lm, Material clicker, Material protectionBlock) {
		this.ObsidianHealth = obsidianHealth;
		this.MaxObsidianDestroyed = maxObby;
		List<Material> em = new ArrayList<>();
		for(String s: lm) {
			em.add(Material.getMaterial(s));
		}
		this.ExtraMaterials = em;
		this.Clicker = clicker;
		this.ProtectionBlock = protectionBlock;
	}

	public int getObsidianHealth() {
		return ObsidianHealth;
	}
	
	public List<Material> getExtraMaterials() {
		return ExtraMaterials;
	}

	public int getMaxObsidianDestroyed() {
		return MaxObsidianDestroyed;
	}

	public Material getClicker() {
		return Clicker;
	}

	@EventHandler
	public void onTNTExplode(EntityExplodeEvent e) {
		int destroy = this.getMaxObsidianDestroyed();
		if(e.getEntityType().equals(EntityType.PRIMED_TNT)) {
			Entity entity = e.getEntity();
			Location l = entity.getLocation();
			if(l.getBlock().getType().equals(Material.WATER) || l.getBlock().getType().equals(Material.LAVA) 
				||  l.getBlock().getType().equals(Material.STATIONARY_WATER) || l.getBlock().getType().equals(Material.STATIONARY_LAVA)) return;
						
			
				List<Block> bs = this.getAdjacentMaterial(l, this.getExtraMaterials(), this.getMaxObsidianDestroyed());
				if(bs.size() <= 0)return;
				for(int i = 0; i <= bs.size() - 1; i++) {
						Block b = bs.get(i);
						 
							if(isBlocked(l, b.getLocation())) {
								return;
							}else if(destroy != 0){
								if(!a.containsKey(b)) {
									a.put(b, this.getObsidianHealth() - 1);
								}else if(a.get(b) - 1 != 0){
									a.put(b, a.get(b) - 1);
									
								}else {
									a.remove(b);
									b.breakNaturally();
								}
							
								destroy--;
							}else if(destroy <= 0){
								return;
							}
				}
			
				
	
				
			}
	}
	
	@EventHandler
	public void onBlockHit(PlayerInteractEvent e) {
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
		ItemStack is = e.getItem();
		if(is == null) return;
		if(is.getType().equals(Clicker)) {
			if(a.containsKey(e.getClickedBlock())) {
				e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "" + a.get(e.getClickedBlock()) + "/" + this.getObsidianHealth());
			}
			else {
				for(Material m : this.getExtraMaterials()) {
					if(e.getClickedBlock().getType().equals(m)) {
			e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "" + this.getObsidianHealth() + "/" + this.getObsidianHealth());
					}
				}
			}
		}
	}
	
	

	private boolean isBlocked(Location tntLoc, Location bLoc) {
		if(tntLoc.distance(bLoc) == 1)return false;
		int tntX = tntLoc.getBlockX();
		int tntY = tntLoc.getBlockY();
		int tntZ = tntLoc.getBlockZ();
		int bX = bLoc.getBlockX();
		int bY = bLoc.getBlockY();
		int bZ = bLoc.getBlockZ();
		int wX = tntX;
		int wY = tntY;
		int wZ = tntZ;
		boolean north = tntX - bX > 0 ? true : false;
		boolean south = tntX - bX < 0 ? true : false;
		boolean east = tntZ - bZ < 0 ? true : false;
		boolean west = tntZ - bZ > 0 ? true : false;
		boolean up = tntY - bY < 0 ? true : false;
		boolean down = tntY - bY > 0 ? true : false;
		
		if(north)wX--;
		else if (south)wX++;
		if(east)wZ++;
		else if(west)wZ--;
		if(up)wY++;
		if(down)wY--;
		
		if(!this.ProtectionBlock.equals(Material.AIR))
		for(int y = 0; y < bLoc.getBlockY(); y++) {
			if(bLoc.getWorld().getBlockAt(new Location(bLoc.getWorld(), bX, y, bZ)).getType().equals(this.ProtectionBlock)) {
				return true;
			}
			
		}
		
		
		return bLoc.getWorld().getBlockAt(new Location(bLoc.getWorld(), wX, wY, wZ)).getType().equals(Material.WATER) ? true : 
			bLoc.getWorld().getBlockAt(new Location(bLoc.getWorld(), wX, wY, wZ)).getType().equals(Material.LAVA) ? true : 
				bLoc.getWorld().getBlockAt(new Location(bLoc.getWorld(), wX, wY, wZ)).getType().equals(Material.STATIONARY_WATER) ? true :
					bLoc.getWorld().getBlockAt(new Location(bLoc.getWorld(), wX, wY, wZ)).getType().equals(Material.STATIONARY_LAVA) ? true : false; 
				
	}
	
	
	private List<Block> getAdjacentMaterial(Location l, List<Material> list, int max) {
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Block b = l.getChunk().getBlock(x, y, z);
		List<Block> bs = new ArrayList<>();
		List<Block> unchecked = new ArrayList<>();
		Block d = b.getRelative(BlockFace.DOWN);
		Block u = b.getRelative(BlockFace.UP);
		Block e = b.getRelative(BlockFace.EAST);
		Block w = b.getRelative(BlockFace.WEST);
		Block n = b.getRelative(BlockFace.NORTH);
		Block s = b.getRelative(BlockFace.SOUTH);
		
		unchecked.add(d);
		unchecked.add(u);
		unchecked.add(e);
		unchecked.add(w);
		unchecked.add(n);
		unchecked.add(s);
		for(Material m : list) {
		if(m.equals(d.getType()))bs.add(d); 
		if(m.equals(u.getType()))bs.add(u);
		if(m.equals(e.getType()))bs.add(e);
		if(m.equals(w.getType()))bs.add(w);
		if(m.equals(s.getType()))bs.add(s);
		if(m.equals(n.getType()))bs.add(n);
		}
		
		if(max <= 0)return bs;
		
		if(bs.size() <= 0) {
			int rand = r.nextInt(unchecked.size());
			bs.addAll(this.getAdjacentMaterial(unchecked.get(rand).getLocation(), list, max - 1));
		}else if(bs.size() < max && unchecked.size() != 0 && bs.size() != 0){
			int rand = r.nextInt(unchecked.size());
			bs.addAll(this.getAdjacentMaterial(unchecked.get(rand).getLocation(), list, max - bs.size()));
		}else {
		
			return bs;
		}
		
		return bs;
		
	}
	
	
	

	
	
	
	
	
}
