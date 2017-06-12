package vault.listview;

import java.util.List;

import javafx.scene.input.KeyEvent;

public interface ControlledList {
	
	void clearList();
	
	void setList(List<?> list);
	
	void onKeyEnter(KeyEvent e);
}
