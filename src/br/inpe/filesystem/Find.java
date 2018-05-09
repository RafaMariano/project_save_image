package br.inpe.filesystem;

import static java.nio.file.FileVisitResult.CONTINUE;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class Find {
	private Finder finder;
	private static Find find;
	
	private Find(){
		this.finder = new Finder();
	}
	
	public static synchronized Find getInstance() {
		if (find == null)
			find = new Find();

		return find;
	}
	private class Finder extends SimpleFileVisitor<Path>  {
		private ArrayList<String> files;


		public Finder(){
			this.files = new ArrayList<String>();

		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

			this.files.add(file.toFile().getAbsolutePath());
			return CONTINUE;
		}

		public ArrayList<String> getFiles() {
			return files;
		}

	}

	public ArrayList<String> searchImage(String pathImages) throws IOException{
		Files.walkFileTree(Paths.get(pathImages), this.finder);
		return this.finder.getFiles();
	}

}
