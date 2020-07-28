package org.codex.factions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.codex.chunkbusters.ChunkBusterMain;
import org.codex.economy.EconomyMain;
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
import org.codex.enchants.books.Crit;
import org.codex.enchants.books.Fangs;
import org.codex.enchants.books.Ferrite;
import org.codex.enchants.books.Freeze;
import org.codex.enchants.books.Frenzy;
import org.codex.enchants.books.FrenzyBlocker;
import org.codex.enchants.books.Gears;
import org.codex.enchants.books.Hardened;
import org.codex.enchants.books.Harvest;
import org.codex.enchants.books.HealthBoost;
import org.codex.enchants.books.ItemSwapEvent;
import org.codex.enchants.books.ItemUnuseEvent;
import org.codex.enchants.books.Knight;
import org.codex.enchants.books.LumberJack;
import org.codex.enchants.books.ObsidianShield;
import org.codex.enchants.books.Pheonix;
import org.codex.enchants.books.Rain;
import org.codex.enchants.books.SafeGuard;
import org.codex.enchants.books.SafeWalk;
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
import org.codex.factions.claims.ClaimManager;
import org.codex.factions.claims.ClaimType;
import org.codex.factions.commands.AuctionCommand;
import org.codex.factions.commands.BalanceCommand;
import org.codex.factions.commands.CustomEnchant;
import org.codex.factions.commands.EconomyCommand;
import org.codex.factions.commands.EnchanterComm;
import org.codex.factions.commands.FactionCommand;
import org.codex.factions.commands.FixCommand;
import org.codex.factions.commands.GiveArmorSet;
import org.codex.factions.commands.GiveBook;
import org.codex.factions.commands.GiveChunkBusterCommand;
import org.codex.factions.commands.GiveEnergyComm;
import org.codex.factions.commands.GiveHarvester;
import org.codex.factions.commands.GiveItem;
import org.codex.factions.commands.PayCommand;
import org.codex.factions.commands.RandomTeleportCommand;
import org.codex.factions.commands.RemoveHarvester;
import org.codex.factions.commands.RenameCommand;
import org.codex.factions.commands.TestCommand;
import org.codex.factions.commands.WithdrawCommand;
import org.codex.factions.commands.WorldCommand;
import org.codex.obsidiandestroyer.TNTHandler;
import org.codex.packetmanager.PacketMain;

public class FactionsMain extends JavaPlugin implements Listener {
	public static Map<String, FactionObject> Factions = new HashMap<>();
	/**
	 * A map containing all players which are currently inside of a Faction. Player
	 * must be removed from this list when kicked or not inside of a Faction.
	 */
	public static Map<UUID, FactionPlayer> Players = new HashMap<>();
	/**
	 * format goes Chunk X and Z converted to long World Name and then Faction Name
	 * 
	 */
	public static Map<Long, HashMap<String, String>> ClaimedChunks = new HashMap<>();
	private static Set<String> worlds = new HashSet<>();
	private static File FactionsData;
	private static File PlayersData;
	private static File claimedData;
	private static File worldData;
	private static FactionsMain main;
	private static EconomyMain ecoMain;

	public FactionsMain() {
		// getServer().getPluginManager().enablePlugin(new ChunkBusterMain());
	}

	@SuppressWarnings("unchecked")
	private static void loadData() {

		Factions = (Map<String, FactionObject>) FactionsMain.loadData(FactionsData) == null ? new HashMap<>() : (Map<String, FactionObject>) FactionsMain.loadData(FactionsData);
		Players = (Map<UUID, FactionPlayer>) FactionsMain.loadData(PlayersData) == null ? new HashMap<>() : (Map<UUID, FactionPlayer>) FactionsMain.loadData(PlayersData);
		ClaimedChunks = (Map<Long, HashMap<String, String>>) FactionsMain.loadData(claimedData) == null ? new HashMap<>() : (Map<Long, HashMap<String, String>>) FactionsMain.loadData(claimedData)  ;
		worlds = (Set<String>) FactionsMain.loadData(worldData) == null ? new HashSet<>() : (Set<String>) FactionsMain.loadData(worldData) ;
		EconomyMain.loadMoney();
	}

	public static Object loadData(File f) {
		Object ob;
		try {
			if (!f.exists())
				f.createNewFile();

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			ob = ois.readObject();
			ois.close();
			System.out.println("Loaded " + f.getName());
			return ob;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static void saveData() {
		FactionsMain.save(FactionsData, Factions);
		FactionsMain.save(PlayersData, Players);
		FactionsMain.save(claimedData, ClaimedChunks);
		FactionsMain.save(worldData, worlds);
		EconomyMain.saveMoney();
	}

	public static void save(File f, Object o) {
		ObjectOutputStream oos;
		try {
			if (!f.exists())
				f.createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean isChunkWilderness(int x, int z) {
		return !ClaimedChunks.containsKey(chunkCoordsToLong(x, z));
	}

	public static long chunkCoordsToLong(int x, int z) {
		long posz = z;
		long posx = x;
		posx = posx << 32;
		long result = posx | posz;
		return result;
	}

	// returns null if wilderness
	public static FactionObject getChunkOwner(int x, int z, String name) {
		long posz = z;
		long posx = 32;
		posx = posx << 32;
		long result = posx | posz;
		if(ClaimedChunks == null)return null;
		if (ClaimedChunks.containsKey(result)) {
			return Factions.get(ClaimedChunks.get(result).get(name).toUpperCase());
		} else {
			return null;
		}
	}

	public static FactionObject getChunkOwner(Chunk c) {
		long posz = c.getZ();
		long posx = c.getX();
		String name = c.getWorld().getName();
		posx = posx << 32;
		long result = posx | posz;
		if(ClaimedChunks == null)return null;
		if (ClaimedChunks.containsKey(result)) {
			return Factions.get(ClaimedChunks.get(result).get(name).toUpperCase());
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
		createConfig();
	}

	@SuppressWarnings("unchecked")
	private void createConfig() {
		FileConfiguration config = this.getConfig();
		List<String> matList = new ArrayList<>();
		matList.add(Material.OBSIDIAN.toString());
		matList.add(Material.ENCHANTMENT_TABLE.toString());
		Map<String, Double> priceMap = new HashMap<>();
		priceMap.put(Material.IRON_BLOCK.toString(), 3000D);
		config.addDefault("price_map", priceMap);
		config.addDefault("health", 4);
		config.addDefault("max_destroyed", 8);
		config.addDefault("material_list", matList);
		config.addDefault("clicker", Material.POTATO_ITEM.toString());
		config.addDefault("protection_block", Material.HAY_BLOCK.toString());
		config.options().copyDefaults(true);
		this.saveConfig();
		Bukkit.getServer().getPluginManager()
				.registerEvents(new TNTHandler(config.getInt("health"), config.getInt("max_destroyed"),
						(List<String>) config.getList("material_list"),
						Material.getMaterial(config.getString("clicker")),
						Material.getMaterial(config.getString("protection_block"))), this);
		try {
			for (String m : config.getConfigurationSection("price_map").getKeys(false)) {
				priceMap.put(m, config.getDouble("price_map." + m));
			}
			ecoMain = new EconomyMain(priceMap);
		} catch (NullPointerException e) {
			ecoMain = new EconomyMain(new HashMap<>());
			e.printStackTrace();
		}
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
		getCommand("world").setExecutor(new WorldCommand());
		getCommand("ah").setExecutor(new AuctionCommand());
		getCommand("eco").setExecutor(new EconomyCommand());
		getCommand("pay").setExecutor(new PayCommand());
		getCommand("wild").setExecutor(new RandomTeleportCommand());
		getCommand("balance").setExecutor(new BalanceCommand());
		getCommand("withdraw").setExecutor(new WithdrawCommand());
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
		Bukkit.getServer().getPluginManager().registerEvents(new Harvest(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Crit(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SafeGuard(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SafeWalk(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FactionsListener(), this);
		ChunkBusterMain cb = new ChunkBusterMain();
		getServer().getPluginManager().registerEvents(cb, this);

		Bukkit.getServer().getPluginManager().registerEvents(new NetherArmorSet(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EnderArmorSet(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PhantomArmorSet(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PacketMain(getServer()), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClaimManager(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new AuctionCommand(), this);
		
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
			File CodexDir = new File("plugins/CodexFactions");
			if (!CodexDir.exists()) {
				CodexDir.mkdir();
			}
			FactionsData = new File("plugins/CodexFactions/fdata.txt");
			PlayersData = new File("plugins/CodexFactions/pdata.txt");
			claimedData = new File("plugins/CodexFactions/cdata.txt");
			worldData = new File("plugins/CodexMain/worlds.txt");
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

	public static FactionObject getFactionFromName(String facName) throws NullPointerException {
		try {
		return FactionsMain.Factions.get(facName.toUpperCase());
		}catch(Exception e) {
			throw new NullPointerException("This faction does not exist");
		}
	}

	public static FactionObject getPlayerFaction(UUID id) throws Throwable {
		FactionPlayer fp = Players.get(id);
		if (fp == null) {
			throw new Throwable("You are not in a faction");
		}
		return fp.getFaction();
	}

	public static FactionPlayer getPlayer(UUID id) throws Throwable {
		FactionPlayer fp = Players.get(id);
		if (fp == null) {
			throw new Throwable("You are not in a faction");
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

	public static Claim getClaim(int x, int z, String w) {
		FactionObject fac = FactionsMain
				.getFactionFromName(FactionsMain.ClaimedChunks.get(FactionsMain.chunkCoordsToLong(x, z)).get(w));
		long l = FactionsMain.chunkCoordsToLong(x, z);
		return FactionsMain.getChunkFromLong(l, fac.getLand().get(l).getVectorOne(), w);
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


	public static EconomyMain getEconomy() {
		return ecoMain;
	}
	
	public static Set<String> getWorlds(){
		return worlds;
	}

	public static void addWorld(String name) {
		worlds.add(name);
	}

}
