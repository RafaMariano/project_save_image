package br.inpe.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class Log {
	
	private File logFile = new File("/home/inpe/log/log.txt");
	private static Log logSingleton;

	private Log(){}
	
	public static synchronized Log getInstance(){
		if (logSingleton == null)
			logSingleton = new Log();
		
		return logSingleton;
	}


	public void write ( String msgError, String classError ){
		
		FileWriter fileWriter;
		try {
			
			fileWriter = new FileWriter(this.logFile,true);
			
			if(this.logFile.exists()){
				
				makeLog(readLog(fileWriter), msgError, classError);
			}
			else{
				
				makeLog(fileWriter, msgError, classError);
				
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			fileWriter = null;
		}
		
		
	}

	private void makeLog(FileWriter writer, String msgError, String classError ) throws IOException{

		writer = new FileWriter(this.logFile);

		writer.write("Problema:" + msgError + "\n");
		writer.write("Classe:" + classError + "\n");
		writer.write("Data:" + new Date());
		
		writer.flush();
		writer.close();
	}
	

	
	private FileWriter readLog(FileWriter  writer) throws IOException{
		
		FileReader reader = new FileReader(this.logFile);
		BufferedReader br = new BufferedReader(reader);
		String linha = br.readLine();

		while(linha != null)
		{
			writer.write(linha+"\n");
			br.readLine();
			linha = br.readLine();
		}

		br.close();
		reader.close();
		
		return writer;
	}


//	private String getMsgError() {
//		return msgError;
//	}
//
//
//	private String getClassError() {
//		return classError;
//	}


} 