package vault;

import java.io.IOException;

import backtest.BackTest;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import market.Market;
import market.MarketFactory;
import speculate.Speculate;
import speculate.SpeculateFactory;
import utils.SaveToFile;

public class Main extends Application  {
	
	BackTest backtest;
	Speculate speculate;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
	
		primaryStage.setTitle("Market Operator");
		
		//border pane
		BorderPane borderPane = new BorderPane();
		
		//list and load data by calling speculate
		final ObservableList<String> stats = FXCollections.observableArrayList();
		ListView<String> statList = new ListView<>(stats);
		BorderPane.setAlignment(statList,  Pos.CENTER_LEFT);
		borderPane.setCenter(statList);
	     
		//header top
		Text title = setTitle();
		BorderPane.setAlignment(title,  Pos.CENTER);
		borderPane.setTop(title);
				
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		
		Button viewOpen = new Button("View Open");
		viewOpen.setPrefSize(200, 50);
		viewOpen.setVisible(false);
		Button viewClose = new Button("View Close");
		viewClose.setPrefSize(200,50);
		viewClose.setVisible(false);
		Button backTest = new Button("Back Test");
		backTest.setPrefSize(200,50);
		backTest.setVisible(false);
		Button clearBtn = new Button("Clear");
		clearBtn.setPrefSize(200, 50);
		clearBtn.setVisible(false);
		Button saveBtn = new Button("Save");
		saveBtn.setPrefSize(200, 50);
		saveBtn.setVisible(false);
		hbox.getChildren().addAll(viewOpen,viewClose,backTest,clearBtn,saveBtn);
		
		//load data in background
		Runnable r = new Runnable() {
	         public void run() {
	     		speculate = speculate();
	     		viewOpen.setVisible(true);
	     		viewClose.setVisible(true);
	     		backTest.setVisible(true);
	     		clearBtn.setVisible(true);
	     		saveBtn.setVisible(true);
	     	}
	     };

	     new Thread(r).start();
		
		borderPane.setBottom(hbox);
		
		Scene scene = new Scene(borderPane, 1250, 750);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		viewOpen.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	String marketName = statList.getSelectionModel().getSelectedItem();
		    	MarketFactory marketFactory = new MarketFactory();
		    	Market market = marketFactory.createMarket(marketName);
		    	SpeculateFactory speculateFactory = new SpeculateFactory();
		    	stats.removeAll(stats);
		    	Speculate speculate  = speculateFactory.startSpeculating(market);
		    	speculate.getAllOpenPositionsWithEntry(market, stats);
		    }
		});
		
		viewClose.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	String marketName = statList.getSelectionModel().getSelectedItem();
		    	MarketFactory marketFactory = new MarketFactory();
		    	Market market = marketFactory.createMarket(marketName);
		    	SpeculateFactory speculateFactory = new SpeculateFactory();
		    	stats.removeAll(stats);
		    	Speculate speculate  = speculateFactory.startSpeculating(market);
		    	speculate.getPositionsToCloseSingleMarket(market,stats);
		    }
		});
		
		backTest.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	stats.removeAll(stats);
		    	speculate.newBackTest(speculate.getMarket(), speculate, stats);
		    }
		});
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	StringBuilder sb = new StringBuilder();
		    	for(int x = 0; x < stats.size();x++){
		    		sb.append(stats.get(x).toString() + "\n");
		    	}
		    	
		    	String results = sb.toString();
		    	long date = System.currentTimeMillis();
		    	try {
					SaveToFile.writeToTextFile(Long.toString(date), results);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    }
		});
		
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	stats.removeAll(stats);
		    	stats.addAll(Market.DIGITAL_MARKET, Market.STOCK_MARKET);
		    }
		});
		
	}
	
	public Text setTitle(){
		Text title = new Text("Market Operator");
		title.setFill(Color.GREEN);
		title.setFont(Font.font(null, FontWeight.NORMAL, 24));
		return title;
	}
	
	static Speculate speculate() {
		MarketFactory marketFactory = new MarketFactory();
		Market market = marketFactory.createMarket(Market.DIGITAL_MARKET);
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate  = speculateFactory.startSpeculating(market);
		speculate.newNewBackTest(market, speculate);
		return speculate;
		
	}
	
}
