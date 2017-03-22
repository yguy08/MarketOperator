package vault;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import operator.TradingSystem;

public class BaseLogic extends Application {
	
	public static void main(String[] args) {
        //launch(args);
        try {
			TradingSystem.marketsToWatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Stock Market Operator");
        GridPane gridpane = new GridPane();
        gridpane.getColumnConstraints().add(new ColumnConstraints(200));
        gridpane.getColumnConstraints().add(new ColumnConstraints(200));
        gridpane.getColumnConstraints().add(new ColumnConstraints(200));        
        
        List<CurrencyPair> marketList = TradingSystem.marketList;
        List<String> marketNames = new ArrayList<>();
        for(int x = 0; x < marketList.size(); x++){
        	marketNames.add(marketList.get(x).toString());
        }
        
        ObservableList<String> markets = FXCollections.observableArrayList(marketNames);
        ListView<String> listView = new ListView<String>(markets);
        GridPane.setColumnIndex(listView, 0);
        
        gridpane.getChildren().addAll(listView);      
                
        primaryStage.setScene(new Scene(gridpane, 600, 600));
        primaryStage.show();
    }

}
