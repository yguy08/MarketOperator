package vault.listview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class MainListViewControl extends Pane implements Initializable {
	
	@FXML private ListView<String> mainListView = new ListView<>();
	
	private ObservableList<String> mainObservableList = FXCollections.observableArrayList();
	
	public MainListViewControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
            mainListView.setItems(mainObservableList);
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

	}


}
