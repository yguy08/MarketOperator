package vault;

import java.io.IOException;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import market.Market;
import market.MarketFactory;
import position.Position;
import speculator.Speculator;
import speculator.SpeculatorFactory;
import util.SaveToFile;
import vault.VaultPreloader.MarketConsumer;

public class SpeculatorMain extends Application implements MarketConsumer {
	
	//choose then preload a market
	Market market = null;
	
    Stage stage = null;
    
	ObservableList<String> resultsList = FXCollections.observableArrayList();
	ListView<String> statList;
	
	ObservableList<Position> positionObservableList = FXCollections.observableArrayList();
	ListView<Position> positionListView;
	
	BorderPane chooseMarketPane, speculatorMainRoot;
	Text speculateTitle;
	HBox chooseMarket, speculateMainHbox;
	Scene chooseMarketScene, speculateScene;
	Button btnStock, btnDigital, viewOpen, viewClose, backTest, newEntries, clearBtn, saveBtn, btnDigitalOffline;
	Stage chooseMarketStage, speculateStage;
	
	private Button setBalanceBtn = new Button("Balance 2x");
	
	private Button setEntryBtn = new Button("Entry+");
	
	private Button setRiskBtn = new Button("Risk+");
	
	private Button setTimeframeBtn = new Button("Time+"); 
    
    private void mayBeShow() {
        // Show the application if the application stage is ready and market != null
        if (market != null && stage != null) {
            Platform.runLater(new Runnable() {
                @Override
				public void run() {
                    stage.show();
                }                
            });
        }
    }
    
	@SuppressWarnings("restriction")
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(SpeculatorMain.class, VaultPreloader.class, args);
	}
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(setSpeculateScene());
        stage.setTitle("Speculator");
        mayBeShow();
    }
 
    @Override
	public void setMarket(String marketName) {
    	MarketFactory mFactory = new MarketFactory();
        market = mFactory.createMarket(marketName);
        System.out.println(this.market.toString());
        mayBeShow();
    }
    
    private Scene setSpeculateScene(){
		
    	//set title
		
		//border pane
		speculatorMainRoot = new BorderPane();
		
		//list
		resultsList = FXCollections.observableArrayList();
		statList = new ListView<>(resultsList);
		BorderPane.setAlignment(statList,  Pos.CENTER_LEFT);
		speculatorMainRoot.setCenter(statList);
		
		//header top title
		speculateTitle = new Text("Market Operator");
		speculateTitle.setFill(Color.GREEN);
		speculateTitle.setFont(Font.font(null, FontWeight.NORMAL, 24));
		BorderPane.setAlignment(speculateTitle,  Pos.CENTER);
		speculatorMainRoot.setTop(speculateTitle);
		
		//button HBOX
		speculateMainHbox = new HBox();
		speculateMainHbox.setPadding(new Insets(15,12,15,12));
		speculateMainHbox.setSpacing(10);
		
		//main buttons
		viewOpen = new Button("View Open");
		viewOpen.setPrefSize(200, 50);
		viewOpen.setVisible(true);
		viewOpen.setOnAction(e-> viewOpenClicked(e));
		
		viewClose = new Button("View Close");
		viewClose.setPrefSize(200,50);
		viewClose.setVisible(true);
		viewClose.setOnAction(e-> viewCloseClicked(e));
		
		backTest = new Button("Backtest");
		backTest.setPrefSize(200,50);
		backTest.setVisible(true);
		backTest.setOnAction(e-> backTestClicked(e));
		
		//BACKTEST 2XXXXXXX
		
		newEntries = new Button("New Entries");
		newEntries.setPrefSize(200,50);
		newEntries.setVisible(true);
		newEntries.setOnAction(e-> newEntriesClicked(e));
		
		clearBtn = new Button("Clear");
		clearBtn.setPrefSize(200, 50);
		clearBtn.setVisible(true);
		clearBtn.setOnAction(e-> clearBtnClicked(e));
		
		saveBtn = new Button("Save");
		saveBtn.setPrefSize(200, 50);
		saveBtn.setVisible(true);
		saveBtn.setOnAction(e-> saveBtnClicked(e));
		
		//add btns to Hbox
		speculateMainHbox.getChildren().addAll(viewOpen,viewClose,backTest,newEntries,clearBtn,saveBtn);
		
		//add hbox to border pane
		speculatorMainRoot.setBottom(speculateMainHbox);
		
		//right form control
		VBox speculatorMainForm = new VBox();
		
		setBalanceBtn.setPrefSize(200,50);
		setBalanceBtn.setVisible(true);
		
		setEntryBtn.setPrefSize(200,50);
		setEntryBtn.setVisible(true);
		
		setRiskBtn.setPrefSize(200,50);
		setRiskBtn.setVisible(true);
		
		setTimeframeBtn.setPrefSize(200,50);
		setTimeframeBtn.setVisible(true);
		
		speculatorMainForm.getChildren().addAll(setBalanceBtn,setEntryBtn,setRiskBtn,setTimeframeBtn);
		
		speculatorMainRoot.setRight(speculatorMainForm);
		
		speculateScene = new Scene(speculatorMainRoot, 1250, 750);
		
		return speculateScene;
	
    }
    
    public void viewOpenClicked(ActionEvent e){
    	resultsList.removeAll(resultsList);
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(market);
		
		
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
    
    private void viewCloseClicked(ActionEvent e){
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(market);
		
    }
    
    private void backTestClicked(ActionEvent e){
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(market);
		
    }
    
    private void newEntriesClicked(ActionEvent e){
    	SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		Speculator speculator = speculatorFactory.startSpeculating(market);
    	//speculator.getNewEntries(market, speculator);
    }
    
    private void clearBtnClicked(ActionEvent e){
    	resultsList.removeAll(resultsList);
    }
    
    private void saveBtnClicked(ActionEvent e){
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
