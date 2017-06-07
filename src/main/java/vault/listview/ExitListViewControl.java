package vault.listview;

import java.io.IOException;

import exit.Exit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import vault.main.VaultMainControl;

public class ExitListViewControl extends Pane {
	
	@FXML private ListView<Exit> exitListView = new ListView<>();
	
	private ObservableList<Exit> exitObservableList = FXCollections.observableArrayList();

	public ExitListViewControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ExitListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        exitListView.setItems(exitObservableList);
	}
	
	public ObservableList<Exit> getMainObservableList() {
		return exitObservableList;
	}
	
	public ListView<Exit> getMainListView(){
		return exitListView;
	}
	
	@FXML public void keyListener(KeyEvent event){
		if(event.getCode()==KeyCode.ENTER){
			VaultMainControl.getVaultMainControl().returnToEntries();
			exitObservableList.removeAll(exitObservableList);
		}
	}

}
