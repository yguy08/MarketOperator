package vault;

public enum ScreenEnum {
	
	MAIN ("main", "VaultMainFXML.fxml"),
	SETTINGS ("settings", "VaultSettingsFXML.fxml");
	
	private String screenName;
	private String fxmlPath;

	ScreenEnum(String screenName, String fxmlPath){
		this.screenName = screenName;
		this.fxmlPath = fxmlPath;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getFxmlPath() {
		return fxmlPath;
	}
	
}
