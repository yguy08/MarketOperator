package vault.listview;

import java.util.List;

import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

public interface ControlledList extends Initializable {
	
	void clearList();
	
	void setList(List<?> list);
	
	void onKeyEnter(KeyEvent e);
}
