package br.inpe.filesystem;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import br.inpe.database.Query;
import nom.tam.fits.FitsException;

public class Controller {
	private final String pathDB;
	private final String pathPrincipal;
	private Query query;


	public Controller(String pathPrincipal, String pathDB){
		this.pathPrincipal = pathPrincipal;
		this.pathDB = pathDB;
		this.query = new Query();

	}

	public ArrayList<String> getImages() throws IOException{
		return Find.getInstance().searchImage(this.pathPrincipal);	
	}

	public void sendToBD(ArrayList<String> pathImages) throws ParseException {
		
		for (String pathImage: pathImages){
			try{
				
				Image image = new Image(pathImage);
				
				String pathDestination = FileSystem.getInstance().createDir(pathImage, pathDB, pathPrincipal);
				FileSystem.getInstance().copyFile(pathImage, pathDestination);
				image.setKeyValue("FILESYSTEM", pathDestination);
				FileSystem.getInstance().deletePath(pathImage, pathDestination);
				
				
//				ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
//				MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");

				this.query.insertDocument(image.getDocument());
				
			}
			catch (Error e) {
				e.printStackTrace();

			} catch (FitsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//erro de puxar informacoes do fits
				e.printStackTrace();
			}
		}

	}

}
