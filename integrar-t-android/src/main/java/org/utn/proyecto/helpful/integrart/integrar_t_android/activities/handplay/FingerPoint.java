package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

public class FingerPoint{
	private final float x;
	private final float y;
	
	public FingerPoint(float x, float y){
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float range(FingerPoint point){
		return (float)Math.sqrt((double)((x - point.x)*(x - point.x) + (y - point.y)*(y - point.y))); 
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof FingerPoint)) return false;
		FingerPoint f = (FingerPoint)o;
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