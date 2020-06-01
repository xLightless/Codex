package Factions;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionsMain extends JavaPlugin implements CommandExecutor {
	static File JarLocation;
	public static Map<String, FactionObject> Factions = new HashMap<>();
	public static Map<UUID, FactionPlayer> Players = new HashMap<>();

	static {
		String urlString = ClassLoader.getSystemClassLoader().getResource("Factions/FactionsMain.class").toString();
		urlString = urlString.substring(urlString.indexOf("file:"), urlString.length());
		urlString = urlString.replaceAll("!", "");
		try {
			URL url = new URL(urlString);
			JarLocation = new File(url.toURI()).getParentFile().getParentFile();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public FactionsMain() {

	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}

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
}
