package com.speculation1000.specvault.vault;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class PreloaderControl extends BorderPane implements Initializable {
	
	public PreloaderControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PreloaderView.fxml"));
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

}
