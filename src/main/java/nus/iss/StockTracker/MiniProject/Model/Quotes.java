package nus.iss.StockTracker.MiniProject.Model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;

import org.springframework.stereotype.Component;

@Component ("quotes")
public class Quotes {

    private static final Logger logger = LoggerFactory.getLogger(Quotes.class);

    private String symbol;
    private BigDecimal lastPrice;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal netChange;
    private BigDecimal totalVolume;
    private BigDecimal fiftyTwoWkHigh;
    private BigDecimal fiftyTwoWkLow;

    private String ticker;
    
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public BigDecimal getLastPrice() {
        return lastPrice;
    }
    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }
    public BigDecimal getOpenPrice() {
        return openPrice;
    }
    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }
    public BigDecimal getClosePrice() {
        return closePrice;
    }
    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }
    public BigDecimal getHighPrice() {
        return highPrice;
    }
    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }
    public BigDecimal getLowPrice() {
        return lowPrice;
    }
    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }
    public BigDecimal getNetChange() {
        return netChange;
    }
    public void setNetChange(BigDecimal netChange) {
        this.netChange = netChange;
    }
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }
    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public BigDecimal getFiftyTwoWkHigh() {
        return fiftyTwoWkHigh;
    }
    public void setFiftyTwoWkHigh(BigDecimal fiftyTwoWkHigh) {
        this.fiftyTwoWkHigh = fiftyTwoWkHigh;
    }
    public BigDecimal getFiftyTwoWkLow() {
        return fiftyTwoWkLow;
    }
    public void setFiftyTwoWkLow(BigDecimal fiftyTwoWkLow) {
        this.fiftyTwoWkLow = fiftyTwoWkLow;
    }

    //retrieve the 'ticker' json file before extracting one by one
    //Should use String or JsonObject
    public static Quotes createJson (String ticker, String json) throws IOException {
        logger.info(">>> Ceate Json for Quotes");
        Quotes q = new Quotes();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            logger.info(">>>>>>>" + o.get(ticker));
            
            JsonString lastSymbol = o.getJsonObject(ticker).getJsonString("symbol");
            q.symbol = lastSymbol.toString().replace("\"", "" );
            JsonNumber lastPriceNum = o.getJsonObject(ticker).getJsonNumber("lastPrice");
            q.lastPrice = lastPriceNum.bigDecimalValue();
            JsonNumber openPriceNum = o.getJsonObject(ticker).getJsonNumber("openPrice");
            q.openPrice = openPriceNum.bigDecimalValue();
            JsonNumber highPriceNum = o.getJsonObject(ticker).getJsonNumber("highPrice");
            q.highPrice = highPriceNum.bigDecimalValue();
            JsonNumber lowPriceNum = o.getJsonObject(ticker).getJsonNumber("lowPrice");
            q.lowPrice = lowPriceNum.bigDecimalValue();
            JsonNumber closePriceNum = o.getJsonObject(ticker).getJsonNumber("closePrice");
            q.closePrice = closePriceNum.bigDecimalValue();
            JsonNumber totalVolumeNum = o.getJsonObject(ticker).getJsonNumber("totalVolume");
            q.totalVolume = totalVolumeNum.bigDecimalValue();
            JsonNumber netChangeNum = o.getJsonObject(ticker).getJsonNumber("netChange");
            q.netChange = netChangeNum.bigDecimalValue();
            JsonNumber fiftyTwoWkHighNum = o.getJsonObject(ticker).getJsonNumber("52WkHigh");
            q.fiftyTwoWkHigh = fiftyTwoWkHighNum.bigDecimalValue();
            JsonNumber fiftyTwoWkLowNum = o.getJsonObject(ticker).getJsonNumber("52WkLow");
            q.fiftyTwoWkLow = fiftyTwoWkLowNum.bigDecimalValue();   
            
        }

        return q;





    }
    

}
