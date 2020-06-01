package Factions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	public Map<Long, String> ClaimedChunks = new HashMap<>();
	static File FactionsDir;
	static File FactionsData;
	static File PlayersData;

	static {
		String urlString = ClassLoader.getSystemClassLoader().getResource("Factions/FactionsMain.class").toString();
		urlString = urlString.substring(urlString.indexOf("file:"), urlString.length());
		urlString = urlString.replaceAll("!", "");
		try {
			URL url = new URL(urlString);
			JarLocation = new File(url.toURI()).getParentFile().getParentFile();
			FactionsDir = new File(JarLocation.getParentFile() + "/CodexFactions");
			FactionsData = new File(FactionsDir + "/fdata.txt");
			PlayersData = new File(PlayersData + "/pdata.txt");

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public FactionsMain() {

	}

	@SuppressWarnings("unchecked")
	public void loadData() {
		try {
			if (!FactionsData.exists()) {
				FactionsData.createNewFile();
			}
			if (!PlayersData.exists()) {
				PlayersData.createNewFile();
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FactionsData));
			Factions  = (Map<String, FactionObject>) ois.readObject();
			ois.close();
			ois = new ObjectInputStream(new FileInputStream(PlayersData));
			Players = (Map<UUID, FactionPlayer>) ois.readObject();
			ois.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public void saveData() {
		try {
			if (!FactionsData.exists()) {
				FactionsData.createNewFile();
			}
			if (!PlayersData.exists()) {
				PlayersData.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(FactionsData));
			oos.writeObject(Factions);
			oos.close();
			oos = new ObjectOutputStream (new FileOutputStream(PlayersData));
			oos.writeObject(Players);
			oos.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
