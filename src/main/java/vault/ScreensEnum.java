package vault;

public enum ScreensEnum {
	
	MAIN ("main", "VaultMainFXML.fxml"),
	SETTINGS ("settings", "VaultSettingsFXML.fxml");
	
	private String screenName;
	private String fxmlPath;

	ScreensEnum(String screenName, String fxmlPath){
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
