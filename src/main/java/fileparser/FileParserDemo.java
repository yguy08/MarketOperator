package fileparser;

import java.util.List;

public class FileParserDemo {

	public static void main(String[] args) {
		List<String> etfs = FileParser.generateYahooURL();
		
		for(int i = 0; i < etfs.size();i++){
			System.out.println(etfs.get(i));
		}

	}

}
