package org.codex.factions.executors;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;
import org.codex.factions.Relationship;

import net.md_5.bungee.api.ChatColor;

public class Chatter implements Execute, Listener {

	private static Map<Player, Relationship> chat = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(!FactionsMain.Players.containsKey(p.getUniqueId())) {
				p.sendMessage(ChatColor.RED + "You are not in a faction");
				return true;
			}
			else if(args.length != 2) {
				p.sendMessage(ChatColor.RED + "Command invalid");
				return false;
			}else {
				switch(args[1]) {
					case "a":
						chat.put(p, Relationship.ALLY);
						break;
					case "t":
						chat.put(p, Relationship.TRUCE);
						break;
					case "p":
						if(chat.containsKey(p))chat.remove(p);
						p.sendMessage(ChatColor.AQUA + "You have changed to public chat");
						return true;
					case "ally":
						chat.put(p, Relationship.ALLY);
						break;
					case "truce":
						chat.put(p, Relationship.TRUCE);
						break;
					case "public":
						if(chat.containsKey(p))chat.remove(p);
						p.sendMessage(ChatColor.AQUA + "You have changed to public chat");
						return true;
						
				}
				p.sendMessage(chat.get(p).getColor() + "You have changed to " + chat.get(p).toString().toLowerCase() + " chat" );
				return true;
			}
		}
		
		return true;
	}
	
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(chat.containsKey(e.getPlayer())) {
			Player p = e.getPlayer();
			e.setCancelled(true);
			Relationship r = chat.get(p);
			FactionObject fac;
			FactionPlayer facp;
			try {
				fac = FactionsMain.getPlayerFaction(p.getUniqueId());
				facp = FactionsMain.getPlayer(p.getUniqueId());
			} catch (Throwable e1) {
				e1.printStackTrace();
				return;
			}
			fac.broadcast(r.getColor() + facp.getTag() + p.getDisplayName() + " : " + e.getMessage());
			for(byte i = r.getID(); i >=0; i--) 
			for(String string: fac.getRelations().get(i)) {
				FactionObject fac2 = FactionsMain.getFactionFromName(string);
				fac2.broadcast(r.getColor() + facp.getTag() + p.getDisplayName() + " : " + e.getMessage());
			}
			
		}
	}

}
