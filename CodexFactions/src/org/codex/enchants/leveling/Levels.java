package org.codex.enchants.leveling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codex.factions.Glow;

import net.md_5.bungee.api.ChatColor;

public class Levels implements Listener, CommandExecutor {

	private ItemStack is = new ItemStack(Material.SUGAR);
	private ItemMeta im = is.getItemMeta();
	private List<String> lore = new ArrayList<>();

	public Levels() {
		lore.add(ChatColor.GREEN + "This magical dust has the power to increase the level of any item.");
		lore.add(ChatColor.GREEN + "Go into your inventory and click the item you wish to level up ");
		lore.add(ChatColor.GREEN + "and the item will gain XP. A bar will appear showing your progress");
		im.setDisplayName(ChatColor.YELLOW + "Mystical leveling dust ");
		im.setLore(lore);
		is.setItemMeta(im);

	}

	public ItemStack getDustItemStack(int amount) {

		ItemMeta im = this.is.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW + "Mystical leveling dust " + ChatColor.GRAY + "[" + ChatColor.YELLOW + ""
				+ ChatColor.BOLD + "" + amount + ChatColor.GRAY + "" + "]");
		ItemStack is = this.is;
		Glow g = new Glow(40);
		im.addEnchant(g, 1, true);
		is.setItemMeta(im);

		return is;
	}

	@EventHandler
	public void onApply(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack ci = e.getCurrentItem();
		ItemStack cu = e.getCursor();
		if (e.isCancelled())
			return;
		if (ci == null || cu == null || ci.getType() == Material.AIR || cu.getType() == Material.AIR)
			return;
		if (cu.getType() != Material.SUGAR)
			return;
		if (cu.getItemMeta().getDisplayName().contains("Mystical leveling dust")) {
			if (!canBeLeveled(ci.getType())) {
				p.sendMessage(ChatColor.RED + "That item cannot be leveled");
				return;
			} else {

				ItemMeta cim = ci.getItemMeta();
				String s = cim.getDisplayName();
				String s1 = cu.getItemMeta().getDisplayName();
				int level;
				int amount = Integer.parseInt(ChatColor.stripColor(s1).split("\\[")[1].split("\\]")[0]);
				if (s == null)
					s = ChatColor.stripColor(ci.getType().toString().toLowerCase()).replace("_", " ");
				if (s.contains("[") && s.contains("]")) {
					level = Integer.parseInt(ChatColor.stripColor(s).split("\\[")[1].split("\\]")[0]);
					List<String> lore = cim.getLore();
					int extraxp = 0;
					int x = 0;
					for (String s2 : lore) {
						if (ChatColor.stripColor(s2).contains("|||")) {
							extraxp = Integer.parseInt(s2.split(" : ")[1].split("/")[0]);
							break;
						} else
							x++;
					}
					if ((amount + extraxp) < getXpForLevel(level)) {
						lore.set(x,
								ChatColor.RED + " " + ChatColor.BOLD + getBars((amount + extraxp), getXpForLevel(level))
										+ ChatColor.BLUE + " : " + (amount + extraxp) + "/"
										+ ((int) getXpForLevel(level)));
						p.sendMessage(ChatColor.GREEN
								+ "You have gained more xp towards your next level. You have added " + amount + " xp.");
					} else if ((amount + extraxp) == getXpForLevel(level)) {
						level += 1;
						cim.setDisplayName(
								cim.getDisplayName().split("\\[")[0] + " " + ChatColor.RESET + "" + ChatColor.GRAY + "["
										+ ChatColor.YELLOW + "" + ChatColor.BOLD + "" + (level) + ChatColor.GRAY + "]");
						lore.set(x, ChatColor.RED + " " + ChatColor.BOLD + getBars(0, getXpForLevel(level))
								+ ChatColor.BLUE + " : " + 0 + "/" + ((int) getXpForLevel(level)));
						p.sendMessage(ChatColor.GREEN + "You have leveled up your item up once to level " + level);
					} else {
						double oldLevel = level;
						double usedXp = amount + extraxp + getTotalXp(oldLevel) - this.getXpForLevel(oldLevel);
						level = (int) getLevel(usedXp);
						double xpdiff = this.getXpDifference(usedXp, level);
						cim.setDisplayName(ChatColor.RESET + "" + cim.getDisplayName().split("\\[")[0] + " "
								+ ChatColor.RESET + "" + ChatColor.GRAY + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD
								+ "" + (level) + ChatColor.GRAY + "]");
						lore.set(x, ChatColor.RED + " " + ChatColor.BOLD + getBars((int) xpdiff, getXpForLevel(level))
								+ ChatColor.BLUE + " : " + ((int) xpdiff) + "/" + ((int) getXpForLevel(level)));
						p.sendMessage(ChatColor.GREEN + "You have leveled up your item to level " + level);
					}
					cim.setLore(lore);
					ci.setItemMeta(cim);
					e.setCurrentItem(ci);
					if (cu.getAmount() > 1) {
						cu.setAmount(cu.getAmount() - 1);
						e.getWhoClicked().setItemOnCursor(cu);
					} else
						e.getWhoClicked().setItemOnCursor(null);
					e.setCancelled(true);
				} else {

					cim.setDisplayName(ChatColor.RESET + "" + s + " " + ChatColor.GRAY + "[" + ChatColor.YELLOW + ""
							+ ChatColor.BOLD + "1" + ChatColor.GRAY + "]");
					level = 1;

					List<String> lore = cim.getLore();
					if (lore == null) {
						lore = new ArrayList<>();
					}
					if ((amount - 1) < ((int) getXpForLevel(level))) {
						lore.add(ChatColor.RED + " " + ChatColor.BOLD + getBars((amount - 1), getXpForLevel(level))
								+ ChatColor.BLUE + " : " + (amount - 1) + "/" + ((int) getXpForLevel(level)));
						p.sendMessage(
								ChatColor.GREEN + "added a level (COSTS 1) and " + (amount - 1) + " to your item");
					} else if ((amount - 1) == ((int) getXpForLevel(level))) {
						level += 1;
						lore.add(ChatColor.RED + " " + ChatColor.BOLD + getBars(0, getXpForLevel(level))
								+ ChatColor.BLUE + " : " + (0) + "/" + ((int) getXpForLevel(level)));
						cim.setDisplayName(cim.getDisplayName() + " " + ChatColor.RESET + "" + ChatColor.GRAY + "["
								+ ChatColor.YELLOW + "" + ChatColor.BOLD + level + ChatColor.GRAY + "]");
						p.sendMessage(ChatColor.GREEN
								+ "Created the leveling system (COSTS 1) and you had enough to level up to the next level");
					} else {
						double oldLevel = level;
						double usedXp = amount - 1 + getTotalXp(oldLevel) - this.getXpForLevel(oldLevel);
						level = (int) getLevel(usedXp);
						double xpdiff = this.getXpDifference(usedXp, level);
						cim.setDisplayName(
								cim.getDisplayName().split("\\[")[0] + " " + ChatColor.RESET + "" + ChatColor.GRAY + "["
										+ ChatColor.YELLOW + "" + ChatColor.BOLD + "" + (level) + ChatColor.GRAY + "]");
						lore.add(ChatColor.RED + " " + ChatColor.BOLD + getBars((int) xpdiff, getXpForLevel(level))
								+ ChatColor.BLUE + " : " + ((int) xpdiff) + "/" + ((int) getXpForLevel(level)));
						p.sendMessage(ChatColor.GREEN
								+ "Created the leveling system (COSTS 1) and you leveled up to level " + level);
					}
					cim.setLore(lore);
					ci.setItemMeta(cim);
					e.setCurrentItem(ci);
					e.setCurrentItem(ci);
					if (cu.getAmount() > 1) {
						cu.setAmount(cu.getAmount() - 1);
						e.getWhoClicked().setItemOnCursor(cu);
					} else
						e.getWhoClicked().setItemOnCursor(null);
					e.setCancelled(true);
				}

			}

		}

	}

	private String getBars(int i, double xpForLevel) {
		int bars = (int) ((20 * i) / xpForLevel);
		String temp = "";
		for (int x = 0; x < bars; x++) {
			temp = temp + ChatColor.GREEN + "" + ChatColor.BOLD + "|";
		}
		if (bars != 20) {
			for (int x = bars; x < 20; x++) {
				temp = temp + ChatColor.RED + "" + ChatColor.BOLD + "|";
			}
		}
		return temp;
	}

	public double getLevel(double totalxp) {
		int level = 0;
		while (totalxp >= 0) {
			totalxp -= getXpForLevel(level);
			if (totalxp >= 0)
				level++;
		}
		return level;

	}

	public double getTotalXp(double level) {

		if (level != 0) {
			return (((Math.pow(level, 2)) * 4) + 20) + getTotalXp(level - 1);
		}
		return 0;
	}

	public double getXpForLevel(double level) {
		if (level != 0)
			return (((Math.pow(level, 2)) * 4) + 20);
		return 0;
	}

	public double getXpDifference(double usedXp, double level) {
		int level2 = 0;

		for (int x = 0; x < level; x++) {
			if ((usedXp - this.getXpForLevel(level2)) >= 0)
				usedXp -= this.getXpForLevel(level2);

			level2++;
		}

		return usedXp;
	}

	public boolean canBeLeveled(Material m) {

		switch (m) {
		case DIAMOND_SWORD:
			return true;
		case DIAMOND_AXE:
			return true;
		case DIAMOND_SPADE:
			return true;
		case DIAMOND_PICKAXE:
			return true;
		case DIAMOND_HOE:
			return true;
		case IRON_SWORD:
			return true;
		case IRON_AXE:
			return true;
		case IRON_SPADE:
			return true;
		case IRON_PICKAXE:
			return true;
		case IRON_HOE:
			return true;
		case GOLD_SWORD:
			return true;
		case GOLD_AXE:
			return true;
		case GOLD_SPADE:
			return true;
		case GOLD_PICKAXE:
			return true;
		case GOLD_HOE:
			return true;
		case STONE_SWORD:
			return true;
		case STONE_AXE:
			return true;
		case STONE_SPADE:
			return true;
		case STONE_PICKAXE:
			return true;
		case STONE_HOE:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 1) {
				p.getInventory().addItem(this.getDustItemStack(Integer.parseInt(args[0])));
			} else {
				p.getInventory().addItem(this.getDustItemStack(10));
			}
			return true;
		}
		return false;
	}

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			ItemStack is = p.getInventory().getItemInHand();
			if (is != null && is.getType() != Material.AIR) {
				ItemMeta m = is.getItemMeta();
				if (m == null)
					return;
				String s = m.getDisplayName();
				if (s != null) {
					if (s.contains("[") && s.contains("]")) {
						try {
							int level = Integer.parseInt(ChatColor.stripColor(s).split("\\[")[1].split("\\]")[0]);
							double dmgMultiplier = 1 + ((double) level / 100);
							double damage = (e.getDamage() * dmgMultiplier);
							e.setDamage(damage);
						} catch (Exception e2) {
							e.setDamage(e.getDamage());
						}

					}

				}
			}

		}

	}

}
