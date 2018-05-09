package br.inpe.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystem {

	private static FileSystem fileSystem;

	private FileSystem() {

	}

	public static synchronized FileSystem getInstance() {
		if (fileSystem == null)
			fileSystem = new FileSystem();

		return fileSystem;
	}

	public String createDir(String pathImage, String pathDB, String pathPrincipal) throws IOException {

		StringBuilder newPathImage = new StringBuilder(pathImage);

		if (pathImage.substring(0, pathPrincipal.length()).equals(pathPrincipal)){
			newPathImage.replace(0, pathPrincipal.length(), pathDB);
			String destination = newPathImage.toString();
			Files.createDirectories(Paths.get(destination));
			return destination;
		}
		else{
			return null;
		}
	}

	public void copyFile(String pathImage, String pathDestination)  throws IOException{
		
			Files.move(Paths.get(pathImage), Paths.get(pathDestination));
	}

	public String deletePath(String source, final String pathPrincipal) throws IOException {

		if (source.equals(pathPrincipal) == false) {

			File file = new File(source);
			if (file.list().length <= 0) {
				if (file.delete()) {
					// log
					return deletePath(file.getParent(), pathPrincipal);
				}
			}
		}
		return source;
	}

}
