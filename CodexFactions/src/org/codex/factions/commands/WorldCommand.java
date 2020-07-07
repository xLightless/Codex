package org.codex.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codex.world.executor.Executor;
import org.codex.world.executor.Generator;
import org.codex.world.executor.Helper;
import org.codex.world.executor.Info;
import org.codex.world.executor.Lister;
import org.codex.world.executor.Teleporter;

import net.md_5.bungee.api.ChatColor;

public class WorldCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length >= 1) {
			Executor ex;
		switch(args[0]) {
		case "generate":
			ex = new Generator();
			break;
		case "gen":
			ex = new Generator();
			break;
		case "tp":
			ex = new Teleporter();
			break;
		case "list":
			ex = new Lister();
			break;
		case "info":
			ex = new Info();
			break;
		case "help":
			ex = new Helper();
			break;
		default:
			sender.sendMessage(ChatColor.RED + "That is not a valid command. Please type /world help");
			return false;
			}
		return ex.onCommand(sender, args);
		}
		return false;
	}

}
