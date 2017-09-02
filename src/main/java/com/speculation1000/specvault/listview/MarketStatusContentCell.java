package com.speculation1000.specvault.listview;

import com.speculation1000.specdb.market.MarketStatusContent;
import com.speculation1000.specvault.market.MarketData;

import javafx.scene.control.ListCell;

public class MarketStatusContentCell extends ListCell<MarketStatusContent> {
	
	@Override
	public void updateItem(MarketStatusContent msc, boolean empty){
		super.updateItem(msc, empty);
		
        if (empty || msc == null) {
            setText(null);
            setGraphic(new MarketData().getBox());
        }else {
            setText(null);
            MarketData marketData = new MarketData();
            marketData.setSymbolText(msc.getSymbol());
            marketData.setPriceText(" @" + msc.getCurrentPrice());
            marketData.setMiscText(" " + msc.getDayHighLowMap().firstEntry().getValue());
            setGraphic(marketData.getBox());
        }
    }

}
