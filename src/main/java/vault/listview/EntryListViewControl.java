package vault.listview;

import java.io.IOException;

import entry.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class EntryListViewControl extends Pane {
	

	@FXML private ListView<Entry> entryListView = new ListView<>();
	
	private ObservableList<Entry> entryObservableList = FXCollections.observableArrayList();

	public EntryListViewControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EntryListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
            entryListView.setItems(entryObservableList);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	public ObservableList<Entry> getMainObservableList() {
		return entryObservableList;
	}
	
	public ListView<Entry> getMainListView(){
		return entryListView;
	}

}
