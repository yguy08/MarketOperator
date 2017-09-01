package com.speculation1000.specvault.listview;

import com.speculation1000.specdb.market.MarketStatusContent;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MarketStatusContentCellFactory implements Callback<ListView<MarketStatusContent>,ListCell<MarketStatusContent>> {

	@Override
	public ListCell<MarketStatusContent> call(ListView<MarketStatusContent> param) {
		return new MarketStatusContentCell();
	}

}
