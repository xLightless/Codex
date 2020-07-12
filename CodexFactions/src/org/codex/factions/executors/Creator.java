package org.codex.factions.executors;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Creator implements Execute {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {

		if (args.length >= 2 && sender instanceof Player) {
			Player p = (Player) sender;
			try {
				this.createFaction(args[1], p.getUniqueId());
				p.sendMessage(ChatColor.GREEN + "you have created the faction " + args[1]);
				return true;
			} catch (Throwable e) {
				p.sendMessage(e.getMessage());
			}
		}
	
		return false;
	}
	

	private void createFaction(String facName, UUID uuid) throws Throwable {

		if (!FactionsMain.Factions.containsKey(facName)) {
			if (FactionsMain.Players.containsKey(uuid))
				throw new Throwable(ChatColor.RED + "You are already inside of a Faction");
			if(facName.length() >= 20) throw new Throwable(ChatColor.RED + "This faction name is too long. Retry.");
			FactionObject fac = new FactionObject(facName, uuid);
			FactionsMain.Factions.put(facName.toUpperCase(), fac);
			FactionsMain.saveData();
			return;
		} else {
			throw new Throwable(ChatColor.RED + "This faction name has already been taken");
		}
	}


}
