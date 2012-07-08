package org.utn.proyecto.helpful.integrart.core.fileresources;

public class DefaultFileResourceService implements FileResourceService {

	private final FileResourcePersister persister;
	public DefaultFileResourceService(FileResourcePersister persister){
		this.persister = persister;
	}
	public void uploadFile(FileUploadData fileUpload, String path, String name) {
		persister.save(fileUpload.getData(), path, name);
	}
	public void uploadFile(FileUploadData fileUpload, String path) {
		persister.save(fileUpload.getData(), path);
	}
}
