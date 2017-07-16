package speculator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.poloniex.PoloniexExchange;

import vault.Config;

public class SpeculatorJunit {	
	
	@Test
	public void testAccount() throws NotAvailableFromExchangeException, NotYetImplementedForExchangeException, ExchangeException, IOException{
		ExchangeSpecification exchangeSpecification = new ExchangeSpecification(PoloniexExchange.class.getName());
		URL resourceUrl = Config.class.getResource("account.csv");
		List<String> apiStuff = new ArrayList<>();
		try {
			apiStuff = Files.readAllLines(Paths.get(resourceUrl.toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String apiKey = null;
		String secret = null;
		for(String s : apiStuff){
			if(!s.startsWith("//")){
				String[] authArr = s.split(",");
				apiKey = authArr[1];
				secret = authArr[2];
			}
		}
		if(apiKey != null){
			exchangeSpecification.setApiKey(apiKey);
		}
		
		if(secret != null){
			exchangeSpecification.setSecretKey(secret);
		}
		
		Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
		assertEquals(exchangeSpecification.getApiKey(),exchange.getExchangeSpecification().getApiKey());
	}

}
