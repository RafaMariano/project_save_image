package br.inpe.database;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Mongo {
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	private static Mongo mongo;
	
	private Mongo() throws UnknownHostException{
		this.mongoClient = new MongoClient();
	//	this.database = mongoClient.getDatabase("inpe");
	}
		
	public static synchronized Mongo getInstance() throws UnknownHostException{
		if (mongo == null)
			mongo = new Mongo();
		
		return mongo;
	}
	
	public MongoDatabase getDataBase(){
		return database;
	}

}
