package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.factions.Executors.Creator;
import org.codex.factions.Executors.Disbander;
import org.codex.factions.Executors.Info;
import org.codex.factions.Executors.Kicker;

import net.md_5.bungee.api.ChatColor;

public class FactionCommand implements CommandExecutor {

	private Kicker kick = new Kicker();
	private Creator create = new Creator();
	private Disbander disband = new Disbander();
	private Info info = new Info();
	
	public FactionCommand() {
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 0) {
			switch (args[0]) {
			case "create":
				return create.onCommand(sender, args);
			case "disband":
				return disband.onCommand(sender, args);
			case "invite":
				break;
			case "kick":
				return kick.onCommand(sender, args);
			case "leave":
				break;
			case "mod":
				break;
			case "coleader":
				break;
			case "leader":
				break;
			case "help":
				break;
			case "tag":
				break;
			case "rename":
				break;
			case "claim":
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
