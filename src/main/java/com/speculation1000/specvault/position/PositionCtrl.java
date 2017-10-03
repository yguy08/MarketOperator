package com.speculation1000.specvault.position;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.speculation1000.specvault.mainmenu.MainMenuCtrl;
import com.speculation1000.specvault.vault.Displayable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class PositionCtrl extends BorderPane implements Initializable, Displayable {
	
	@FXML private ListView<String> listView;
	
	private ObservableList<String> mainObsList = FXCollections.observableArrayList();
	
	@FXML private Button navA;
	
	@FXML private Button navB;

	public PositionCtrl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PositionView.fxml"));
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
		
	}

	@Override
	@FXML public void navUp() {
		int i = listView.getSelectionModel().getSelectedIndex();
		if(i>0) {
			listView.getSelectionModel().selectPrevious();
			listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
		}
	}

	@Override
	@FXML public void navDown() {
		int i = listView.getSelectionModel().getSelectedIndex();
		if(i<listView.getItems().size()-1) {
			listView.getSelectionModel().selectNext();
			listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
		}
	}

	@Override
	@FXML public void navA() {
		
	}

	@Override
	@FXML public void navB() {
		navB.getScene().setRoot(new MainMenuCtrl().getContent());
	}

	@Override
	@FXML public void navLeft() {
				
	}

	@Override
	@FXML public void navRight() {
				
	}

	@Override
	public Parent getContent() {
		return this;
	}

}
