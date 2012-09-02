package org.utn.proyecto.helpful.integrart.web.settings.services;
import java.util.List;

import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;
import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.web.settings.domain.ResourceType;
import org.utn.proyecto.helpful.integrart.web.settings.services.FileUploadForm;

import com.google.inject.Inject;


public class UpdateResourcesService {
	private final FileResourceService fileService;
	private final PersisterService persisterService;
	private final ResourceType type;
	
	@Inject
	public UpdateResourcesService(FileResourceService fileService, PersisterService persisterService, ResourceType type){
		this.fileService = fileService;
		this.persisterService = persisterService;
		this.type = type;
	}
	
	public void addResource(FileUploadForm form, String userId, String activityName, String activitySection, String resourceName){
		ActivityResource activity = new ActivityResource(userId, activityName, resourceName, type);
		addActivityResource(form, activity);
	}
	
	protected void addActivityResource(FileUploadForm form, ActivityResource activity){
		this.fileService.uploadFile(form, activity.getPath());
		this.persisterService.insert(activity);
	}
	
	public void addResource(FileUploadForm form, String userId, String activityName, String activitySection){
		ActivityResource activity = buildActivity(userId, activityName, activitySection, type);
		this.fileService.uploadFile(form, activity.getPath());
		addActivityResource(form, activity);
	}
	
	public List<ActivityResource> getResources(String userId, String activityName){
		ActivityResource example = new ActivityResource(userId, activityName, null);
		List<ActivityResource> list = persisterService.find(example, new String[]{"userId", "activityName"});
		return list;
	}
	
	public void updateResources(List<ActivityResource> resources){
		for(ActivityResource resource : resources){
			persisterService.update(resource);
		}
	}
	
	private ActivityResource buildActivity(String userId, String activityName, String activitySection, ResourceType type){
		ActivityResource example = new ActivityResource(userId, activityName, activitySection, null, type);
		long resourceNumber = persisterService.count(example, 
				new String[]{"userId", "activityName", "activitySection", "resourceType"});
		String name = type.name() + resourceNumber + type.getResourceExtension();
		return new ActivityResource(userId, activityName, activitySection, name, type);
	}	
}
