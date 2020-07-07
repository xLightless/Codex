package org.codex.world.executor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Teleporter implements Executor	{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && args.length >= 5 && (sender.hasPermission("Codex.World.Teleport") || sender.isOp())) {
			try {
			 World w = Bukkit.getWorld(args[1]);
			 int x = Integer.parseInt(args[2]);
			 int y = Integer.parseInt(args[3]);
			 int z = Integer.parseInt(args[4]);
			 Player p = (Player) sender;
			 p.teleport(new Location(w, x, y, z));
			 sender.sendMessage(ChatColor.GREEN + "Teleported");
			 return true;
			}catch(NumberFormatException | NullPointerException e) {
				sender.sendMessage(ChatColor.RED + "Command invalid. Please do /world help");
			}
		}else if(sender instanceof Player && args.length >= 2 && (sender.hasPermission("Codex.World.Teleport") || sender.isOp())){
			try {
				 World w = Bukkit.getWorld(args[1]);
				 Player p = (Player) sender;
				 p.teleport(new Location(w, p.getLocation().getX(),  p.getLocation().getY(),  p.getLocation().getZ()));
				 sender.sendMessage(ChatColor.GREEN + "Teleported");
				 return true;
				}catch(NumberFormatException | NullPointerException e) {
					sender.sendMessage(ChatColor.RED + "Command invalid. Please do /world help");
				}
		}
		return false;
	}

}
