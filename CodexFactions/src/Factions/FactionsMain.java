package Factions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionsMain extends JavaPlugin implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equals("/f")) {
			if (args.length != 0) {
				switch (args[0]) {
				case "create":
					break;
				case "disband":
					break;
				case "invite":
					break;
				case "kick":
					break;
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
				}

			}

		}
		return false;
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}
}
