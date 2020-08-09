package org.codex.spectateteleportblocker;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class SpectateTeleportMain implements Listener {
	
	@EventHandler
	public void onPlayerTeleport (PlayerTeleportEvent e) {
		
		Player player = (Player) e.getPlayer();
			
			if (player.getGameMode() == GameMode.SPECTATOR || e.getCause() == TeleportCause.SPECTATE) 
			
			e.setCancelled(true);
			
			
				
	}
}
