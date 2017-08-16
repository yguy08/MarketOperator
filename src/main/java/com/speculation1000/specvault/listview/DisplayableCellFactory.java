package com.speculation1000.specvault.listview;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class DisplayableCellFactory implements Callback<ListView<Displayable>,ListCell<Displayable>>{

	@Override
	public ListCell<Displayable> call(ListView<Displayable> param) {
		return new DisplayableCell();
	}

}
