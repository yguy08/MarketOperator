package vault;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import operator.Asset;
import operator.Entry;
import operator.TradingSystem;

public class BaseLogic extends Application {
	
	public static final Exchange poloniex 				= ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	public static final MarketDataService dataService 	= poloniex.getMarketDataService();;
	public static final List<CurrencyPair> marketList 	= poloniex.getExchangeSymbols();
	
	public static List<String> marketString;
	static List<PoloniexChartData> priceList;

	
	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Stock Market Operator");
        
        List<String> marketString = new ArrayList<>();
        for(int x = 0; x < marketList.size();x++){
        	marketString.add(marketList.get(x).toString());
        }
        
        ListView<String> allMarketList = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList (marketString);
        allMarketList.setItems(items);
 
        allMarketList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov, 
                        String old_val, String new_val) {
                    	try {
							TradingSystem.marketsToWatch();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                }
            });
        
        String assetName;
        List<String> names = new ArrayList<>();
        for(int i = 0; i < marketList.size();i++){
        	assetName = marketList.get(i).toString();
        	long dateFrom = new Date().getTime() / 1000 - (TradingSystem.HIGH_LOW * 24 * 60 * 60);
        	//long longerDate = new Date().getTime() / 1000 - (TradingSystem.HIGH_LOW * 2 * 24 * 60 * 60);
        	assetName = marketList.get(i).toString();
            priceList = TradingSystem.setCustomPriceList((PoloniexMarketDataServiceRaw) dataService, assetName, dateFrom);
    		Asset asset = new Asset(assetName, priceList);
    		Entry entry = new Entry(asset.getName(), asset.getPriceList());
    		if(entry.entryList.size() > 0){
    			names.add(assetName);
    		}
    	
        }        
      

        ListView<String> entryList = new ListView<String>();
        ObservableList<String> entryItems =FXCollections.observableArrayList (names);
        entryList.setItems(entryItems);
        //entryList.setPrefWidth(200);
        //entryList.setPrefHeight(600);
        
        StackPane root = new StackPane();
        root.getChildren().add(allMarketList);
        
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();
    }
}
