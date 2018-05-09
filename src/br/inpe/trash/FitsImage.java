package br.inpe.trash;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Calendar;
import org.bson.Document;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.Header;
import nom.tam.fits.HeaderCard;
import nom.tam.util.Cursor;

public class FitsImage {

	private Fits fits;
	private Document collection;
	private StringBuilder sb;
	private FormatDecimal formatDecimal;

	public FitsImage(String image, int length, String newPath, FormatDecimal formatDecimal) throws ParseException {		

		try{
	
			this.sb = new StringBuilder(image);
			this.fits = new Fits(image);
			this.formatDecimal = formatDecimal;

			this.collection = setDocument(new Document());
			//alterar
			this.collection.put("FileSystem", copyFile( image, createDir(this.sb, length, new StringBuilder(newPath)) ));
			deletePath(this.sb.substring(0, this.sb.lastIndexOf("/")), this.sb.substring(0, length-1));

			this.collection.put("_id", image.substring(image.lastIndexOf("/")+1));
			this.fits.close();

		}
		catch(StringIndexOutOfBoundsException a){
			System.out.println("Imagem em um caminho inválido:     " + image);
			//adicionar no log

		}catch (FitsException fits){
			System.out.println("Imagem fits com problema. Corrompido? É uma imagem Fits?   " + image);
			System.out.println(fits);
		}
		catch(IOException io){
			System.out.println("Error: "+ io);
			//adicionar no log
		}

	}

	private String createDir(StringBuilder path, int length, StringBuilder newPath) throws IOException{
		
		//Colocando o caminho da imagem sem o nome no /home/inpe/Database/
		newPath.append(path.substring(length, path.lastIndexOf("/")));
		//Criando os diretorios
		Files.createDirectories(Paths.get(newPath.toString()));
		//Adicionando o nome da imagem ao caminho
		newPath.append(path.substring(path.lastIndexOf("/")));
		
		return newPath.toString();
	}

	private String copyFile(String source, String destination) throws IOException {

		Files.move(Paths.get(source), Paths.get(destination));
		return destination;
	}

	
	private String deletePath(String source, final String path) throws IOException{

		if(source.equals(path) == false){

			File file = new File(source);
			if (file.list().length <= 0){
				if (file.delete()){
					//log
					return deletePath (file.getParent(), path);
				}
			}
		}
		return source;
	}
	

	private Document setDocument(Document document) throws FitsException, IOException, ParseException{
		Header header = this.fits.getHDU(0).getHeader();

		Cursor<String, HeaderCard> c = header.iterator();

		while(c.hasNext() ){
			HeaderCard bb = c.next();

			if(bb.getKey().equals("END") == false){

				if (bb.getKey() != null){
					if (bb.getKey().isEmpty() == false){

						if (bb.getKey().equals("COMMENT")){
							document.put(bb.getKey(),bb.getComment());
						}
						else if(bb.getValue() != null) {
							document.put(bb.getKey(), bb.getValue());

						}
						else {
							document.put(bb.getKey(),"");

						}
					}
				}
			}

		}	
		setTimeAndDay(document);

		return document;
	}

	//Vai ser retirado na versao final
	private void setTime(String time) throws ParseException{
		this.formatDecimal.setTime(time);
	}
	
	//Vai ser retirado na versao final
	private int getTime(int calendar){
		return this.formatDecimal.getTime(calendar);
	}

	//Vai ser retirado na versao final
	private String getSecondMilliToFloat(int second, int milli){
		return this.formatDecimal.setFloat(second, milli);
	}

	//Vai ser retirado na versao final
	private void setTimeAndDay(Document document) throws ParseException{

		if (document.containsKey("DATE-OBS")){

			setTime(document.get("DATE-OBS").toString());  

			document.append("DAY",getTime(Calendar.DAY_OF_MONTH));
			document.append("MONTH",getTime( Calendar.MONTH) + 1);
			document.append("YEAR", getTime(Calendar.YEAR));
			document.append("HOUR", getTime(Calendar.HOUR));
			document.append("MINUTE", getTime(Calendar.MINUTE));
			
			document.append("SECOND",  getSecondMilliToFloat(getTime(Calendar.SECOND), getTime(Calendar.MILLISECOND)));


		}
			else if (document.containsKey("DATE") ){
					setTime(document.get("DATE").toString());  
					document.append("DAY",getTime(Calendar.DAY_OF_MONTH));
					document.append("MONTH",getTime( Calendar.MONTH) + 1);
					document.append("YEAR", getTime(Calendar.YEAR));
					document.append("HOUR", getTime(Calendar.HOUR));
					document.append("MINUTE", getTime(Calendar.MINUTE));
					
					document.append("SECOND",  getSecondMilliToFloat(getTime(Calendar.SECOND), getTime(Calendar.MILLISECOND)));
			
				}
	}

	public Document getDocument(){
		return this.collection;
	}

}
