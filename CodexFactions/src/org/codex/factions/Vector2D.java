package org.codex.factions;

import java.io.Serializable;

public class Vector2D<A, B> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 141234121242L;
	
	private A a;
	private B b;
	
	public Vector2D(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public void setVectorOne(A a) {
		this.a = a;
	}
	
	public A getVectorOne() {
		return this.a;
	}
	
	public void setVectorTwo(B b) {
		this.b = b;
	}
	
	public B getVectorTwo(){
		return b;
	}
	
	
}
