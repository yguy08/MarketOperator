package operator;

import java.io.IOException;

import bitcoin.PoloAsset;
import bitcoin.PoloMarket;

public class Market {
	
	String name;
	
	PoloMarket polo;
	PoloAsset poloAsset;
	
	public Market(String market, String asset){
		if(market.trim().equals("digits")){
			this.name = market;
			polo = new PoloMarket();
			try {
				poloAsset = new PoloAsset(asset, polo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error getting polo asset");
				e.printStackTrace();
			}
		}else{
			System.out.println("Not supported yet!");
		}
	}
	
	public PoloMarket getPoloMarket(){
		return polo;
	}
	
	public PoloAsset getPoloAsset(){
		return poloAsset;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setMarket(String name){
		this.name = name;
	}

}

