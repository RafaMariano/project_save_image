package br.inpe.database;

import org.bson.Document;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

public class Query {

	public void insertDocument(Document image) {
		try {

//			MongoCollection<Document> collection = Mongo.getInstance().getDataBase().getCollection("inpe");
//			collection.insertOne(image);

		} catch (MongoWriteException mw) {
			// System.err.println("Está imagem já existe cadastrado no banco " +
			// mw);
		} catch (MongoSocketOpenException ms) {
			// System.err.println(" " + ms);
		}
	}

}
