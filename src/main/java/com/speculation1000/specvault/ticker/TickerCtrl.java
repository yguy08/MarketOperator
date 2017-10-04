package com.speculation1000.specvault.ticker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.speculation1000.specdb.market.Market;
import com.speculation1000.specvault.vault.Displayable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class TickerCtrl extends BorderPane implements Initializable, Displayable {
	
	@FXML private ListView<Market> listView;
	
	private ObservableList<Market> mainObsList = FXCollections.observableArrayList();

	public TickerCtrl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccountBalView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	@Override
	public void navUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navA() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navB() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Parent getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

}
