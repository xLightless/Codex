package org.codex.factions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ID {

	private static List<Integer> ids = new ArrayList<Integer>(); 
	private int id;
	
	public ID() {
	}
	
	public ID(int id) {
		this.id = id;
		ids.add(id);
	}
	
	public int generateNewID() {
		List<Integer> ids = ID.ids;
		Random r = new Random();
		int key = r.nextInt(ids.size()+1);
		if(ids.contains(key)) {
			return this.generateNewID();
		}else {
			ids.add(key);
			ID.ids = ids;
			this.id = (key);
			return key;
		}
		
	}

	public int getId() {
		return id;
	}


	
	
	
	
	
	
	
	
}
