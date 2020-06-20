package org.codex.factions.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.armorsets.ArmorSet;
import org.codex.enchants.armorsets.ArmorSets;
import org.codex.enchants.books.ArmorListener;

import net.md_5.bungee.api.ChatColor;

public class FixCommand implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player) {
			Player p = (Player) arg0;
			if(!(p.hasPermission("General.Essentials.Fix") || p.isOp()))return false;
			return fix(p.getInventory().getItemInHand(), p) ? true : false;
		}
		return false;
	}
	
	public static boolean fix(ItemStack ui, Player p) {
		
		if(ui !=null) {
		ui.setDurability((short) -(ui.getType().getMaxDurability()));
		for(ArmorSets a: ArmorSets.values()) {
		if(ArmorSet.isArmor(ArmorSets.getArmorSetFromSetType(a), ui)) {
			ArmorSet set = ArmorSets.getArmorSetFromSetType(a);
			int x = 0;
			for(String s: ui.getItemMeta().getLore()) {
				if(!s.contains("Durability : ")) {
					x++;
				}else {
					break;
				}
			}
			ItemMeta im = ui.getItemMeta();
			List<String> s = im.getLore();
			s.set(x, ChatColor.GRAY + "Durability : " + (set.getDurabilityMultiplier() * ArmorSet.getMaxDiamondDurability(ArmorListener.getArmorType(ui.getType()))));
			p.sendMessage( ChatColor.GOLD + "Repaired your armor set");
			im.setLore(s);
			ui.setItemMeta(im);
			return true;
		}
		}
		p.setItemInHand(ui);
		p.updateInventory();	
		}
		return false;
	}
	
	public static boolean fix(ItemStack ui, Player p, String message, int i) {
		
		if(ui !=null) {
		ui.setDurability((short) -(ui.getType().getMaxDurability()));
		for(ArmorSets a: ArmorSets.values()) {
		if(ArmorSet.isArmor(ArmorSets.getArmorSetFromSetType(a), ui)) {
			ArmorSet set = ArmorSets.getArmorSetFromSetType(a);
			int x = 0;
			for(String s: ui.getItemMeta().getLore()) {
				if(!s.contains("Durability : ")) {
					x++;
				}else {
					break;
				}
			}
			ItemMeta im = ui.getItemMeta();
			List<String> s = im.getLore();
			s.set(x, ChatColor.GRAY + "Durability : " + (set.getDurabilityMultiplier() * ArmorSet.getMaxDiamondDurability(ArmorListener.getArmorType(ui.getType()))));
			p.sendMessage(message);
			im.setLore(s);
			ui.setItemMeta(im);
			
		}
		}
		p.getInventory().setItem(i, ui);
		p.updateInventory();	
		return true;
		}
		return false;
	}

}
