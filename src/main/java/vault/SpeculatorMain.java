package vault;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import market.Market;
import position.Position;
import vault.VaultPreloader.MarketConsumer;

public class SpeculatorMain extends Application implements MarketConsumer {
	
	//choose then preload a market
	Market market = null;
	
    Stage stage = null;
    
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
    
    private void mayBeShow() {
        // Show the application if the application stage is ready and market != null
        if (market != null && stage != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    stage.show();
                }                
            });
        }
    }
    
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(SpeculatorMain.class, VaultPreloader.class, args);
	}
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(setSpeculateScene());
        mayBeShow();
    }
 
    public void setMarket(Market market) {
        this.market = market; 
        System.out.println(this.market.toString());
        mayBeShow();
    }
    
    private Scene setSpeculateScene(){
		
    	//set title
		
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
		//viewOpen.setOnAction(e-> viewOpenClicked(e));
		
		viewClose = new Button("View Close");
		viewClose.setPrefSize(200,50);
		viewClose.setVisible(false);
		//viewClose.setOnAction(e-> viewCloseClicked(e));
		
		backTest = new Button("Backtest");
		backTest.setPrefSize(200,50);
		backTest.setVisible(false);
		//backTest.setOnAction(e-> backTestClicked(e));
		
		//BACKTEST 2XXXXXXX
		
		newEntries = new Button("New Entries");
		newEntries.setPrefSize(200,50);
		newEntries.setVisible(false);
		//newEntries.setOnAction(e-> newEntriesClicked(e));
		
		clearBtn = new Button("Clear");
		clearBtn.setPrefSize(200, 50);
		clearBtn.setVisible(false);
		//clearBtn.setOnAction(e-> clearBtnClicked(e));
		
		saveBtn = new Button("Save");
		saveBtn.setPrefSize(200, 50);
		saveBtn.setVisible(false);
		//saveBtn.setOnAction(e-> saveBtnClicked(e));
		
		//add btns to Hbox
		speculateMainHbox.getChildren().addAll(viewOpen,viewClose,backTest,newEntries, clearBtn,saveBtn);
		
		//add hbox to border pane
		speculatePane.setBottom(speculateMainHbox);
		
		speculateScene = new Scene(speculatePane, 1250, 750);
		
		return speculateScene;
	
    }
}
