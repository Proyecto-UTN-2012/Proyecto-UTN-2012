package org.utn.proyecto.helpful.integrart.core.fileresources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class LocalDriveFileResourcePersister implements FileResourcePersister {
	private final String FILE_SEPARATOR = System.getProperty("file.separator");
	private final String basePath;
	
	public LocalDriveFileResourcePersister(String basePath){
		this.basePath = basePath;
	}
	
	private void save(byte[] bytes, File file){
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		FileOutputStream fop;
		try {
			fop = new FileOutputStream(file);
			fop.write(bytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void save(byte[] bytes, String path, String name) {
		File file = new File(basePath + FILE_SEPARATOR + path + FILE_SEPARATOR + name);
		save(bytes, file);
	}

	public void save(byte[] bytes, String path) {
		File file = new File(basePath + FILE_SEPARATOR + path);
		save(bytes, file);
	}

}
