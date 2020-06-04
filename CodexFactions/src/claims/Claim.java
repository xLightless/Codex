package claims;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

public interface Claim {
	
	public void onEnter(Entity e);
	
	public void onLeave(Entity e);
	
	public Chunk getChunk();
	
}
