package org.codex.factions.executors.auction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.ArmorListener;
import org.codex.enchants.books.ItemUnuseEvent;
import org.codex.enchants.books.WeaponEquipMethod;
import org.codex.factions.FactionsMain;
import org.codex.factions.executors.Execute;

import net.md_5.bungee.api.ChatColor;
	

public class Sell implements Execute{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Command Arguments not full, try typing /ah sell PRICE");
			return false;
		}
		int price;
		try {
			price = Integer.parseInt(args[1]);
		}catch(NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + "Invalid argument. Please use /ah sell PRICE");
			return false;
		}
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command is meant for players only. Sorry!");
			return false;
		}
		Player p = (Player) sender;
		ItemStack is = p.getInventory().getItemInHand();
		if(is == null ? true : is.getType().equals(Material.AIR)) {
			p.sendMessage(ChatColor.RED + "Invalid item");
			return false;
		}
		Bukkit.getServer().getPluginManager().callEvent(
				new ItemUnuseEvent(p, is, WeaponEquipMethod.HOTBAR_SWAP, ArmorListener.getWeaponType(is.getType())));
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
		for(int i = 0; i <= 4; i++)lore.add(" ");
		lore.add(ChatColor.GOLD + "price : $" + price);
		lore.add(ChatColor.GREEN + "seller : " + p.getPlayerListName());
		lore.add(ChatColor.RED + "expiration date : " + this.addHoursToJavaUtilDate(new Date(), 7).toString());

		im.setLore(lore);
		is.setItemMeta(im);
		FactionsMain.aucMain.addItem(p, is);
		p.sendMessage(ChatColor.GOLD + (im.hasDisplayName() ? im.getDisplayName() : is.getType().name().toLowerCase()) + " has been added to the auction house.");
		p.setItemInHand(new ItemStack(Material.AIR));
		
		return true;
	}
	
	public Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}

}
