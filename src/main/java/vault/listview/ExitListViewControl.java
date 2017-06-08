package vault.listview;

import java.io.IOException;
import java.util.List;

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
        
        exitListView.setItems(exitObservableList);
	}
	
	public ObservableList<Exit> getMainObservableList() {
		return exitObservableList;
	}
	
	public ListView<Exit> getMainListView(){
		return exitListView;
	}
	
	@FXML public void keyListener(KeyEvent event){
		if(event.getCode()==KeyCode.ENTER || event.getCode()==KeyCode.SHIFT){
			VaultMainControl.getVaultMainControl().returnToEntries();
			exitObservableList.removeAll(exitObservableList);
		}
	}

	@Override
	public void clearList() {
		exitObservableList.clear();
	}

	@Override
	public void setCenter() {
		VaultMainControl.getVaultMainControl().setCenter(this);
	}

	@Override
	public void setList(List<?> list) {
		for(Object o : list){
			exitObservableList.add((Exit) o);
		}
	}

	@Override
	public void onKeyEnter(KeyEvent e) {
		System.out.println("Exit!");
	}

}
