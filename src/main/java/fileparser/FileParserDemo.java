package fileparser;

import java.util.List;

public class FileParserDemo {

	public static void main(String[] args) {
		getEtfURL();

	}
	
	static void getEtfURL(){
		List<String> etfs = FileParser.generateETFYahooURL();
		
		for(int i = 0; i < etfs.size();i++){
			System.out.println(etfs.get(i));
		}
		
	}
	
	static void getForexUrl(){
		List<String> forex = FileParser.generateForexYahooURL();
		
		for(int i = 0; i < forex.size();i++){
			System.out.println(forex.get(i));
		}
	}

}
