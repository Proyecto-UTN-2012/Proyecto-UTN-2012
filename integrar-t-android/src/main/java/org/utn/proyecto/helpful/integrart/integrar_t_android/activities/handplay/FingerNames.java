package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

public enum FingerNames {
	PULGAR(0),
	INDICE(1),
	MAYOR(2),
	ANULAR(3),
	MENIQUE(4);
	
	private final int id;
	private final static int[] strings = new int[]{
		R.string.pulgar, 
		R.string.indice, 
		R.string.mayor, 
		R.string.anular, 
		R.string.menique}; 

	
	private FingerNames(int id){
		this.id = id;
	}
	
	public int getNameId(){
		return strings[id];
	}
}
