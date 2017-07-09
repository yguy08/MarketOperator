package util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileParser {
	
	public static List<?> readTextFileByLines(String fileName) throws IOException{
		List<?> lines = Files.readAllLines(Paths.get(fileName));
		return lines;
	}
	
}
