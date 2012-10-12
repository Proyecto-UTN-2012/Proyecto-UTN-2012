package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

public class Finger{
	private final float x;
	private final float y;
	
	public Finger(float x, float y){
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Finger)) return false;
		Finger f = (Finger)o;
		return f.x == x && f.y == y;
	}
	
	@Override
	public int hashCode(){
		return ((Float)x).intValue() + ((Float)y).intValue();
	}
	
	@Override
	public String toString(){
		return "Finger{" + x + "," + y + "}";
	}
}