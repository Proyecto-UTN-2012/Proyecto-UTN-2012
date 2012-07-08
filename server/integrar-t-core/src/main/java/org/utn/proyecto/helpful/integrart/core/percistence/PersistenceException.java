package org.utn.proyecto.helpful.integrart.core.percistence;

public class PersistenceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersistenceException(Class<?> clazz){
		super("The class " + clazz.getCanonicalName() + "don't has the Persistable annotation");
	}
}
