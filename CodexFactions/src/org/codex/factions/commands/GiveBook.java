package org.codex.factions.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.Book;
import org.codex.enchants.books.EnchantType;

import net.md_5.bungee.api.ChatColor;

public class GiveBook implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender.hasPermission("Enchanter.GiveBook") || sender.isOp())) {
			sender.sendMessage(ChatColor.RED + "You do not have proper permission to use this command");
			return true;
		}
		if(!(sender instanceof Player))return false;
		if(args.length == 2) {
			Book b = EnchantType.getEnchantClass(args[0].toUpperCase());
			Player p = (Player) sender;
			ItemStack is = b.getItemStack(Integer.parseInt(args[1]));
			ItemMeta im = is.getItemMeta();
			List<String> lore= im.getLore();
			int x = 0;
			for(String s : lore) {
				if(s.contains("Success")) {
					lore.set(x, ChatColor.GREEN + "Success Rate : " + 100);
					x++;
				}
				else if (s.contains("Destroy")) {
					lore.set(x, ChatColor.RED + "Destroy Rate : " + 0);
					x++;
				}else x++;
			}
		
			im.setLore(lore);
			is.setItemMeta(im);
			p.getInventory().addItem(is);
			p.sendMessage(ChatColor.GREEN + "you have been given " + b.getItemStack().getItemMeta().getDisplayName());
			return true;
		}else if(args.length == 3){
			Book b = EnchantType.getEnchantClass(args[0].toUpperCase());
			Player p = (Player) sender;
			ItemStack is = b.getItemStack(Integer.parseInt(args[1]));
			ItemMeta im = is.getItemMeta();
			List<String> lore= im.getLore();
			int x = 0;
			for(String s : lore) {
				if(s.contains("Success")) {
					lore.set(x, ChatColor.GREEN + "Success Rate : " + args[2]);
					x++;
				}
				else if (s.contains("Destroy")) {
					lore.set(x, ChatColor.RED + "Destroy Rate : " + 0);
					x++;
				}else x++;
			}
		
			im.setLore(lore);
			is.setItemMeta(im);
			p.getInventory().addItem(is);
			p.sendMessage(ChatColor.GREEN + "you have been given " + b.getItemStack().getItemMeta().getDisplayName());
			return true;
		}else if(args.length == 4){
			Book b = EnchantType.getEnchantClass(args[0].toUpperCase());
			Player p = (Player) sender;
			ItemStack is = b.getItemStack(Integer.parseInt(args[1]));
			ItemMeta im = is.getItemMeta();
			List<String> lore= im.getLore();
			int x = 0;
			for(String s : lore) {
				if(s.contains("Success")) {
					lore.set(x, ChatColor.GREEN + "Success Rate : " + args[2]);
					x++;
				}
				else if (s.contains("Destroy")) {
					lore.set(x, ChatColor.RED + "Destroy Rate : " + args[3]);
					x++;
				}else x++;
			}
		
			im.setLore(lore);
			is.setItemMeta(im);
			p.getInventory().addItem(is);
			p.sendMessage(ChatColor.GREEN + "you have been given " + b.getItemStack().getItemMeta().getDisplayName());
			return true;
		}else {
			sender.sendMessage("Sorry, Your going to have to retype the command - Format - /givebook [BOOK] [LEVEL]");
		}
		
		
		return false;
	}

}
