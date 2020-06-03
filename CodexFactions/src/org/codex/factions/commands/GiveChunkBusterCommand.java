package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.chunkbusters.ChunkBusterMain;

import net.md_5.bungee.api.ChatColor;

public class GiveChunkBusterCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player && (sender.hasPermission("Codex.Factions.GiveBuster") || sender.isOp())) {
			Player p = (Player) sender;
			p.getInventory().addItem(ChunkBusterMain.getChunkBuster());
			p.sendMessage(ChatColor.AQUA + "You have been given a chunk buster");
			return true;
		}
		return false;
	}

}
