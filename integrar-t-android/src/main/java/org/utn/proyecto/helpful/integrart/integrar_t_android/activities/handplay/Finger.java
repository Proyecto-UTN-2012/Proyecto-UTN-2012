package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

public class Finger {
	private final FingerNames name;
	private final FingerPoint points;
	
	public Finger(FingerNames name, FingerPoint points){
		this.name = name;
		this.points = points;
	}

	public FingerNames getName() {
		return name;
	}
	
	public FingerPoint getPoints(){
		return points;
	}
	
	public float getX(){
		return points.getX();
	}
	
	public float getY(){
		return points.getY();
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Finger)) return false;
		Finger finger = (Finger)o;
		return name == finger.name && points.equals(finger.points);
	}
	
	@Override
	public int hashCode(){
		return name.ordinal() + (int)getX() - (int)getY();
	}
	
	@Override
	public String toString(){
		return "Finger{ name: " + name.toString() + ", x: " + getX() + ", y: " + getY() + "}";
	}
}
