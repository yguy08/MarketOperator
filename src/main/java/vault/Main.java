package vault;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import market.Market;
import speculate.Speculate;
import speculate.SpeculateFactory;
import utils.SaveToFile;

public class Main extends Application  {
	
	Vault vault;
	
	ObservableList<String> resultsList = FXCollections.observableArrayList();
	ListView<String> statList;	
	
	BorderPane chooseMarketPane, speculatePane;
	Text speculateTitle;
	HBox chooseMarket, speculateMainHbox;
	Scene chooseMarketScene, speculateScene;
	Button btnStock, btnDigital, viewOpen, viewClose, backTest, newEntries, clearBtn, saveBtn, btnPoloOffline;
	Stage chooseMarketStage, speculateStage;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		//set main stage to access outside of method
		speculateStage = primaryStage;
		
		//set choose market pop-up
		setChooseMarketPopup();
		
		//set main stage
		setMainStage();
		
		//start-up
		primaryStage.setScene(speculateScene);
		primaryStage.show();
		
		chooseMarketStage.showAndWait();
		
	}
	
    public void ButtonClicked(ActionEvent e){
    	String marketName;
        if (e.getSource() == btnStock){
        	chooseMarketStage.close();
        	marketName = Market.STOCK_MARKET;
        }else if (e.getSource() == btnDigital){
        	chooseMarketStage.close();
        	marketName = Market.DIGITAL_MARKET;
        }else{
        	chooseMarketStage.close();
        	marketName = Market.POLONIEX_OFFLINE;
        }
        
		//load data in background (for digital currencies)
		Runnable r = new Runnable() {
	         public void run() {
	     		vault = new Vault(marketName, resultsList);
	     		viewOpen.setVisible(true);
	     		viewClose.setVisible(true);
	     		backTest.setVisible(true);
	     		newEntries.setVisible(true);
	     		clearBtn.setVisible(true);
	     		saveBtn.setVisible(true);
	     	}
	     };
	     
	     new Thread(r).start();
    }
    
    public void viewOpenClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(this.vault.market);
    	speculate.getAllOpenPositions(this.vault, speculate);
    }
    
    public void viewCloseClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(this.vault.market);
    	speculate.getPositionsToClose(this.vault, speculate);
    }
    
    public void backTestClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(this.vault.market);
    	speculate.runBackTest(this.vault, speculate);
    }
    
    public void newEntriesClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(this.vault.market);
    	speculate.getNewEntries(this.vault, speculate);
    }
    
    public void clearBtnClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    }
    
    public void saveBtnClicked(ActionEvent e){
    	StringBuilder sb = new StringBuilder();
    	for(int x = 0; x < resultsList.size();x++){
    		sb.append(resultsList.get(x).toString() + "\n");
    	}
    	String results = sb.toString();
    	long date = System.currentTimeMillis();
    	try {
			SaveToFile.writeToTextFile(Long.toString(date), results);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    void setMainStage(){
		//set title
		speculateStage.setTitle("Market Operator");
		
		//border pane
		speculatePane = new BorderPane();
		
		//list
		resultsList = FXCollections.observableArrayList();
		statList = new ListView<>(resultsList);
		BorderPane.setAlignment(statList,  Pos.CENTER_LEFT);
		speculatePane.setCenter(statList);
		
		//header top title
		speculateTitle = new Text("Market Operator");
		speculateTitle.setFill(Color.GREEN);
		speculateTitle.setFont(Font.font(null, FontWeight.NORMAL, 24));
		BorderPane.setAlignment(speculateTitle,  Pos.CENTER);
		speculatePane.setTop(speculateTitle);
		
		//button HBOX
		speculateMainHbox = new HBox();
		speculateMainHbox.setPadding(new Insets(15,12,15,12));
		speculateMainHbox.setSpacing(10);
		
		//main buttons
		viewOpen = new Button("View Open");
		viewOpen.setPrefSize(200, 50);
		viewOpen.setVisible(false);
		viewOpen.setOnAction(e-> viewOpenClicked(e));
		
		viewClose = new Button("View Close");
		viewClose.setPrefSize(200,50);
		viewClose.setVisible(false);
		viewClose.setOnAction(e-> viewCloseClicked(e));
		
		backTest = new Button("Backtest");
		backTest.setPrefSize(200,50);
		backTest.setVisible(false);
		backTest.setOnAction(e-> backTestClicked(e));
		
		newEntries = new Button("New Entries");
		newEntries.setPrefSize(200,50);
		newEntries.setVisible(false);
		newEntries.setOnAction(e-> newEntriesClicked(e));
		
		clearBtn = new Button("Clear");
		clearBtn.setPrefSize(200, 50);
		clearBtn.setVisible(false);
		clearBtn.setOnAction(e-> clearBtnClicked(e));
		
		saveBtn = new Button("Save");
		saveBtn.setPrefSize(200, 50);
		saveBtn.setVisible(false);
		saveBtn.setOnAction(e-> saveBtnClicked(e));
		
		//add btns to Hbox
		speculateMainHbox.getChildren().addAll(viewOpen,viewClose,backTest,newEntries, clearBtn,saveBtn);
		
		//add hbox to border pane
		speculatePane.setBottom(speculateMainHbox);
		
		chooseMarketScene = new Scene(chooseMarketPane, 450, 300);
		speculateScene = new Scene(speculatePane, 1250, 750);
		
		chooseMarketStage = new Stage();
		chooseMarketStage.setScene(chooseMarketScene);
        
		//tell stage it is meant to pop-up (Modal)
		chooseMarketStage.initModality(Modality.APPLICATION_MODAL);
		chooseMarketStage.setTitle("Choose Market");
		
	}
    
	void setChooseMarketPopup(){
		//market buttons
		btnStock = new Button("Stock Market");
		btnDigital = new Button("Digital Market");
		btnPoloOffline = new Button("Poloniex Offline");
		
		//set button size
		btnStock.setPrefSize(150, 50);
		btnDigital.setPrefSize(150, 50);
		btnPoloOffline.setPrefSize(150, 50);
		
		//set button onclick
		btnStock.setOnAction(e-> ButtonClicked(e));
		btnDigital.setOnAction(e-> ButtonClicked(e));
		btnPoloOffline.setOnAction(e-> ButtonClicked(e));
		
		//popup border pane
		chooseMarketPane = new BorderPane();
		
		//popup Hbox
		chooseMarket = new HBox();
		chooseMarket.setPadding(new Insets(10,10,10,10));
		chooseMarket.setSpacing(10);
		
		//add buttons to hbox
		chooseMarket.getChildren().addAll(btnStock,btnDigital, btnPoloOffline);
		
		//set borderpane
		BorderPane.setAlignment(chooseMarket,  Pos.CENTER);
		chooseMarketPane.setCenter(chooseMarket);
	}
	
}
