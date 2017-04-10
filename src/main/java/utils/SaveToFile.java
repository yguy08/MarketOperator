package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SaveToFile {
	
	public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get("Results/" + fileName + ".txt"), content.getBytes(), StandardOpenOption.CREATE);
	}

}
