package org.codex.factions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.md_5.bungee.api.ChatColor;

public class FactionsListener implements Listener{
 
	@EventHandler
	public void onFactionMemberAttack(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p1 = (Player) e.getDamager();
			Player p2 = (Player) e.getEntity();
			try {
				
				FactionPlayer facp1 = FactionsMain.getPlayer(p1.getUniqueId());
				FactionPlayer facp2 = FactionsMain.getPlayer(p2.getUniqueId());
				
				FactionObject fac1 = facp1.getFaction();
				FactionObject fac2 = facp2.getFaction();
				if(fac1.getRelationshipWith(fac2).isFriendly()) {
					e.setCancelled(true);
					p1.sendMessage(ChatColor.AQUA + "You cannot damage " + facp2.getTag() + " " + p2.getName());
				}
			} catch (Throwable e1) {
			}
			
		}
	}
	
}
