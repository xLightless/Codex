package org.codex.factions.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.enchants.books.BookManager;
import org.codex.enchants.books.BookType;
import org.codex.factions.ExpManager;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class EnchanterComm implements CommandExecutor, Listener{

	private final String e = "Enchanter";
	private static final ChatColor g = ChatColor.GREEN;
	@SuppressWarnings("unused")
	private ItemStack[] is;
	public String cb = ChatColor.RESET + "" + BookType.COMMON_BOOK.getChatColor() +  "" + ChatColor.BOLD + "Common Enchant Book";
	public String rb = ChatColor.RESET + "" +  BookType.RARE_BOOK.getChatColor() +  "" + ChatColor.BOLD + "Rare Enchant Book";
	public String mb = ChatColor.RESET + "" + BookType.MAJESTIC_BOOK.getChatColor() +  "" + ChatColor.BOLD + "Majestic Enchant Book";
	public String lb = ChatColor.RESET + "" + BookType.LEGENDARY_BOOK.getChatColor() +  "" + ChatColor.BOLD + "Legendary Enchant Book";
	public String myb = ChatColor.RESET + "" + BookType.MYSTICAL_BOOK.getChatColor() +  "" + ChatColor.BOLD + "Mystic Enchant Book";
	
	public EnchanterComm(FactionsMain plugin) {
	
		
		
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(!(sender.hasPermission("Enchanter.Enchanter") || sender.isOp())) {
			sender.sendMessage(ChatColor.RED + "You do not have proper permission to use this command");
			return true;
			}
			if(sender instanceof Player) {
			Player player = (Player) sender;
			Inventory i = getEnchanterInventory(player);
            
			is = i.getContents();
			
			
			player.openInventory(i);
			player.sendMessage(ChatColor.RED + "The Enchanter has been summoned");
			
			return true;
			}else {
				sender.sendMessage(ChatColor.RED + "Only a player can use this command");
			}
			return false;
	}

	
	private Inventory getEnchanterInventory(Player p) {
		Inventory i =  Bukkit.getServer().createInventory(p, 9, e);
		for (int x = 0; x < 5; x++) {
		ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta metbook = book.getItemMeta();
        List<String> lore= new ArrayList<>();
        if(x == 0) {
        	metbook.setDisplayName(cb);
        	lore.add(g + "Price : 5000 xp");
        	
        }
        else if(x == 1) {
        	metbook.setDisplayName(rb);
        	lore.add(g + "Price : 10000 xp");
        	
        }
        else if(x == 2) {
        	metbook.setDisplayName(mb);
        	lore.add(g + "Price : 15000 xp");
        	
        }
        else if(x == 3) {
        	metbook.setDisplayName(lb);
        	lore.add(g + "Price : 20000 xp");
        	
        }
        else if(x == 4) {
        	metbook.setDisplayName(myb);
        	lore.add(g + "Price : 25000 xp");
        	
        }
		metbook.setLore(lore);
		book.setItemMeta(metbook);
		
		
		i.setItem(x+2, book);
		
		}
		
		return i;
	}
	
	@EventHandler
    private void inventoryClick(InventoryClickEvent e)
    {
		
		Player p = (Player) e.getWhoClicked();
		ExpManager xpmanager = new ExpManager(p);
		ItemStack ci = e.getCurrentItem();
		if (e.getView().getTitle().equals(this.e)) {
			e.setCancelled(true);
               
               if ((ci == null) || (ci.getType().equals(Material.AIR))) {
                   return;
               }
               else if(ci.getType().equals(Material.BOOK)) {
            	  
            	   if(ci.getItemMeta().getDisplayName().equals(cb) && xpmanager.getTotalExperience() >= 5000) {
            		   xpmanager.setTotalExperience(xpmanager.getTotalExperience() - 5001);
            		   p.getInventory().addItem(getEnchantedBook(BookType.COMMON_BOOK));
            		   Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ANVIL_BREAK, 1, 1);
            	   }
            	   else if(ci.getItemMeta().getDisplayName().equals(rb) && xpmanager.getTotalExperience() >= 10000) {
            		   xpmanager.setTotalExperience(xpmanager.getTotalExperience() - 10001);
            		   p.getInventory().addItem(getEnchantedBook(BookType.RARE_BOOK));
            		   Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ANVIL_BREAK, 1, 1);
            	   }
            	   else if(ci.getItemMeta().getDisplayName().equals(mb) && xpmanager.getTotalExperience() >= 15000) {
            		   xpmanager.setTotalExperience(xpmanager.getTotalExperience() - 15001);
            		   p.getInventory().addItem(getEnchantedBook(BookType.MAJESTIC_BOOK));
            		   Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ANVIL_BREAK, 1, 1);
            	   }
            	   else if(ci.getItemMeta().getDisplayName().equals(lb) && xpmanager.getTotalExperience() >= 20000) {
            		   xpmanager.setTotalExperience(xpmanager.getTotalExperience() - 20001);
            		   p.getInventory().addItem(getEnchantedBook(BookType.LEGENDARY_BOOK));
            		   Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ANVIL_BREAK, 1, 1);
            	   }
            	   else if(ci.getItemMeta().getDisplayName().equals(myb) && xpmanager.getTotalExperience() >= 25000) {
            		   xpmanager.setTotalExperience(xpmanager.getTotalExperience() - 25001);
            		   p.getInventory().addItem(getEnchantedBook(BookType.MYSTICAL_BOOK));
            		   Bukkit.getServer().getWorlds().get(0).playSound(p.getLocation(), Sound.ANVIL_BREAK, 1, 1);
            	   }else {
                	   p.sendMessage(ChatColor.RED + "You do not have enough XP!!!");
                	   return;
                   }
            	  
               }else {
            	   return;
               }

               
		}
    }
	
	
	public ItemStack getEnchantedBook(BookType book) {
				
		BookManager b = new BookManager();
		
		switch (book) {
		
		case COMMON_BOOK:
			return b.cb;
		case RARE_BOOK:
			return b.rb;
		case MAJESTIC_BOOK:
			return b.mb;
		case LEGENDARY_BOOK:
			return b.lb;
		case MYSTICAL_BOOK:
			return b.myb;
			
		default:
			try {
				throw NulledEnchantedBook();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			break;
		
			
		
		
		}
		
		
		
		
		return null;
		
	}


	private Exception NulledEnchantedBook() {
		System.out.println("ERROR URE STUPID U ADDED ANOTHER BOOK WITHOUT ADDING TO THE GET ENCHANTED BOOK METHOD");
		return null;
	}
	
}


