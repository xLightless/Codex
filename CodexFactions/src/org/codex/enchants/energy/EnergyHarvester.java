package org.codex.enchants.energy;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.codex.factions.FactionsMain;
import org.codex.factions.ID;


@SerializableAs("EnergyHarvester")
public class EnergyHarvester implements ConfigurationSerializable{

	private ID id = new ID();
	private int xPos;
	private int yPos;
	private int zPos;
	private double energy;
	private String type;
	private String world;
	private boolean harvesting = true;
	private int taskID = 0;
	private UUID uuid;
	
	
	public EnergyHarvester(String world, int xPos, int yPos, int zPos, String string, double energy) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.type = string;
		this.energy = energy;
		this.world = world;
		id.generateNewID();
		this.startHarvesting();
	}


	public EnergyHarvester(Map<String, Object> map) {
	      this.xPos = (int) map.get("x");
	      this.yPos = (int) map.get("y");
	      this.zPos = (int) map.get("z");
	      this.type =  (String) map.get("type");
	      this.energy = (double) map.get("nrg");
	      this.world = (String) map.get("world");
	      this.uuid = UUID.fromString((String) map.get("uuid"));
	      this.id = new ID((int) map.get("id"));
	      this.startHarvesting();
	   }
	
	public double getEnergy() {
		return energy;
	}
	
	public EnergyHarvesterType getType() {
		return EnergyHarvesterType.value(type);
	}
	
	public World getWorld() {
		return Bukkit.getWorld(world) == null ? null : Bukkit.getWorld(world);
	}
	
	public ID getId() {
		return id;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getzPos() {
		return zPos;
	}
	
	public boolean isHarvesting() {
		return harvesting;
	}
	
	
	public Map<String, Object> serialize() {
		
		
		Map<String, Object> map = new TreeMap<String, Object>();
		 map.put("x", xPos);  
	      map.put("y", yPos);
	      map.put("z", zPos);
	      map.put("type", type);
	      map.put("nrg", energy);
	      map.put("world", world);
	      map.put("uuid", this.uuid.toString());
	      map.put("id", this.getId().getId());
		return map;
	}
	
	private void startHarvesting() {
		double interval = 0.01;
	taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(FactionsMain.getMain(), new Runnable() {
		@Override
		public void run() {
			for(EnergyHarvester harvester :Energy.getYml().getMap().values()) {
			if(getWorld() == harvester.getWorld() && getHarvester() != harvester) {
			if(!isClose(new Location(getWorld(), getxPos(), getyPos(), getzPos()), new Location(harvester.getWorld(), harvester.getxPos(), harvester.getyPos(), harvester.getzPos()))) {
				harvesting = true;
			}else{
				harvesting = false;
				continue;
			}
			}else {
				harvesting = true;
			}
			}
			if(harvesting) {
				energy += EnergyHarvesterType.value(type).getSpeed() * interval * getActivationLevel();
				Energy.getYml().getMap().replace(getId().getId() + "", getHarvester());
				Energy.getYml().save();
			}else {
				
			}
			
		}
		
	}, ((long) (20 * 3600 * interval)), (long) (20 * 3600 * interval));
		
	}
	
	private boolean isClose(Location loc1, Location loc2) {
		if(loc1.distance(loc2) <= this.getType().getRadius())return true;
		else return false;
	}
	
	private EnergyHarvester getHarvester() {
		return this;
	}
	
	public int getTaskID() {
		return taskID;
	}
	
	@Override 
	public String toString() {
		return this.xPos + ", " + this.yPos + ", " + this.zPos + ", " + this.world + ", " + this.type;
	}


	public UUID getUUID() {
		return uuid;
	}

	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	
	
	public double getActivationLevel() {
		EnergyHarvesterType t = this.getType();
		Location topLeftCorner = new Location(this.getWorld(), this.getxPos() + t.getRadius(), this.getyPos(), this.getzPos() + t.getRadius());
		Location bottomRightCorner = new Location(this.getWorld(), this.getxPos() - t.getRadius(), this.getyPos(), this.getzPos() - t.getRadius());
		double amountMissing = 0;
		double totalAmount = (t.getRadius() * 8) + 4;
		for(int x = topLeftCorner.getBlockX(); x >= bottomRightCorner.getBlockX(); x--) {
			Material m1 = this.getWorld().getBlockAt(new Location(topLeftCorner.getWorld(), x, topLeftCorner.getBlockY(), topLeftCorner.getBlockZ())).getType();
			Material m2 = this.getWorld().getBlockAt(new Location(topLeftCorner.getWorld(), x, topLeftCorner.getBlockY(), bottomRightCorner.getBlockZ())).getType();
			if(!m1.equals(t.getHarvestingMaterial()))amountMissing++;
			if(!m2.equals(t.getHarvestingMaterial()))amountMissing++;
		}
		for(int z = topLeftCorner.getBlockZ(); z >= bottomRightCorner.getBlockZ(); z--) {
			Material m1 = this.getWorld().getBlockAt(new Location(topLeftCorner.getWorld(), topLeftCorner.getBlockX(), topLeftCorner.getBlockY(), z)).getType();
			Material m2 = this.getWorld().getBlockAt(new Location(topLeftCorner.getWorld(), bottomRightCorner.getBlockX(), topLeftCorner.getBlockY(), z)).getType();
			if(!m1.equals(t.getHarvestingMaterial()))amountMissing++;
			if(!m2.equals(t.getHarvestingMaterial()))amountMissing++;
		}
		return 3 - ((amountMissing/totalAmount) * 3);
	}
	
	
	
	
	
	
}
