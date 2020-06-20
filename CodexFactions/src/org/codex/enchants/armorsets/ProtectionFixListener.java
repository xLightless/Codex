package org.codex.enchants.armorsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.Book;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityHuman;

public class ProtectionFixListener implements Listener{

	@EventHandler
	public void onEnchant(EnchantItemEvent e) {
		e.getEnchanter().sendMessage(ChatColor.BLUE + "test");
		Map<Enchantment, Integer> map = e.getEnchantsToAdd();
		if(e.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) {
			CustomProtectionEnchantment prot = CustomProtectionEnchantment.enchant;
			map.put(prot, map.get(Enchantment.PROTECTION_ENVIRONMENTAL));
			map.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
			e.getEnchanter().sendMessage(e.getExpLevelCost() + "");
			e.getEnchanter().setLevel(e.getEnchanter().getLevel() - (e.getExpLevelCost()/10));
			ItemStack is = e.getItem();
			e.getInventory().remove(e.getItem());
			is.addEnchantments(map);
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
			List<String> newlore = ProtectionFixListener.createPrefilledList(lore.size() + 1, "");
			int i = 0;
			
			for(String s : lore) {
				i++;
				newlore.set(i, s);
				
				
			}
			newlore.set(0, ChatColor.GRAY + "Protection " + Book.getRomanNumeral(map.get(prot)));
			im.setLore(newlore);
			is.setItemMeta(im);
			e.getInventory().addItem(is);
			e.getEnchantBlock().breakNaturally(new ItemStack(Material.AIR));
			Block b = e.getEnchanter().getWorld().getBlockAt(e.getEnchantBlock().getLocation());
			b.setType(Material.ENCHANTMENT_TABLE);
			resetSeed(e.getEnchanter());
			e.setCancelled(true);
			
		}
		
	}
	
	public void resetSeed(Player player) {
		((EntityHuman) ((CraftPlayer) player).getHandle()).enchantDone( 0);
		player.closeInventory();
	}
	
	public static <T> List<T> createPrefilledList(int size, T item) {
	    ArrayList<T> list = new ArrayList<T>(size);
	    for (int i = 0; i < size; i++) {
	        list.add(item);
	    }
	    return list;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			int protlevels = 0;
			Player p = (Player) e.getEntity();
				ItemStack[] armor = p.getInventory().getArmorContents();
				if(armor[0] != null) {
					if(armor[0].containsEnchantment(CustomProtectionEnchantment.enchant)) {
						protlevels += armor[0].getEnchantmentLevel(CustomProtectionEnchantment.enchant);
					}
				}
				if(armor[1] != null) {
					if(armor[1].containsEnchantment(CustomProtectionEnchantment.enchant)) {
						protlevels += armor[1].getEnchantmentLevel(CustomProtectionEnchantment.enchant);
					}
				}
				if(armor[2] != null) {
					if(armor[2].containsEnchantment(CustomProtectionEnchantment.enchant)) {
						protlevels += armor[2].getEnchantmentLevel(CustomProtectionEnchantment.enchant);
					}
				}
				if(armor[3] != null) {
					if(armor[3].containsEnchantment(CustomProtectionEnchantment.enchant)) {
						protlevels += armor[3].getEnchantmentLevel(CustomProtectionEnchantment.enchant);
					}
				}
				double percentProtect = protlevels * 0.04D;
				e.setDamage(e.getDamage() * (1 - percentProtect));
		}
	}
	
	public static ItemStack getFixedProtection(ItemStack is, int level) {
		is.addEnchantment(CustomProtectionEnchantment.enchant, level);
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
		List<String> newlore = ProtectionFixListener.createPrefilledList(lore.size() + 1, "");
		int i = 0;
		
		for(String s : lore) {
			i++;
			newlore.set(i, s);
			
		}
		newlore.set(0, ChatColor.GRAY + "Protection " + Book.getRomanNumeral(level));
		im.setLore(newlore);
		is.setItemMeta(im);
		return is;
	}
	
	

	
}
