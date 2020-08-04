package org.codex.factions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class CustomCannoningMain implements CommandExecutor, Listener {

	private static ItemStack is = new ItemStack(Material.RED_SANDSTONE);
	private static ItemStack noIS = new ItemStack(Material.AIR);	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;
		
		if ((sender instanceof Player) && (sender.isOp()) || (sender.hasPermission("CustomCannoning.use"))) {
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(ChatColor.RED + "Block 36");
			List<String>lore = new ArrayList<String>();
			lore.add("Place this block as if you were using a Block 36 Sand Comp! Does not require pistons");
			isMeta.setLore(lore);
			is.setItemMeta(isMeta);
			
			if ((player.getItemInHand() == null || player.getItemInHand() == noIS)) {
				return false;
			}
			
			PlayerInventory inv = player.getInventory();
			inv.addItem(is);
		}
		
		return false;
	}
	
	
	
	@EventHandler
	public void onBlockEdit (BlockPlaceEvent e) {
		
		Location location = e.getBlock().getLocation();
		Location particleLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5);
		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (block.getType() == Material.RED_SANDSTONE) {
			player.sendMessage(ChatColor.RED +"You have placed an Entity Holder");
			block.setType(Material.PISTON_MOVING_PIECE);
			
			int i;
	        for (i = 0; i < 100; i++) {
	            block.getLocation().getWorld().playEffect(particleLocation, Effect.FLAME, 10);
	        }
	        
	//    if (placedAgainst == Material.WATER || placedAgainst == Material.STATIONARY_WATER || placedAgainst == Material.LAVA || placedAgainst == Material.STATIONARY_LAVA) {
	//    	e.setCancelled(true);
	//    	player.sendMessage(ChatColor.RED + "You cannot place this Block near Liquids!");
	//   }
			
			
		}
		
	}
	// Doesnt work pls fix 
	public void onBlockPlaceNear (PlayerInteractEvent e2) {

		Player player = e2.getPlayer();
		
		if (e2.getClickedBlock().getType() == Material.WATER || e2.getClickedBlock().getType() == Material.LAVA || e2.getClickedBlock().getType() == Material.STATIONARY_LAVA || e2.getClickedBlock().getType() == Material.STATIONARY_WATER) {
			e2.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You cannot place this Block near Liquids!");
		}
		
	}
	
	
	
	
}
