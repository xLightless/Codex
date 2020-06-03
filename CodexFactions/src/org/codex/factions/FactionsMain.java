package org.codex.factions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.codex.chunkbusters.ChunkBusterMain;
import org.codex.factions.Executors.Kicker;

import net.md_5.bungee.api.ChatColor;

public class FactionsMain extends JavaPlugin implements CommandExecutor {
	static File JarLocation;
	public static Map<String, FactionObject> Factions = new HashMap<>();
	/**
	 * A map containing all players which are currently inside of a Faction. Player
	 * must be removed from this list when kicked or not inside of a Faction.
	 */
	public static Map<UUID, FactionPlayer> Players = new HashMap<>();
	public static Map<Long, String> ClaimedChunks = new HashMap<>();
	static File FactionsData;
	static File PlayersData;
	Kicker kick;

	public FactionsMain() {
		//getServer().getPluginManager().enablePlugin(new ChunkBusterMain());
	}

	@SuppressWarnings("unchecked")
	public static void loadData() {
		try {
			if (!FactionsData.exists()) {
				FactionsData.createNewFile();
			}
			if (!PlayersData.exists()) {
				PlayersData.createNewFile();
			}
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FactionsData));
				Factions = (Map<String, FactionObject>) ois.readObject();
				ois.close();
				ois = new ObjectInputStream(new FileInputStream(PlayersData));
				Players = (Map<UUID, FactionPlayer>) ois.readObject();
				ois.close();
			} catch (Exception e) {
				System.out.println("Ois stream is empty");
			}
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void saveData() {
		try {
			if (!FactionsData.exists()) {
				FactionsData.createNewFile();
			}
			if (!PlayersData.exists()) {
				PlayersData.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FactionsData));
			oos.writeObject(Factions);
			oos.close();
			oos = new ObjectOutputStream(new FileOutputStream(PlayersData));
			oos.writeObject(Players);
			oos.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static boolean isChunkWilderness(int x, int z) {
		long posz = z;
		long posx = x << 32;
		long result = posx | posz;
		return !ClaimedChunks.containsKey(result);
	}

	// returns null if wilderness
	public static FactionObject getChunkOwner(int x, int z) {
		long posz = z;
		long posx = x << 32;
		long result = posx | posz;
		if (ClaimedChunks.containsKey(result)) {
			return Factions.get(ClaimedChunks.get(result));
		} else {
			return null;
		}
	}

	@Override
	public void onEnable() {
		loadSaveFolders();
		getCommand("f").setExecutor(this);
		ChunkBusterMain cb = new ChunkBusterMain();
		getServer().getPluginManager().registerEvents(cb, this);
	}

	private void loadSaveFolders() {
		String urlString = FactionsMain.class.getClassLoader().getResource("org/codex/factions/FactionsMain.class")
				.toString();
		urlString = urlString.substring(urlString.indexOf("file:"), urlString.length());
		urlString = urlString.replaceAll("!", "");
		try {
			URL url = new URL(urlString);
			JarLocation = new File(url.toURI()).getParentFile().getParentFile();
			File CodexDir = new File("plugins/CodexFactions");
			if(!CodexDir.exists()) {
				CodexDir.mkdir();
			}
			FactionsData = new File("plugins/CodexFactions/fdata.txt");
			PlayersData = new File("plugins/CodexFactions/pdata.txt");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		FactionsMain.loadData();

	}

	@Override
	public void onDisable() {
		FactionsMain.saveData();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length != 0) {
			switch (args[0]) {
			case "create":
				if (args.length >= 2 && sender instanceof Player) {
					Player p = (Player) sender;
					try {
						this.createFaction(args[1], p.getUniqueId());
						p.sendMessage(ChatColor.GREEN + "you have created the faction " + args[1]);
					} catch (Throwable e) {
						p.sendMessage(e.getMessage());
					}
				}
				break;
			case "disband":
				if (!(sender instanceof Player))
					break;
				Player p = (Player) sender;
				try {
					this.clearFaction(p.getUniqueId());
				} catch (Throwable e) {
					p.sendMessage(e.getMessage());
				}
				break;
			case "invite":
				break;
			case "kick":
				kick.onCommand(sender, args);
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
			case "rename":
				break;
			case "claim":
				break;
			case "info":
				break;
			default:
				sender.sendMessage(ChatColor.RED + "That command is not valid. Try /f help");
				break;
			}

		} else
			sender.sendMessage(ChatColor.RED + "That command is not valid. Try /f help");
		return false;
	}

	public static FactionObject getFactionFromName(String facName) {
		return FactionsMain.Factions.get(facName);
	}

	private void createFaction(String facName, UUID uuid) throws Throwable {

		if (!Factions.containsKey(facName)) {
			if (Players.containsKey(uuid))
				throw new Throwable(ChatColor.RED + "You are already inside of a Faction");
			FactionObject fac = new FactionObject(facName, uuid);
			Factions.put(facName, fac);
			saveData();
			return;
		} else {
			throw new Throwable(ChatColor.RED + "This faction name has already been taken");
		}
	}

	private void clearFaction(UUID uuid) throws Throwable {
		if (!Players.containsKey(uuid))
			throw new Throwable(ChatColor.RED + "You are not inside of a Faction");
		if (!Factions.get(Players.get(uuid).getFactionName()).getLeaderUUID().equals(uuid))
			throw new Throwable(ChatColor.RED + "You are not the leader of this Faction");
		FactionObject fac = Players.get(uuid).getFaction();
		for (UUID u : fac.getPlayers())
			Players.remove(u);
		Factions.remove(fac.getFactionName());
		Bukkit.broadcastMessage(
				ChatColor.GRAY + fac.getFactionName() + " has been disbanded by " + Bukkit.getPlayer(uuid).getName());
	}
	public static FactionObject getPlayerFaction(UUID id) throws Throwable{
		FactionPlayer fp = Players.get(id);
		if(fp == null) {
			throw new Throwable("Your not in a faction");
		}
		return fp.getFaction();
	}
	public static FactionPlayer getPlayer(UUID id) throws Throwable{
		FactionPlayer fp = Players.get(id);
		if(fp == null) {
			throw new Throwable("Your not in a faction");
		}
		return fp;
	}
	

}
