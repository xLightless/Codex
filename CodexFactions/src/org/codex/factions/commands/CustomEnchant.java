package org.codex.factions.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomEnchant implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if((arg0.isOp() || arg0.hasPermission("CustomEnchants.all")) && arg0 instanceof Player) {
			Player p = (Player) arg0;
			ItemStack is = p.getItemInHand();
			if(is == null || is.getType().equals(Material.AIR))return false;
			p.setItemInHand(org.codex.enchants.armorsets.ProtectionFixListener.getFixedProtection(is, Integer.parseInt(arg3[0])));
		}
		return false;
	}

}
