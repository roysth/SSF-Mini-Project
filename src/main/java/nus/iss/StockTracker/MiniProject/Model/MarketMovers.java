package nus.iss.StockTracker.MiniProject.Model;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

//Will be using the ObjectMapper to convert Json to Java object
public class MarketMovers {

    private static final Logger logger = LoggerFactory.getLogger(MarketMovers.class);

    private BigDecimal change;
    private BigDecimal totalVolume;
    private BigDecimal last;
    private String description;
    private String direction;
    private String symbol;
    

    public BigDecimal getChange() {
        return change;
    }
    public void setChange(BigDecimal change) {
        this.change = change;
    }
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }
    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }
    public BigDecimal getLast() {
        return last;
    }
    public void setLast(BigDecimal last) {
        this.last = last;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    
   
    
    
}
