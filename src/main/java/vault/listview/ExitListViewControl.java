package vault.listview;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import trade.Exit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import vault.main.VaultMainControl;

public class ExitListViewControl extends Pane implements ControlledList {
	
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
	}
	
	public ObservableList<Exit> getMainObservableList() {
		return exitObservableList;
	}
	
	public ListView<Exit> getMainListView(){
		return exitListView;
	}
	
	@Override
	public void clearList() {
		exitListView.getItems().clear();		
	}

	@Override
	public void setList(List<?> list) {
		for(Object o : list){
			exitObservableList.add((Exit) o);
		}
		exitListView.requestFocus();
		exitListView.getSelectionModel().selectFirst();
	}

	@Override
	@FXML public void onKeyEnter(KeyEvent event) {
		boolean anyOpen = false;
		for(Exit exit : exitListView.getItems()){
			if(exit.isOpen()){
				anyOpen = true;
				break;
			}
		}
		if(event.getCode()==KeyCode.SPACE){
			Exit exit;
			if(anyOpen){
				VaultMainControl.getVaultMainControl().openSelected();
			}else{
				exit = exitListView.getSelectionModel().getSelectedItem();
				VaultMainControl.getVaultMainControl().exitSelected(exit);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitListView.setItems(exitObservableList);		
	}

}
