package br.inpe.filesystem;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
public class ImagesFits {
	@Id
	private String id;

	private org.bson.Document document;

	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public org.bson.Document getDocument(){
		return document;
	}
	public void setDocument(org.bson.Document document){
		this.document = document;
	}
}
