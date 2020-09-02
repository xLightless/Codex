package org.codex.factions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.economy.EconomyMain;
import org.codex.factions.commands.AuctionCommand;

import net.md_5.bungee.api.ChatColor;

public class AuctionMain extends AuctionCommand {
	
	static String invAuctionHouse = "Auction House";
	static String invAhSellName = ChatColor.WHITE + "Click a Inventory Item to Sell:";
	
	private static HashMap<Player, Integer> playerView = new HashMap<>();
	private static List<String> itemList = new ArrayList<>();
	
	
	
	public static void createMenu (Player player) {

		int inventory = itemList.size() / 35;
		@SuppressWarnings("unused")
		int invOffset = itemList.size() % 35;
		
		Inventory ahMain = Bukkit.createInventory(null, 54, invAuctionHouse);	
		player.openInventory(ahMain);
		playerView.put(player, inventory);
		
		
		//Ah buttons
		
		
		//Balance Button
		ItemStack balance = new ItemStack(Material.PAPER);
		ItemMeta balanceMeta = balance.getItemMeta();
		balanceMeta.setDisplayName(ChatColor.RED + "Your balance is: " + EconomyMain.getPlayerMoney(player));
		balance.setItemMeta(balanceMeta);		
		ahMain.setItem(45, balance);
		
		//Ah sell button from main inventory
		ItemStack ahSell = new ItemStack(Material.CHEST);
		ItemMeta ahSellMeta = ahSell.getItemMeta();
		ahSellMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + "Want to sell something?");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click here to Sell an Item on the Auction House!");
		ahSellMeta.setLore(lore);
		ahSell.setItemMeta(ahSellMeta);
		ahMain.setItem(53, ahSell);
		
	}
	
	public static void createMenu (Inventory ahSell, Player player) {
		
		Inventory ahSell_ = Bukkit.createInventory(player, 36, invAhSellName);
		player.openInventory(ahSell_);
		
	}
	
	@EventHandler
	public void onInventoryClose (InventoryCloseEvent e) {
		
		Player player = (Player) e.getPlayer();
		
		if (playerView.containsKey(player) && e.getInventory().getTitle() == invAuctionHouse) {
		
			
			
		}
		
	}
	
	@EventHandler
	public void onButtonClick (InventoryClickEvent e) {
		
		Player player = (Player) e.getClickedInventory() ;
		Inventory ahMain = null;

		if (player.getOpenInventory() == ahMain) {
			Bukkit.broadcastMessage(" ah event testerino");
		}

		
	}
}