package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.enchants.items.CustomItem;
import org.codex.enchants.items.CustomItemType;
import org.codex.enchants.items.TrenchPickaxe;

import net.md_5.bungee.api.ChatColor;

public class GiveItem implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(p.hasPermission("CustomEnchant.GiveItem") || p.isOp()) {
				if(args.length >= 1) {
					try {
						CustomItemType type = CustomItemType.valueOf(args[0].toUpperCase());
						CustomItem<?> i;
						if(!type.equals(CustomItemType.TRENCH_PICKAXE))i = type.getItem();
						else i = new TrenchPickaxe(Integer.parseInt(args[1]));
						p.getInventory().addItem(i.getItemStack());
						p.sendMessage(ChatColor.GREEN + "You have been given " + i.getItemStack().getItemMeta().getDisplayName());
						return true;
					}catch(Exception e) {
						p.sendMessage(ChatColor.RED + "That is not a valid item");
						return false;
					}
				}
			}else {
				p.sendMessage(ChatColor.RED + "Sorry, you do not have proper permission");
				return true;
			}
			
		}
		
		return false;
	}
	
}
