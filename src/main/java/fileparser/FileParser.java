package fileparser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
	
	public static List<String> readTextFileByLines(String fileName) throws IOException{
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		return lines;
	}
	
	public static void getCurrentDirectory(){
		System.out.println(System.getProperty("user.dir"));
	}
	
	public static List<String> readYahooStockFileByLines(String fileName) throws IOException{
		String csv = "StockFiles/" + fileName + ".csv";
		List<String> lines = Files.readAllLines(Paths.get(csv));
		lines.remove(0);
		return lines;
	}
	
	public static List<String> readFedForexByLines(String fileName) throws IOException{
	List<String> lines = Files.readAllLines(Paths.get("StockFiles/" + fileName));
	lines.remove(0);
		return lines;
	}
	
	public static List<String> readStockTickerList() throws IOException{
		List<String> lines = Files.readAllLines(Paths.get("StockFiles/SP500.csv"));
		return lines;
	}
	
	public static List<String> readETFList() throws IOException{
		List<String> lines = Files.readAllLines(Paths.get("StockFiles/TopEtfs.csv"));
		return lines;
	}
	
	public static List<String> generateYahooURL() {
		try {
			List<String> etfs = readETFList();
			List<String> urls = new ArrayList<>();
			for(int i = 0; i < etfs.size();i++){
				String url = "http://chart.finance.yahoo.com/table.csv?s=" +etfs.get(i)+"&a=3&b=5&c=2012&d=3&e=5&f=2017&g=d&ignore=.csv";
				urls.add(url);
			}
			return urls;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
}
