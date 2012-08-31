package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

public class PictogramData {
	private String name;
	private int[] levels;
	
	public PictogramData(String name, int[] levels){
		this.name = name;
		this.levels = levels;
	}
	
	public String getName() {
		return name;
	}

	public int[] getLevels() {
		return levels;
	}
}
