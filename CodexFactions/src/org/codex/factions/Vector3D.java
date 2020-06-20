package org.codex.factions;

public class Vector3D<A, B, C> {

	private A a;
	private B b;
	private C c;
	
	public Vector3D(A a, B b, C c) {
		this.setVectorOne(a);
		this.setVectorTwo(b);
		this.setVectorThree(c);
	}

	public A getVectorOne() {
		return a;
	}

	public void setVectorOne(A a) {
		this.a = a;
	}

	public B getVectorTwo() {
		return b;
	}

	public void setVectorTwo(B b) {
		this.b = b;
	}

	public C getVectorThree() {
		return c;
	}

	public void setVectorThree(C c) {
		this.c = c;
	}
	
	
}
