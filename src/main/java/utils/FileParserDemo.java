package utils;

import java.util.List;

public class FileParserDemo {

	public static void main(String[] args) {
		getPoloURL();

	}
	
	static void getEtfURL(){
		List<String> etfs = FileParser.generateETFYahooURL();
		
		for(int i = 0; i < etfs.size();i++){
			System.out.println(etfs.get(i));
		}
		
	}
	
	static void getPoloURL(){
		List<String> poloUrls = FileParser.generatePoloURL();
		
		for(int i = 0; i < poloUrls.size();i++){
			System.out.println(poloUrls.get(i));
		}
	}

}
