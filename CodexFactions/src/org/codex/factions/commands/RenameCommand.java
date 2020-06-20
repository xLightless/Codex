package org.codex.factions.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;





public class RenameCommand implements CommandExecutor {

	public RenameCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(!(arg0.hasPermission("Enchanter.Rename") || arg0.isOp())) {
			arg0.sendMessage(ChatColor.RED + "You do not have proper permission to use this command");
			return true;
		}
		if(arg0 instanceof Player) {
			Player p = (Player) arg0;
			ItemStack is = p.getInventory().getItemInHand();
			if(is != null && is.getType() != Material.AIR ) {
				if(arg3.length >= 1) {
					String y = "";
					for(int z = 0; z < arg3.length; z++) {
						y += arg3[z] + " ";
					}
					String x = y.replaceAll("&", "§").replaceAll("\\[", "{").replaceAll("\\]", "}");
					
					ItemMeta im = is.getItemMeta();
					int level;
					
					if(!is.hasItemMeta()) {
						im.setDisplayName(ChatColor.RESET + x);
						is.setItemMeta(im);
						p.getInventory().setItemInHand(is);
						p.sendMessage(ChatColor.GREEN + "You have changed the name of your item to " + ChatColor.RESET + "" + x);
						return true;
					}else if(!is.getItemMeta().hasDisplayName()) {
						im.setDisplayName(ChatColor.RESET + x);
						is.setItemMeta(im);
						p.getInventory().setItemInHand(is);
						p.sendMessage(ChatColor.GREEN + "You have changed the name of your item to " + ChatColor.RESET + "" + x);
						return true;
					}
					if(im.getDisplayName().contains("[")) {
						level = Integer.parseInt(ChatColor.stripColor(im.getDisplayName().split("\\[")[1].split("\\]")[0]));
						x +=  ChatColor.GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + level + ChatColor.GRAY + "]";
						
					}else level = 0;
					im.setDisplayName(ChatColor.RESET + x);
					is.setItemMeta(im);
					p.getInventory().setItemInHand(is);
					p.sendMessage(ChatColor.GREEN + "You have changed the name of your item to " + ChatColor.RESET + "" + x);
					return true;
				}
			}
		}
		return false;
	}

}
