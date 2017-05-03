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
import position.Position;
import speculator.Speculator;
import speculator.SpeculatorFactory;
import util.SaveToFile;

public class Main extends Application  {
	
	Vault vault;
	
	ObservableList<String> resultsList = FXCollections.observableArrayList();
	ListView<String> statList;
	
	ObservableList<Position> positionObservableList = FXCollections.observableArrayList();
	ListView<Position> positionListView;
	
	BorderPane chooseMarketPane, speculatePane;
	Text speculateTitle;
	HBox chooseMarket, speculateMainHbox;
	Scene chooseMarketScene, speculateScene;
	Button btnStock, btnDigital, viewOpen, viewClose, backTest, newEntries, clearBtn, saveBtn, btnDigitalOffline;
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
        	marketName = Market.STOCK_MARKET;
            chooseMarketStage.close();
        }else if (e.getSource() == btnDigital){
        	marketName = Market.DIGITAL_MARKET;
            chooseMarketStage.close();
        }else{
        	marketName = Market.DIGITAL_OFFLINE;
            chooseMarketStage.close();
        }
        
		//load data in background (for digital currencies)
		Runnable r = new Runnable() {
	         @Override
			public void run() {
	     		vault = new Vault(marketName, resultsList);
	     		viewOpen.setVisible(true);
	     		viewClose.setVisible(true);
	     		backTest.setVisible(true);
	     		newEntries.setVisible(true);
	     		clearBtn.setVisible(true);
	     		saveBtn.setVisible(true);	         }
	     };
	     
	     new Thread(r).start();
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
		
		//BACKTEST 2XXXXXXX
		
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
		
		speculateScene = new Scene(speculatePane, 1250, 750);
	
    }
    
	void setChooseMarketPopup(){
		//market buttons
		btnDigital = new Button("Digital Market");
		btnDigitalOffline = new Button("Poloniex Offline");
		btnStock = new Button("Stock Market");
		
		//set button size
		btnDigital.setPrefSize(150, 50);
		btnDigitalOffline.setPrefSize(150, 50);
		btnStock.setPrefSize(150, 50);
		
		//set button onclick
		btnDigital.setOnAction(e-> ButtonClicked(e));
		btnDigitalOffline.setOnAction(e-> ButtonClicked(e));
		btnStock.setOnAction(e-> ButtonClicked(e));
		
		//popup border pane
		chooseMarketPane = new BorderPane();
		
		//popup Hbox
		chooseMarket = new HBox();
		chooseMarket.setPadding(new Insets(10,10,10,10));
		chooseMarket.setSpacing(10);
		
		//add buttons to hbox
		chooseMarket.getChildren().addAll(btnDigital, btnDigitalOffline, btnStock);
		
		//set borderpane
		BorderPane.setAlignment(chooseMarket,  Pos.CENTER);
		chooseMarketPane.setCenter(chooseMarket);
		
		chooseMarketScene = new Scene(chooseMarketPane, 450, 300);
		chooseMarketStage = new Stage();
		
		chooseMarketStage.setScene(chooseMarketScene);
        
		//tell stage it is meant to pop-up (Modal)
		chooseMarketStage.initModality(Modality.APPLICATION_MODAL);
		chooseMarketStage.setTitle("Choose Market");

	}
	
    public void viewOpenClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(this.vault.market);
    	speculator.getAllOpenPositions(this.vault, speculator);
	    
	//NEW position list
    	//statList.setItems(this.vault.resultsList);
    	//statList.setCellFactory((ListView<Position> l) -> new ColorCell());
    }
	
	/*  static class ColorCell extends ListCell<Position> {
        @Override
        public void updateItem(Position item, boolean empty) {
            super.updateItem(item, empty);
            String text = (item != null) ? "Not null" : "null";
            if (item != null) {
            	setText(item.getAssetName());
            } else {
                setText(text);
            }
        }
    } */
    
    public void viewCloseClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(this.vault.market);
    	speculator.getPositionsToClose(this.vault, speculator);
    }
    
    public void backTestClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(this.vault.market);
    	speculator.runBackTest(this.vault, this.vault.speculator);
    }
    
    public void newEntriesClicked(ActionEvent e){
    	this.vault.resultsList.removeAll(this.vault.resultsList);
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(this.vault.market);
    	speculator.getNewEntries(this.vault, speculator);
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
	
}
