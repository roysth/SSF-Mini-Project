package nus.iss.StockTracker.MiniProject.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import nus.iss.StockTracker.MiniProject.Model.Quotes;
import nus.iss.StockTracker.MiniProject.Model.MarketMovers;


@Service
public class StockTrackerAPISvc {

    private static final Logger logger = LoggerFactory.getLogger(StockTrackerAPISvc.class);

    //private String apiKey = System.getenv("API_KEY");

    @Value("${open.stock.tracker}")
    private String apiKey;

    private static String qURL = "https://api.tdameritrade.com/v1/marketdata/quotes";

    private static String mmURL = "https://api.tdameritrade.com/v1/marketdata/$SPX.X/movers";

    //For Quotes
    //Use Optional cus need user input. Prevent error if user do not input.
    //use gettForEntity since its a Get method + no need for header
    public Optional<Quotes> getQuotes (String ticker){

        String quoteUrl = UriComponentsBuilder.fromUriString(qURL)
            .queryParam("apikey", apiKey)
            .queryParam("symbol", ticker)
            .toUriString();

        logger.info(">>> Quotes URL API address  : " + quoteUrl);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        
        try{
            resp = template.getForEntity(quoteUrl, String.class);
            logger.info(">>> TEST " + resp.getBody());
            Quotes q = Quotes.createJson(ticker, resp.getBody());
            return Optional.of(q);
        }
        catch(Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    //For MarketMovers (Using Dow Jones)
    //Dont need to use Optional cus dont need user input. Definitely will show this info  
    public String getMarketMovers (String direction){

        String marketMoverseUrl = UriComponentsBuilder.fromUriString(mmURL)
            .queryParam("apikey", apiKey)
            .queryParam("direction", direction)
            .queryParam("change", "percent")
            .toUriString();

        logger.info(">>> Market Movers URL API address  : " + marketMoverseUrl);

        RestTemplate template = new RestTemplate();
        //no header, GET method, hence can use getForEntity method
        ResponseEntity<String> resp = template.getForEntity(marketMoverseUrl, String.class);

        return resp.getBody();
    }
}
