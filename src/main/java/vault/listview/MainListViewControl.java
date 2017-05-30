package vault.listview;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class MainListViewControl extends Pane {
	
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

	public List<String> getMainObservableList() {
		return mainObservableList;
	}
	
	public ListView<String> getMainListView(){
		return mainListView;
	}


}
