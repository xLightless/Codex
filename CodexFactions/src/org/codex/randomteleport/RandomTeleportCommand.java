package org.codex.randomteleport;

import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomTeleportCommand
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((sender instanceof Player))
    {
      Player p = (Player)sender;
      Location pLoc = p.getLocation();
      Random rand = new Random();
      int locX = rand.nextInt(1) == 1 ? rand.nextInt(2000) + 1000 | -1000 : -(rand.nextInt(2000) + 1000);
      int locY = 150;
      int locZ = rand.nextInt(1) == 1 ? rand.nextInt(2000) + 1000 | -1000 : -(rand.nextInt(2000) + 1000);
      for (int i = locY; p.getWorld().getBlockAt(locX + pLoc.getBlockX(), i, locZ + pLoc.getBlockZ()).getType().equals(Material.AIR); i--) {
        locY--;
      }
      Location loc = new Location(p.getWorld(), locX + pLoc.getBlockX(), locY + 1, locZ + pLoc.getBlockZ());
      p.teleport(loc);p.sendMessage(ChatColor.GRAY + "You have been teleported to a new location.");
      return true;
    }
    sender.sendMessage(ChatColor.RED + "This command cannot be executed in console!");
    return false;
  }
}
