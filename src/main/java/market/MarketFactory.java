package market;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class MarketFactory {
	
	//called when app first loads
	public static Market createMarket(){
    	//try internet connection to decide online/offline
		try {
            URL myURL = new URL("https://poloniex.com/");
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            return BitcoinMarket.createOnlineBitcoinMarket();
        } 
        catch (IOException e) {
            return BitcoinMarket.createOfflineBitcoinMarket();
        }
	}
	
	//create market (String marketName)
	
	//create online dollar market
	
	//create offline dollar market
	
	//create online ethereum market
	
	//create offline ethereum market	
	
	
}
