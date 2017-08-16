package com.speculation1000.specvault.listview;

import javafx.scene.control.ListCell;

public class DisplayableCell extends ListCell<Displayable>{

	@Override
	public void updateItem(Displayable displayable, boolean empty){
		super.updateItem(displayable, empty);
		StringBuilder sb = new StringBuilder();
        
		if (displayable == null || empty){

        }else{
        	sb.append(displayable.toString());
        }
        
        this.setText(sb.toString());
    }
}
