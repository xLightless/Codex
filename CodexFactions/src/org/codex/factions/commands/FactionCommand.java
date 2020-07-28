package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.factions.Rank;
import org.codex.factions.Relationship;
import org.codex.factions.executors.Claimer;
import org.codex.factions.executors.Creator;
import org.codex.factions.executors.Disbander;
import org.codex.factions.executors.Info;
import org.codex.factions.executors.Inviter;
import org.codex.factions.executors.Joiner;
import org.codex.factions.executors.Kicker;
import org.codex.factions.executors.Leaver;
import org.codex.factions.executors.Ranker;
import org.codex.factions.executors.Shipper;
import org.codex.factions.executors.Tagger;

import net.md_5.bungee.api.ChatColor;

public class FactionCommand implements CommandExecutor {

	private Joiner join = new Joiner();
	private Kicker kick = new Kicker();
	private Creator create = new Creator();
	private Disbander disband = new Disbander();
	private Info info = new Info();
	private Inviter invite = new Inviter();
	
	public FactionCommand() {
		
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 0) {
			switch (args[0].toLowerCase()) {
			case "create":
				return create.onCommand(sender, args);
			case "disband":
				return disband.onCommand(sender, args);
			case "invite":
				return invite.onCommand(sender, args);
			case "kick":
				return kick.onCommand(sender, args);
			case "join":
				return join.onCommand(sender, args);
			case "leave":
				return new Leaver().onCommand(sender, args);
			case "mod":
				return new Ranker(Rank.MODERATOR, null).onCommand(sender, args);
			case "coleader":
				return new Ranker(Rank.COLEADER, null).onCommand(sender, args);
			case "leader":
				return new Ranker(Rank.LEADER, null).onCommand(sender, args);
			case "help":
				
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
				
				
				break;
			case "tag":
				return new Tagger().onCommand(sender, args);
			case "rename":
				break;
			case "claim":
				return new Claimer().onCommand(sender, args);
				
			case "unclaim":  //Haven't made a unclaim command yet? - lightless
				break;
				
			case "ally":
				return new Shipper(Relationship.ALLY, true).onCommand(sender, args);
			case "truce":
				return new Shipper(Relationship.TRUCE, true).onCommand(sender, args);
			case "neutral":
				return new Shipper(Relationship.NEUTRAL, false).onCommand(sender, args);
			case "enemy":
				return new Shipper(Relationship.ENEMY, false).onCommand(sender, args);
			case "c":
				break;
			case "chat":
				break;
			case "info":
				return info.onCommand(sender, args);
			case "who":
				return info.onCommand(sender, args);
			case "f":
				return info.onCommand(sender, args);
			default:
				sender.sendMessage(ChatColor.RED + "That Command is not valid. Try /f help");
				break;
			}

		} else
			sender.sendMessage(ChatColor.RED + "That command is not valid. Try /f help");
		return false;
	}
	
}
