package org.codex.factions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.codex.chunkbusters.ChunkBusterMain;
import org.codex.enchants.armorsets.ArmorSet;
import org.codex.enchants.armorsets.CustomProtectionEnchantment;
import org.codex.enchants.armorsets.EnderArmorSet;
import org.codex.enchants.armorsets.NetherArmorSet;
import org.codex.enchants.armorsets.PhantomArmorSet;
import org.codex.enchants.armorsets.ProtectionFixListener;
import org.codex.enchants.books.AdvancedFeatherFalling;
import org.codex.enchants.books.ArmorListener;
import org.codex.enchants.books.Armored;
import org.codex.enchants.books.AutoSmelt;
import org.codex.enchants.books.BookManager;
import org.codex.enchants.books.CreeperArmor;
import org.codex.enchants.books.Fangs;
import org.codex.enchants.books.Ferrite;
import org.codex.enchants.books.Freeze;
import org.codex.enchants.books.Frenzy;
import org.codex.enchants.books.FrenzyBlocker;
import org.codex.enchants.books.Gears;
import org.codex.enchants.books.Hardened;
import org.codex.enchants.books.HealthBoost;
import org.codex.enchants.books.ItemSwapEvent;
import org.codex.enchants.books.ItemUnuseEvent;
import org.codex.enchants.books.Knight;
import org.codex.enchants.books.LumberJack;
import org.codex.enchants.books.ObsidianShield;
import org.codex.enchants.books.Pheonix;
import org.codex.enchants.books.Rain;
import org.codex.enchants.books.Springs;
import org.codex.enchants.books.Tank;
import org.codex.enchants.books.Umbrella;
import org.codex.enchants.books.Vampire;
import org.codex.enchants.books.WeaponEquipMethod;
import org.codex.enchants.books.WeaponType;
import org.codex.enchants.books.WeedWacker;
import org.codex.enchants.energy.Energy;
import org.codex.enchants.energy.EnergyHarvester;
import org.codex.enchants.items.RepairCrystal;
import org.codex.enchants.items.TrenchPickaxe;
import org.codex.enchants.leveling.Levels;
import org.codex.factions.claims.Claim;
import org.codex.factions.claims.ClaimType;
import org.codex.factions.commands.CustomEnchant;
import org.codex.factions.commands.EnchanterComm;
import org.codex.factions.commands.FactionCommand;
import org.codex.factions.commands.FixCommand;
import org.codex.factions.commands.GiveArmorSet;
import org.codex.factions.commands.GiveBook;
import org.codex.factions.commands.GiveChunkBusterCommand;
import org.codex.factions.commands.GiveEnergyComm;
import org.codex.factions.commands.GiveHarvester;
import org.codex.factions.commands.GiveItem;
import org.codex.factions.commands.RemoveHarvester;
import org.codex.factions.commands.RenameCommand;
import org.codex.factions.commands.TestCommand;



public class FactionsMain extends JavaPlugin implements Listener{
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
	private static FactionsMain main;
	
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
	
	
	public static long chunkCoordsToLong(int x, int z) {
		long posz = z;
		long posx = x;
		posx = posx << 32;
		long result = posx | posz;
		return result;
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

		main = this;
		loadCommands();
		loadEvents();
		loadConstructors();
		BookManager.loadEnchants();
		ArmorSet.loadSets();
		loadConfigs();
		Energy.loadArmorStands();
		registerGlow();	
	}
	

	public void loadConfigs() {
		ConfigurationSerialization.registerClass(EnergyHarvester.class, "EnergyHarvester");
		Energy.getYml().load();
	}

	private void loadCommands() {
		getCommand("f").setExecutor(new FactionCommand());
		getCommand("givechunkbuster").setExecutor(new GiveChunkBusterCommand());
		getCommand("enchanter").setExecutor(new EnchanterComm(this));
		getCommand("giveenergy").setExecutor(new GiveEnergyComm());
		getCommand("giveset").setExecutor(new GiveArmorSet());
		getCommand("givebook").setExecutor(new GiveBook());
		getCommand("givedust").setExecutor(new Levels());
		getCommand("fix").setExecutor(new FixCommand());
		getCommand("rename").setExecutor(new RenameCommand());
		getCommand("harvesters").setExecutor(new TestCommand());
		getCommand("giveenergyharvester").setExecutor(new GiveHarvester());
		getCommand("removeharvester").setExecutor(new RemoveHarvester());
		getCommand("giveitem").setExecutor(new GiveItem());
		getCommand("cenchant").setExecutor(new CustomEnchant());
	}

	private void loadEvents() {

		Bukkit.getServer().getPluginManager().registerEvents(new Hardened(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EnchanterComm(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ArmorListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new AdvancedFeatherFalling(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BookManager(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Vampire(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new AutoSmelt(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Energy(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Ferrite(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CreeperArmor(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Pheonix(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new LumberJack(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Knight(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Levels(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Frenzy(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FrenzyBlocker(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Tank(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Armored(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Rain(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new WeedWacker(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Umbrella(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Fangs(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new RepairCrystal(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new TrenchPickaxe(0), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Freeze(), this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new ProtectionFixListener(), this);
		ChunkBusterMain cb = new ChunkBusterMain();
		getServer().getPluginManager().registerEvents(cb, this);
		
		Bukkit.getServer().getPluginManager().registerEvents(new NetherArmorSet(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EnderArmorSet(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PhantomArmorSet(), this);
	}

	private void loadConstructors() {
		new Vampire();
		new AutoSmelt();
		new Gears();
		new HealthBoost();
		new AdvancedFeatherFalling();
		new ObsidianShield();
		new Ferrite();
		new CreeperArmor();
		new Pheonix();
		new Springs();
		new LumberJack();
		new Knight();
		new Frenzy();
		new FrenzyBlocker();
		new Tank();
		new Armored();
		new Rain(this);
		new Hardened();
		new WeedWacker();
		new Umbrella();
		new NetherArmorSet();
		new EnderArmorSet();
		new PhantomArmorSet();
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
		BookManager.unloadEnchants();
		ArmorSet.unloadSets();
		saveConfigs();
		Bukkit.getScheduler().cancelTasks(this);
		Energy.removeArmorStands();
		FactionsMain.saveData();
	}

	

	public static FactionObject getFactionFromName(String facName) {
		return FactionsMain.Factions.get(facName.toUpperCase());
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
	
	private void saveConfigs() {
		Energy.getYml().save();
	}

	public static JavaPlugin getMain() {
		return FactionsMain.main;
	}

	@EventHandler
	public void onItemSwap(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		ItemStack oldis = p.getInventory().getItem(e.getPreviousSlot());

		ItemStack newis = p.getInventory().getItem(e.getNewSlot());
		if (oldis != null) {
			Bukkit.getServer().getPluginManager().callEvent(
					new ItemUnuseEvent(p, oldis, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(oldis.getType())));
		}
		if (newis != null) {
			Bukkit.getServer().getPluginManager().callEvent(
					new ItemSwapEvent(p, newis, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(newis.getType())));
		}
	}

	public static WeaponType getWeaponType(Material type) {
		switch (type) {
		case DIAMOND_SWORD:
			return WeaponType.SWORD;
		case IRON_SWORD:
			return WeaponType.SWORD;
		case GOLD_SWORD:
			return WeaponType.SWORD;
		case STONE_SWORD:
			return WeaponType.SWORD;
		case WOOD_SWORD:
			return WeaponType.SWORD;
		case DIAMOND_AXE:
			return WeaponType.AXE;
		case IRON_AXE:
			return WeaponType.AXE;
		case GOLD_AXE:
			return WeaponType.AXE;
		case STONE_AXE:
			return WeaponType.AXE;
		case WOOD_AXE:
			return WeaponType.AXE;
		case DIAMOND_SPADE:
			return WeaponType.SHOVEL;
		case IRON_SPADE:
			return WeaponType.SHOVEL;
		case GOLD_SPADE:
			return WeaponType.SHOVEL;
		case STONE_SPADE:
			return WeaponType.SHOVEL;
		case WOOD_SPADE:
			return WeaponType.SHOVEL;
		case DIAMOND_PICKAXE:
			return WeaponType.PICKAXE;
		case IRON_PICKAXE:
			return WeaponType.PICKAXE;
		case GOLD_PICKAXE:
			return WeaponType.PICKAXE;
		case STONE_PICKAXE:
			return WeaponType.PICKAXE;
		case WOOD_PICKAXE:
			return WeaponType.PICKAXE;
		default:
			break;
		}
		return null;

	}

	public static void broadcast(String s) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(s);
		}
	}

	private void registerGlow() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
		}
		try {
			Glow glow = new Glow(40);
			CustomProtectionEnchantment prot = CustomProtectionEnchantment.enchant;
			Enchantment.registerEnchantment(glow);
			Enchantment.registerEnchantment(prot);
		} catch (IllegalArgumentException e) {
		}
		remap(40);
		remap(42);

	}

	private void remap(int id) {
		try {
			Field byIdField = Enchantment.class.getDeclaredField("byId");
			Field byNameField = Enchantment.class.getDeclaredField("byName");

			byIdField.setAccessible(true);
			byNameField.setAccessible(true);

			@SuppressWarnings("unchecked")
			HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
			@SuppressWarnings("unchecked")
			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byNameField.get(null);

			if (byId.containsKey(id))
				byId.remove(id);

			if (byName.containsKey(getName()))
				byName.remove(getName());
		} catch (Exception ignored) {
		}
	}

	public static Claim getChunkFromLong(long l, int i, String world) {
		Chunk c = Bukkit.getWorld(world).getChunkAt((int) (l >> 32), (int) l);
		try {
			return ClaimType.getClaim(i, c);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	

}
