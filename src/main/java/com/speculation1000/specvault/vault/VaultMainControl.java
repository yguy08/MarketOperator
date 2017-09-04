package com.speculation1000.specvault.vault;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.speculation1000.specdb.dao.AccountDAO;
import com.speculation1000.specdb.market.MarketStatusContent;
import com.speculation1000.specdb.start.SpecDbException;
import com.speculation1000.specdb.start.StatusString;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class VaultMainControl extends GridPane implements Initializable {
	
	@FXML private Text accountBalTitleTxt;
	
	@FXML private Text openPositionsTxt;
	
	@FXML private Text newEntriesTxt;

	private ObservableList<MarketStatusContent> mainObsList = FXCollections.observableArrayList();
    
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
		String balance = null;
		try {
			balance = StatusString.getBalanceStr();
		} catch (SpecDbException e) {
			e.printStackTrace();
		}
		accountBalTitleTxt.setText(balance);
		
		String openTrades = null;
		
		try {
			openTrades = StatusString.getOpenTradesStr();
		}catch(SpecDbException e) {
			e.printStackTrace();
		}
		
		openPositionsTxt.setText(openTrades);
		
		String newEntries = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(StatusString.getLongEntriesString());
		sb.append(StatusString.getShortEntriesString());
		
		newEntries = sb.toString();
		
		newEntriesTxt.setText(newEntries);
		
	}
	
	/*
	@FXML
	public void showAll(){
		mainObsList.clear();
		loadAnimationStart();
		Task<List<MarketStatusContent>> task = new Task<List<MarketStatusContent>>() {
            @Override
            protected List<MarketStatusContent> call() throws Exception {
        	    List<MarketStatusContent> marketList = MarketStatus.getMarketStatusList();
        	    Collections.sort(marketList);
	            return marketList;
            }
        };        
        new Thread(task).start();
        
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				List<MarketStatusContent> marketList = task.getValue();
        	    for(MarketStatusContent msc : marketList){
        	    	msc.setToStr(msc.getSymbol() + " @" + msc.getCurrentPrice());
        	    }
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
	}*/
	
	private void loadAnimationStart(){
		//setCenter(new ProgressIndicator());
	}
	
	private void loadAnimationEnd(){
		
	}
	
}
