package com.speculation1000.specvault.market;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MarketData {

    @FXML private HBox hBox;
    
    @FXML private Text symbolTxt;
    
    @FXML private Text priceTxt;

    public MarketData(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MarketStatusContentCell.fxml"));
        fxmlLoader.setController(this);
        
        try{
        	fxmlLoader.load();
        }
        catch (IOException e){
        	throw new RuntimeException(e);
        }
    }

    public void setSymbolText(String string){
    	symbolTxt.setText(string);
    }
    
    public void setPriceText(String string){
    	priceTxt.setText(string);
    }

    public HBox getBox(){
        return hBox;
    }	

}
