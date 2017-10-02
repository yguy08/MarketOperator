package com.speculation1000.specvault.vault;

import com.speculation1000.specdb.market.Market;

public class MenuItem extends Market {
	
	public String toStr;
	
	public MenuItem(String s) {
		this.toStr = s;
	}
	
	@Override
	public String toString() {
		return toStr;
	}

}
