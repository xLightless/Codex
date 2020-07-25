package org.codex.factions.executors;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.Relationship;

import net.md_5.bungee.api.ChatColor;

public class Shipper implements Execute {

	private Relationship r;
	private HashMap<String, Integer> confirm = new HashMap<>();
	private boolean confirmationRequired;
	
	public Shipper(Relationship r, boolean confirmationRequired) {
		this.r = r;
		this.confirmationRequired = confirmationRequired;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			try {
				FactionPlayer facp = FactionsMain.getPlayer(p.getUniqueId());
				if(facp.getRank().getLevel() < 2) {
					p.sendMessage(ChatColor.RED + "You do not have a high enough rank to use this command");
					return true;
				}else if(args.length != 2){
					p.sendMessage(ChatColor.RED + "Command invalid. Please retype");
				}
				try {
				FactionObject fac = facp.getFaction();
				FactionObject fac2 = FactionsMain.getFactionFromName(args[1]);
				if(confirm.containsKey(fac2.getFactionName()) | !confirmationRequired) {
					confirm.remove(fac2.getFactionName());
					fac.broadcast(ChatColor.AQUA + "You have become " + fac2.getFactionName() + "'s " + r.getTense());
					fac2.broadcast(ChatColor.AQUA + "" + fac.getFactionName() + " has accepted your offer and you are now " + r.getTense());
				}else {
				
					fac2.broadcast(ChatColor.AQUA + fac.getFactionName() + " wishes to become your " + r.getTense());
					fac.broadcast(ChatColor.AQUA + fac2.getFactionName() + " has been informed you wish to be " + r.getTense());
					int i = Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsMain.getMain(), new Runnable() {

						@Override
						public void run() {
							confirm.remove(fac.getFactionName());
							fac.broadcast(ChatColor.AQUA + fac2.getFactionName() + " did not accept your offer to become " + r.getTense());
						}
						
					}, 100* 20);
					confirm.put(fac.getFactionName(), i);
				}
				}catch(NullPointerException e) {
					p.sendMessage(e.getMessage());
				}
			} catch (Throwable e) {
				p.sendMessage(e.getMessage());
			}
		}

		return false;
	}

}
