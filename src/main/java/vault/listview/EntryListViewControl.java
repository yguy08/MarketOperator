package vault.listview;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

public class EntryListViewControl extends Pane implements ControlledList {
	
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
     }
	
	public ObservableList<Entry> getMainObservableList() {
		return entryObservableList;
	}
	
	public ListView<Entry> getMainListView(){
		return entryListView;
	}
	
	@Override
	public void clearList() {
		entryListView.getItems().clear();		
	}

	@Override
	public void setList(List<?> list) {
		for(Object o : list){
			entryObservableList.add((Entry) o);
		}
		entryListView.requestFocus();
		entryListView.getSelectionModel().selectFirst();
	}

	@Override
	@FXML public void onKeyEnter(KeyEvent event) {
		if(event.getCode() == KeyCode.SPACE){
			Entry entry = entryListView.getSelectionModel().getSelectedItem();
			VaultMainControl.getVaultMainControl().entrySelected(entry);
		}		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entryListView.setItems(entryObservableList);		
	}

}
