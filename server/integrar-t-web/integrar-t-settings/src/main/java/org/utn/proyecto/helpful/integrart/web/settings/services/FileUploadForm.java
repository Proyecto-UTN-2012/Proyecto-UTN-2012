package org.utn.proyecto.helpful.integrart.web.settings.services;

import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import org.utn.proyecto.helpful.integrart.core.fileresources.FileUploadData;

public class FileUploadForm implements FileUploadData {
	private byte[] data;
	
	public FileUploadForm(){}
	
	public FileUploadForm(byte[] data){
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}

	@FormParam("uploadedFile")
	@PartType("application/octet-stream")
	public void setData(byte[] data) {
		this.data = data;
	}

}
