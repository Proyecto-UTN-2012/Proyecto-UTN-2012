package org.utn.proyecto.helpful.integrart.core.fileresources;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.utn.proyecto.helpful.integrart.core.percistence.GsonMongoMapper;

import com.mongodb.DBObject;


public class MongoMapperTest {
	private final GsonMongoMapper mapper = new GsonMongoMapper();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMapSinpleObject() {
		SimpleObject o = new SimpleObject("A", 3);
		DBObject object = mapper.toDbObject(o);
		o = mapper.fromDbObject(object, SimpleObject.class);
		assertEquals(new SimpleObject("A", 3), o);
	}
	
	@Test
	public void testMapSimpleObjectWithCollection(){
		SimpleObject o = new SimpleObjectWithCollection("A", 3);
		DBObject object = mapper.toDbObject(o);
		o = mapper.fromDbObject(object, SimpleObjectWithCollection.class);
		assertEquals(new SimpleObjectWithCollection("A", 3), o);
	}
	
	@Test
	public void testMapComplexObject(){
		SimpleObject o = new ComplexObject("A", 3);
		DBObject object = mapper.toDbObject(o);
		o = mapper.fromDbObject(object, ComplexObject.class);
		assertEquals(new ComplexObject("A", 3), o);
	}
	
	@Test
	public void testMapList(){
		List<SimpleObject> list = Arrays.asList(new SimpleObject("A", 3), new SimpleObject("B", 4));
		List<DBObject> dbList = new ArrayList<DBObject>();
		for(SimpleObject object : list){
			dbList.add(mapper.toDbObject(object));
			
		}
		assertEquals(list, mapper.fromDbObject(dbList, SimpleObject.class));
	}

}
