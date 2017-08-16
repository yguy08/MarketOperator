package com.speculation1000.specvault.db;

public enum DbConnectionEnum {
	
	H2_MAIN("jdbc:h2:","tcp://192.168.1.197:8082/~/SpecDb/db/Speculation1000-H2","org.h2.Driver");		
	
	private String driver;
	private String url;
	private String className;
	
	DbConnectionEnum(String driver, String url, String className){
		this.driver = driver;
		this.url = url;
		this.className = className;
	}
	
	public String getConnectionString(){
		return driver + url;
	}
	
	public String getClassForName(){
		return className;
	}
	
	//h2 connection string help
	//https://stackoverflow.com/questions/35854425/java-application-with-h2-database
	
	//OPEN PORT : 8082 (ANY)
	//192.168.1.20  Any - > 8082
	
	//jdbc:h2:tcp://localhost/~/test

}
