package org.codex.factions.executors;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.codex.factions.FactionObject;
import org.codex.factions.FactionPlayer;
import org.codex.factions.FactionsMain;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Inviter implements Execute {

	public static HashMap<Player, FactionObject> map = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (FactionsMain.Players.containsKey(p.getUniqueId())) {
				FactionPlayer facp = FactionsMain.Players.get(p.getUniqueId());
				if (facp.getRank().getLevel() >= 2) {
					Player p2 = Bukkit.getPlayer(args[1]);
					if (p2 == null) {
						p.sendMessage(ChatColor.RED + "This player does not exist. Try Retyping the name");
						return false;
					}
					p.sendMessage(ChatColor.AQUA + "You have invited " + p2.getName()
							+ " to your factions. Type /f revoke " + p2.getName() + " to remove the invite.");
					
					IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"" + "\",\"extra\":[{\"text\":\""
							+ ChatColor.AQUA + "You have been invited to " + facp.getFactionName() + ". "
							+ ChatColor.BOLD + "Click here" + ChatColor.RESET + "" + ChatColor.AQUA
							+ " to " + ChatColor.AQUA + "join." + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
							+ "/f join " + facp.getFactionName() + "\"}}]}");
					PacketPlayOutChat packet = new PacketPlayOutChat(comp);
					((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
					map.put(p2, facp.getFaction());
				}
			} else {
				p.sendMessage(ChatColor.RED + "You are not inside of a Faction");
			}
		} else if (sender.hasPermission("Codex.Factions.Admin")) {

		}
		return false;
	}

}
