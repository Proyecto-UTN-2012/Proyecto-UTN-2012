package org.utn.proyecto.helpful.integrart.core.fileresources;

public class ComplexObject extends SimpleObject {
	private final SimpleObject object;
	public ComplexObject(String sValue, int iValue) {
		super(sValue, iValue);
		object = new SimpleObjectWithCollection(sValue, iValue);
	}
	
	public SimpleObject getObject() {
		return object;
	}

}
