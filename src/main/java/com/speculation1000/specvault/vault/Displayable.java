package com.speculation1000.specvault.vault;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

public interface Displayable extends Initializable {

	public void navUp();
	 
	void navDown();
	 
	void navA();
	
	void navB();

	void navLeft();

	void navRight();

	Parent getContent();
	
}
