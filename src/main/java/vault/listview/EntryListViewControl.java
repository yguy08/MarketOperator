package vault.listview;

import java.io.IOException;

import entry.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import vault.main.VaultMainControl;

public class EntryListViewControl extends Pane {
	

	@FXML private ListView<Entry> entryListView = new ListView<>();
	
	private ObservableList<Entry> entryObservableList = FXCollections.observableArrayList();

	public EntryListViewControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EntryListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        entryListView.setItems(entryObservableList);
	}
	
	public ObservableList<Entry> getMainObservableList() {
		return entryObservableList;
	}
	
	public ListView<Entry> getMainListView(){
		return entryListView;
	}
	
	@FXML public void keyListener(KeyEvent event){
		if(event.getCode()==KeyCode.ENTER){
			if(entryListView.getSelectionModel().getSelectedItem() != null){
				Entry entry = entryListView.getSelectionModel().getSelectedItem();
				System.out.println(entry);
				VaultMainControl.getVaultMainControl().viewOpenForEntry(entry);
			}
		}
	}

}
