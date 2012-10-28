package org.utn.proyecto.helpful.integrart.web.metrics.providers;

import org.utn.proyecto.helpful.integrart.core.percistence.MongoPersisterService;
import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class MongoDBProvider implements Provider<PersisterService> {
	private final String dbName;
	private final String dbHost;
	private final int dbPort;
	
	@Inject
	public MongoDBProvider(@Named("db.name") String dbName, @Named("db.host") String dbHost, @Named("db.port") int dbPort){
		this.dbName = dbName;
		this.dbHost = dbHost;
		this.dbPort = dbPort;
	}
	public PersisterService get() {
		return new MongoPersisterService(dbName, dbHost, dbPort);
	}

}
