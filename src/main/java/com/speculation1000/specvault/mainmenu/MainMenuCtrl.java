package com.speculation1000.specvault.mainmenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.speculation1000.specvault.account.AccountSummaryCtrl;
import com.speculation1000.specvault.vault.Displayable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class MainMenuCtrl extends BorderPane implements Displayable {
	
	@FXML private ListView<MainMenuEnum> listView;
	
	private ObservableList<MainMenuEnum> mainObsList = FXCollections.observableArrayList();
	
	@FXML private Button navA;
	
	public MainMenuCtrl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
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
		mainObsList.addAll(MainMenuEnum.values());
        listView.setItems(mainObsList);
        listView.getSelectionModel().select(0);
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
	public void navLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navA() {
		MainMenuEnum menuItem = listView.getSelectionModel().getSelectedItem();
		switch(menuItem){
			case ACCOUNT:
				AccountSummaryCtrl accCtrl = new AccountSummaryCtrl();
				navA.getScene().setRoot(accCtrl.getContent());
				break;
			default:
				navA.getScene().setRoot(this);
				break;
		}
		
	}

	@Override
	public void navB() {
		navA.getScene().setRoot(this);		
	}

	@Override
	public Parent getContent() {
		return this;
	}

}
