package org.utn.proyecto.helpful.integrart.core.fileresources;

import java.util.Arrays;
import java.util.Collection;

public class SimpleObjectWithCollection extends SimpleObject {

	private final Collection<?> collection;
	
	@SuppressWarnings("unchecked")
	public SimpleObjectWithCollection(String sValue, int iValue) {
		super(sValue, iValue);
		collection = Arrays.asList(sValue, iValue);
	}

	public Collection<?> getCollection() {
		return collection;
	}

}
