package org.codex.world.executor;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;

public class Generator implements Executor{

	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length >= 2 && (sender.hasPermission("Codex.World.Generate") || sender.isOp())) {
			if(!FactionsMain.getWorlds().contains(args[1]) && !(Bukkit.getWorld(args[1]) == null)) {
			World w = Bukkit.getServer().createWorld(new WorldCreator(args[1]));
			FactionsMain.addWorld(w.getName());
			sender.sendMessage(ChatColor.GREEN + args[1] + " has been created");
			return true;
			}else {
				sender.sendMessage(ChatColor.RED + "That world already exists");
			}
		}else {
			sender.sendMessage(ChatColor.RED + "Command invalid. Please type /world help");
		}
		
		return false;
	}
	
	
}
