package com.speculation1000.specvault.listview;

public class DisplayableString implements Displayable {

	private String toStr;
	
	public DisplayableString(String text){
		toStr = text;
	}
	
	@Override
	public String toString(){
		return toStr;
	}
}
