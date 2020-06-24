package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.factions.Rank;
import org.codex.factions.executors.Creator;
import org.codex.factions.executors.Disbander;
import org.codex.factions.executors.Info;
import org.codex.factions.executors.Inviter;
import org.codex.factions.executors.Joiner;
import org.codex.factions.executors.Kicker;
import org.codex.factions.executors.Leaver;
import org.codex.factions.executors.Ranker;
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
				break;
			case "help":
				break;
			case "tag":
				return new Tagger().onCommand(sender, args);
			case "rename":
				break;
			case "claim":
				break;
			case "ally":
				break;
			case "truce":
				break;
			case "info":
				return info.onCommand(sender, args);
			case "f":
				return info.onCommand(sender, args);
			default:
				sender.sendMessage(ChatColor.RED + "That command is not valid. Try /f help");
				break;
			}

		} else
			sender.sendMessage(ChatColor.RED + "That command is not valid. Try /f help");
		return false;
	}
	
}
