package com.speculation1000.specvault.vault;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.speculation1000.specvault.dao.MarketSummaryDAO;
import com.speculation1000.specvault.db.DbConnectionEnum;
import com.speculation1000.specvault.listview.Displayable;
import com.speculation1000.specvault.listview.DisplayableCellFactory;
import com.speculation1000.specvault.market.Market;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

public class VaultMainControl extends BorderPane implements Initializable {
	
	@FXML private ListView<Displayable> listViewDisplay;
	
	@FXML private Button btn1;
	
	@FXML private Button btn2;
		
	private ObservableList<Displayable> mainObsList = FXCollections.observableArrayList();
    
	public VaultMainControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VaultMainView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        List<Market> marketList = MarketSummaryDAO.getLatestTicker(DbConnectionEnum.H2_MAIN);
		listViewDisplay.setCellFactory(new DisplayableCellFactory());
		mainObsList.setAll(marketList);
		listViewDisplay.setItems(mainObsList);
    	listViewDisplay.scrollTo(0);
	}
	
	@FXML
	public void newEntries(){
		mainObsList.clear();
		loadAnimationStart();
		Task<List<Market>> task = new Task<List<Market>>() {
            @Override
            protected List<Market> call() throws Exception {
	            List<Market> marketList = MarketSummaryDAO.getMarketsAtXDayHighOrLow(DbConnectionEnum.H2_MAIN,
	            		25);
	            return marketList;
            }
        };        
        new Thread(task).start();
        
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				List<Market> marketList = task.getValue();
				Platform.runLater(new Runnable() {
		            @Override
					public void run() {
		            	loadAnimationEnd();
		            	mainObsList.setAll(marketList);
			    		listViewDisplay.setItems(mainObsList);
			        	listViewDisplay.scrollTo(0);
		            }
		        });
			}
		});

	}
	
	@FXML
	public void refresh(){
		mainObsList.clear();
		loadAnimationStart();
		Task<List<Market>> task = new Task<List<Market>>() {
            @Override
            protected List<Market> call() throws Exception {
	            List<Market> marketList = MarketSummaryDAO.getLatestTicker(DbConnectionEnum.H2_MAIN);
	            return marketList;
            }
        };        
        new Thread(task).start();
        
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				List<Market> marketList = task.getValue();
				Platform.runLater(new Runnable() {
		            @Override
					public void run() {
		            	loadAnimationEnd();
		            	mainObsList.setAll(marketList);
			    		listViewDisplay.setItems(mainObsList);
			        	listViewDisplay.scrollTo(0);
		            }
		        });
			}
		});
	}
	
	private void loadAnimationStart(){
		setCenter(new ProgressIndicator());
		btn1.setVisible(false);
		btn2.setVisible(false);
	}
	
	private void loadAnimationEnd(){
		setCenter(listViewDisplay);
		btn1.setVisible(true);
		btn2.setVisible(true);
	}
	
}
