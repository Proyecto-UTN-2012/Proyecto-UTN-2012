package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.Resource;

public class FileSystemDataStorage {
	private final static String REPOSITORY = "resource.";
	private final static String MAIN_PACKAGE = "main";
	
	private final DataStorageService db;
	
	public FileSystemDataStorage(DataStorageService db){
		this.db = db;
	}
	
	private boolean contain(String userName){
		String key = buildRepositoryKey(userName);
		return db.contain(key);
	}
	
	private boolean contain(String userName, String activityName){
		String key = buildRepositoryKey(userName, activityName);
		return db.contain(key);
	}
	
	private boolean contain(String userName, String activityName, String packageName){
		String key = buildRepositoryKey(userName, activityName, packageName);
		return db.contain(key);
	}
	
	
	private String buildRepositoryKey(String userName){
		return REPOSITORY + userName;
	}
	
	private String buildRepositoryKey(String userName, String activityName){
		return buildRepositoryKey(userName) + "." + activityName;
	}
	
	private String buildRepositoryKey(String userName, String activityName, String packageName){
		return buildRepositoryKey(userName, activityName) + "." + packageName;
	}
	
	private void addUser(String userName){
		String key = buildRepositoryKey(userName);
		db.put(key, new String[0]);
	}

	private void addActivity(String userName, String activityName){
		if(!contain(userName)) addUser(userName);
		Collection<String> activities = new ArrayList<String>();
		for(String activity : db.get(buildRepositoryKey(userName), String[].class)){
			activities.add(activity);
		}
		activities.add(activityName);
		db.put(buildRepositoryKey(userName), activities.toArray(new String[0]));
		db.put(buildRepositoryKey(userName, activityName), new String[]{MAIN_PACKAGE});
		db.put(buildRepositoryKey(userName, activityName, MAIN_PACKAGE), new Resource<?>[0]);
	}
	
	public void addPackage(String userName, String activityName, String packageName){
		if(contain(userName, activityName, packageName)); //TODO throw Exception
		if(!contain(userName, activityName)) addActivity(userName, activityName);
		Collection<String> packages = new ArrayList<String>();
		for(String _package : db.get(buildRepositoryKey(userName, activityName), String[].class)){
			packages.add(_package);
		}
		packages.add(packageName);
		db.put(buildRepositoryKey(userName, activityName), packages.toArray(new String[0]));
		db.put(buildRepositoryKey(userName, activityName, packageName), new Resource<?>[0]);
		
	}
	
	public void addResource(String userName, String activityName,
			String packageName, Resource<?> resource){
		resource = resource.copy();
		resource.setResource(null);

		String key = buildRepositoryKey(userName, activityName, packageName);
		if(!contain(userName, activityName, packageName)){
			addPackage(userName, activityName, packageName);
		}
		Collection<Resource<?>> resources = new ArrayList<Resource<?>>();
		Resource<?>[] _resources = db.get(key, Resource[].class);
		for(Resource<?> res : _resources){
			resources.add(res);
		}			
		resources.add(resource);
		db.put(key, resources.toArray(new Resource<?>[0]));
	}
	
	public void addResource(String userName, String activityName, Resource<?> resource){
		addResource(userName, activityName, MAIN_PACKAGE, resource);
	}
	
	public List<String> getPackages(String userName, String activityName){
		if(!contain(userName, activityName)) addActivity(userName, activityName);
		String key = buildRepositoryKey(userName, activityName);
		String[] sPackages = db.get(key, String[].class);
		List<String> packages = new ArrayList<String>();
		for(String _package : sPackages){
			if(!_package.equals(MAIN_PACKAGE))
				packages.add(_package);
		}
		return packages;
		
	}
	
	public Resource<?>[] getResources(String userName, String activityName, String packageName){
		String key  = buildRepositoryKey(userName, activityName, packageName);
		return db.get(key, Resource[].class);
	}
	
	public Resource<?>[] getResources(String userName, String activityName){
		String key  = buildRepositoryKey(userName, activityName, MAIN_PACKAGE);
		return db.get(key, Resource[].class);
	}
	
	public Resource<?>[] getAllResources(String userName, String activityName){
		Collection<Resource<?>> resources = new ArrayList<Resource<?>>();
		String[] packages = db.get(buildRepositoryKey(userName, activityName), String[].class);
		for(String _package : packages){
			addResourcesToCollection(resources, getResources(userName, activityName, _package));
		}
		return resources.toArray(new Resource<?>[0]);
	}
	
	private void addResourcesToCollection(Collection<Resource<?>> collection, Resource<?>[] resources){
		for(Resource<?> resource : resources){
			collection.add(resource);
		}
	}
}
