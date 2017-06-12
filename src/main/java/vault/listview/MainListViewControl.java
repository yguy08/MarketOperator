package vault.listview;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import vault.main.VaultMainControl;

public class MainListViewControl extends Pane implements Initializable, ControlledList {
	
	@FXML private ListView<String> mainListView = new ListView<>();
	
	private ObservableList<String> mainObservableList = FXCollections.observableArrayList();
	
	public MainListViewControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

	public ObservableList<String> getMainObservableList() {
		return mainObservableList;
	}
	
	public ListView<String> getMainListView(){
		return mainListView;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        mainListView.setItems(mainObservableList);
	}

	@Override
	public void clearList() {
		mainObservableList.clear();
	}

	@Override
	public void setCenter() {
		
	}

	@Override
	public void setList(List<?> list) {
		for(Object c : list){
			mainObservableList.add((String) c);
		}
	}

	@Override
	public void onKeyEnter(KeyEvent e) {
		//need to register with list view
		VaultMainControl.getVaultMainControl().returnToEntries();
		//clearList();
	}


}
