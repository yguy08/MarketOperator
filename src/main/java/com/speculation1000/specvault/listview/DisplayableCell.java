package com.speculation1000.specvault.listview;

import com.speculation1000.specvault.market.Market;
import com.speculation1000.specvault.vault.SpecString;

import javafx.scene.control.ListCell;

public class DisplayableCell extends ListCell<Displayable>{

	@Override
	public void updateItem(Displayable displayable, boolean empty){
		super.updateItem(displayable, empty);
		StringBuilder sb = new StringBuilder();
        
		if (displayable == null || empty){

        }else{
        	Market m = (Market) displayable;
        	sb.append(m.getBase()+m.getCounter()+":"+m.getExchange());
        	sb.append(" ");
        	boolean notDigital = m.getCounter().equalsIgnoreCase("BTC") ||
        			m.getCounter().equalsIgnoreCase("ETH") ||
        			m.getCounter().equalsIgnoreCase("XMR") ||
        			m.getCounter().equalsIgnoreCase("BCH");
        	if(!notDigital){
        		sb.append("@" + SpecString.prettyUSDPrice(m.getClose()));
        	}else{
        		sb.append("@" + SpecString.prettySatsPrice(m.getClose()));
        	}
        	this.setText(sb.toString());
        }
    }
}
