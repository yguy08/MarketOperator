package com.speculation1000.specvault.mainmenu;

public enum MainMenuEnum {
	
	ACCOUNT ("Account");
	
	private String displayName;
	
	MainMenuEnum(String s){
		this.displayName = s;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	@Override
	public String toString(){
		return displayName;
	}

	
}
