package org.utn.proyecto.helpful.integrart.core.fileresources;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.utn.proyecto.helpful.integrart.core.percistence.GsonMapper;
import org.utn.proyecto.helpful.integrart.core.percistence.JsonMapper;

public class GsonMapperTest {

	private JsonMapper mapper;
	@Before
	public void setUp() throws Exception {
		mapper = new GsonMapper();
	}
	
	@Test
	public void testDeserializePrimitive(){
		String json = mapper.toJson(1);
		Number value = mapper.fromJson(json);
		assertEquals("Deserialize Primitive fail", 1, value.intValue());
	}
	
	@Test
	public void testDeserializeCollection(){
		String json = mapper.toJson(Arrays.asList("a","b"));
		Collection<?> col = mapper.fromJson(json);
		assertEquals("Deserialize collection fail",Arrays.asList("a","b"),col);
	}
	
	@Test
	public void testDeserializeSimpleObject(){
		SimpleObject object = new SimpleObject("a", 1);
		String json = mapper.toJson(object);
		object = mapper.fromJson(json);
		assertEquals("Deserialize Simple Object fail", new SimpleObject("a", 1), object);
	}

	@Test
	public void testDeserializeObjectWithCollection(){
		SimpleObject object = new SimpleObjectWithCollection("a", 1);
		String json = mapper.toJson(object);
		object = mapper.fromJson(json);
		assertEquals("Deserialize Simple Object with collection fail", new SimpleObjectWithCollection("a", 1), object);
	}

	@Test
	public void testDeserializeComplexObject(){
		SimpleObject object = new ComplexObject("a", 1);
		String json = mapper.toJson(object);
		object = mapper.fromJson(json, ComplexObject.class);
		assertEquals("Deserialize Complex Object fail", new ComplexObject("a", 1), object);		
	}

}
