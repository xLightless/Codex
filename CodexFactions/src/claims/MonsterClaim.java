package claims;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

public class MonsterClaim implements Claim{

	private Chunk c;
	
	public MonsterClaim(Chunk c) {
		this.c = c;
	}
	
	
	@Override
	public void onEnter(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeave(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Chunk getChunk() {
		return c;
	}

}
