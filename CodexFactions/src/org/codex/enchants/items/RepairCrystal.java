package org.codex.enchants.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.ArmorListener;
import org.codex.enchants.books.BookManager;
import org.codex.factions.Glow;
import org.codex.factions.commands.FixCommand;

import net.md_5.bungee.api.ChatColor;

public class RepairCrystal extends CustomItem<PlayerInteractEvent>{

	
	public RepairCrystal() {
		super(Material.REDSTONE, CustomItemType.REPAIR_CRYSTAL);
		this.setEnergyCost(700);
		this.createItemStack();
	}

	@Override
	public ItemStack createItemStack() {
		ItemStack is = new ItemStack(this.getMaterial());
		ItemMeta im = is.getItemMeta();
		im.addEnchant(new Glow(40), 1, true);
		im.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Repair Crystal");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Embued with the power to fix all items in your inventory");
		lore.add(ChatColor.GRAY + "Right click in order to activate this divine item");
		lore.add(ChatColor.AQUA + "Energy cost : 700");
		this.setEnergyCost(700);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@EventHandler
	@Override
	public void onActivation(PlayerInteractEvent e) {
		Player p =  e.getPlayer();
		ItemStack is = e.getItem();
		if(is == null)return;
		ItemMeta im = is.hasItemMeta() ? is.getItemMeta() : null;
		if(im == null)return;
		if(!im.hasLore())return;
		if(im.getLore() == null)return;
		if(!im.hasDisplayName())return;
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR)){
		if(!im.getDisplayName().equals(this.getItemStack().getItemMeta().getDisplayName()))return;
		List<String> lore = im.getLore();
		if(lore.get(1).equals(this.getItemStack().getItemMeta().getLore().get(1))) {
			boolean b = this.use(is).getVectorOne();
			if(b) {
				int x = -1;
				for(ItemStack i: p.getInventory().getContents()) {
					x++;
					if(i == null)continue;
					if(i.getType().equals(Material.AIR))continue;
					if(BookManager.checkWeapon(i) || ArmorListener.isArmor(i.getType())) {
						ItemMeta im2 = i.getItemMeta();
						if(im2.hasDisplayName())FixCommand.fix(i, p, ChatColor.GOLD + "repaired " + im2.getDisplayName(), x);
						else FixCommand.fix(i, p, ChatColor.GOLD + "repaired " + i.getType().toString().toLowerCase(), x);
					}
				}
				for(ItemStack i: p.getInventory().getArmorContents()) {
					x++;
					if(i == null)continue;
					if(i.getType().equals(Material.AIR))continue;
					if(BookManager.checkWeapon(i) || ArmorListener.isArmor(i.getType())) {
						ItemMeta im2 = i.getItemMeta();
						if(im2.hasDisplayName())FixCommand.fix(i, p, ChatColor.GOLD + "repaired " + im2.getDisplayName(), x);
						else FixCommand.fix(i, p, ChatColor.GOLD + "repaired " + i.getType().toString().toLowerCase(), x);
					}
				}
			}else {
				p.sendMessage(ChatColor.RED + "You do not have enough energy");
			}
		}
		}else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(!im.getDisplayName().equals(this.getItemStack().getItemMeta().getDisplayName()))return;
			List<String> lore = im.getLore();
			if(lore.get(1).equals(this.getItemStack().getItemMeta().getLore().get(1))) {
				p.sendMessage(ChatColor.RED + "You cannot place this");
				p.updateInventory();
				e.setCancelled(true);
			}
		}
	}

	@Override
	public boolean isItem(ItemStack is) {
		if(!is.hasItemMeta())return false;
		if(!is.getItemMeta().hasLore())return false;
		if(is.getItemMeta().getDisplayName().equals(this.getItemStack().getItemMeta().getDisplayName())) {
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			if(lore.get(2).equals(this.getItemStack().getItemMeta().getLore().get(2)))return true;
		}
		return false;
	}



}
