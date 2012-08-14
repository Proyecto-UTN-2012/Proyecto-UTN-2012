package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.Resource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ResourceType;

public class FileSystemMappingTest{
	private final static String REPOSITORY = "resource.";
	private final static String MAIN_PACKAGE = "main";
	
	private FileSystemDataStorage service;
	private DataStorageService db;
	@Before
	public void setUp() throws Exception {
		db = new DataStorageMock();
		service = new FileSystemDataStorage(db);
	}

	@Test
	public void testAddPackageTestUser() {
		service.addPackage("user1", "activity1", "package1");
		String[] activities = db.get(REPOSITORY + "user1", String[].class);
		assertTrue(activities.length == 1 && activities[0].equals("activity1"));
	}
	
	@Test
	public void testAddPackageTestActivity() {
		service.addPackage("user1", "activity1", "package1");
		String[] activities = db.get(REPOSITORY + "user1.activity1", String[].class);
		assertTrue(activities.length == 2 
				&& activities[0].equals(MAIN_PACKAGE) && activities[1].equals("package1"));
	}
	
	@Test
	public void testAddResourceTestUser(){
		service.addResource("user1", "activity1", "package1",new Resource<Void>("IMAGE1", ResourceType.IMAGE));
		String[] activities = db.get(REPOSITORY + "user1", String[].class);
		assertTrue(activities.length == 1 
				&& activities[0].equals("activity1"));
	}
	
	@Test
	public void testAddResourceTestActivity(){
		service.addResource("user1", "activity1", "package1",new Resource<Void>("IMAGE1", ResourceType.IMAGE));
		String[] activities = db.get(REPOSITORY + "user1.activity1", String[].class);
		assertTrue(activities.length == 2 
				&& activities[0].equals("main")
				&& activities[1].equals("package1"));
	}
	
	@Test
	public void testAddResourceTestPackage(){
		Resource<Void> r = new Resource<Void>("IMAGE1", ResourceType.IMAGE);
		service.addResource("user1", "activity1", "package1", r);
		Resource<?>[] activities = db.get(REPOSITORY + "user1.activity1.package1", Resource[].class);
		assertTrue(activities.length == 1 && activities[0].equals(r));
	}

	@Test
	public void testAddResourceWithoutPackage(){
		Resource<Void> r = new Resource<Void>("IMAGE1", ResourceType.IMAGE);
		service.addResource("user1", "activity1", r);
		Resource<?>[] activities = db.get(REPOSITORY + "user1.activity1.main", Resource[].class);
		assertTrue(activities.length == 1 && activities[0].equals(r));
	}
	
	@Test
	public void testGetPackages(){
		Resource<Void> r = new Resource<Void>("IMAGE1", ResourceType.IMAGE);
		service.addResource("user1", "activity1", "package1", r);
		assertEquals(Arrays.asList("package1"), service.getPackages("user1", "activity1"));
	}

	@Test
	public void testGetResourcesByPackage(){
		Resource<Void> r = new Resource<Void>("IMAGE1", ResourceType.IMAGE);
		service.addResource("user1", "activity1", "package1", r);
		Resource<?>[] resources = service.getResources("user1", "activity1", "package1");
		assertTrue(resources.length == 1 && resources[0].equals(r));
	}

	@Test
	public void testGetResourcesWithoutPackage(){
		Resource<Void> r1 = new Resource<Void>("IMAGE1", ResourceType.IMAGE);
		Resource<Void> r2 = new Resource<Void>("IMAGE2", ResourceType.IMAGE);
		service.addResource("user1", "activity1", "package1", r1);
		service.addResource("user1", "activity1", r2);
		Resource<?>[] resources = service.getResources("user1", "activity1");
		assertTrue(resources.length == 1 && resources[0].equals(r2));
	}

	@Test
	public void testGetAllResources(){
		Resource<Void> r1 = new Resource<Void>("IMAGE1", ResourceType.IMAGE);
		Resource<Void> r2 = new Resource<Void>("IMAGE2", ResourceType.IMAGE);
		service.addResource("user1", "activity1", "package1", r1);
		service.addResource("user1", "activity1", r2);
		Resource<?>[] resources = service.getAllResources("user1", "activity1");
		assertTrue(resources.length == 2);
	}

}
