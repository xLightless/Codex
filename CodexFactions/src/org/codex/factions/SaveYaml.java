package org.codex.factions;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;




public class SaveYaml<X> {

	private Map<String, X> map;
	private String fileName;
	private String name;
	
	public SaveYaml(String fileName, String name, Map<String, X> map) {
		this.fileName = fileName;
		this.name = name;
		this.map = map;
	}
	
	
	
	public void save() {
		YamlConfiguration config = new YamlConfiguration();
		config.createSection(name, map);
		try {
			
			config.save(new File(FactionsMain.getMain().getDataFolder(), fileName));
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public Map<String, X> getMap(){
		return map;
	}
	
	public void add(String key, X x) {
		map.put(key, x);
	}
	
	public void load() {
		try {
			File file = new File(FactionsMain.getMain().getDataFolder(), fileName);
			if(file.exists()) {
				YamlConfiguration config = new YamlConfiguration();
				config.load(file);
				ConfigurationSection sec = config.getConfigurationSection(name);
				if(sec.getKeys(false) != null) {
				for(String key : sec.getKeys(false)) { 
					@SuppressWarnings("unchecked")
					X x =  (X) sec.get(key);
					map.put(key, x);
					
					}
				Bukkit.getLogger().info(map.toString());
				}else {
					file.createNewFile();
				}
			}else {
				Bukkit.getLogger().warning("nothing is in the " + this.fileName + " file");
				}
			}catch (IOException | InvalidConfigurationException ex) {
				ex.printStackTrace();
		}
	}
	
	
}
