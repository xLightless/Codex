package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.enchants.armorsets.EnderArmorSet;
import org.codex.enchants.armorsets.NetherArmorSet;
import org.codex.enchants.armorsets.PhantomArmorSet;
import org.codex.enchants.books.ArmorType;

import net.md_5.bungee.api.ChatColor;

public class GiveArmorSet implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player))return false;
		if(!(sender.hasPermission("Enchanter.GiveSet") || sender.isOp())) {
			sender.sendMessage(ChatColor.RED + "You do not have proper permission to use this command");
			return true;
		}
		Player p = (Player) sender;
		if(arg3.length == 2) {
			switch(arg3[0].toLowerCase()) {
			case "nether" :
				NetherArmorSet nas = new NetherArmorSet();
				p.getInventory().addItem(nas.getItemStack(ArmorType.value(arg3[1].toUpperCase())));
				break;
			case "ender":
				EnderArmorSet eas = new EnderArmorSet();
				p.getInventory().addItem(eas.getItemStack(ArmorType.value(arg3[1].toUpperCase())));
				break;
			case "phantom":
				PhantomArmorSet pas = new PhantomArmorSet();
				p.getInventory().addItem(pas.getItemStack(ArmorType.value(arg3[1].toUpperCase())));
			}
		}else {
			p.sendMessage(ChatColor.RED + "Please enter in this format /giveset [SET] [ARMOR TYPE]");
		}
		return true;
	}

}
