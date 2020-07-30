package org.codex.factions.executors;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class Helper implements Execute{

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.AQUA + "/f " + ChatColor.WHITE + "- Primary Command for Faction Commands");
		sender.sendMessage(ChatColor.AQUA + "/f create <Faction Name> " + ChatColor.WHITE + "- Used to create your Faction");
		sender.sendMessage(ChatColor.AQUA + "/f invite <Player Name> " + ChatColor.WHITE + "- Invite Players to your Faction");
		sender.sendMessage(ChatColor.AQUA + "/f join <Faction Name> " + ChatColor.WHITE + "- Used to join an existing Faction");
		sender.sendMessage(ChatColor.AQUA + "/f leave " + ChatColor.WHITE + "- This Command is used to Leave a Faction");
		sender.sendMessage(ChatColor.AQUA + "/f kick <Player Name> " + ChatColor.WHITE + "- Faction Heirarchy may use this command to remove Players.");
		sender.sendMessage(ChatColor.AQUA + "/f disband " + ChatColor.WHITE + "- Disbands your existing Faction - BE CAREFUL WHEN USING THIS!");
		sender.sendMessage(ChatColor.AQUA + "/f tag/name <Faction Name> " + ChatColor.WHITE + "- Changes your Faction Name");
		sender.sendMessage(ChatColor.AQUA + "/f rename " + ChatColor.WHITE + "- Alias to f tag/name? - kingmo100");
		sender.sendMessage(ChatColor.AQUA + "/f claim " + ChatColor.WHITE + "- Used to take rights from Wilderness Claims");
		sender.sendMessage(ChatColor.AQUA + "/f unclaim " + ChatColor.WHITE + "- Used to restore rights to Wilderness");
		sender.sendMessage(ChatColor.AQUA + "/f ally " + ChatColor.WHITE + "- Create an Alliance with another Faction - Has Highest Priority!");
		sender.sendMessage(ChatColor.AQUA + "/f truce " + ChatColor.WHITE + "- Create a Truce with another Faction - Has Moderate Priority!");
		sender.sendMessage(ChatColor.AQUA + "/f neutral " + ChatColor.WHITE + "- Set existing Factions to a Neutral Relation");
		sender.sendMessage(ChatColor.AQUA + "/f enemy " + ChatColor.WHITE + "- Used to set a existing Faction as an Enemy to Yours");
		sender.sendMessage(ChatColor.AQUA + "/f chat/c <Chat Type> " + ChatColor.WHITE + "- Toggle between Public/Private Conversations");
		sender.sendMessage(ChatColor.AQUA + "/f who/f/info " + ChatColor.WHITE + "- Inspect your Faction Information");
		sender.sendMessage(ChatColor.AQUA + "/f mod " + ChatColor.WHITE + "- Promote your Faction Players to a different Authority - Lowest");
		sender.sendMessage(ChatColor.AQUA + "/f coleader " + ChatColor.WHITE + "- Promote your Faction Players to a different Authority - Moderate");
		sender.sendMessage(ChatColor.AQUA + "/f leader " + ChatColor.WHITE + "- Promote your Faction Players to a different Authority - Highest");
		sender.sendMessage(" ");
		
		return true;
	}

}
