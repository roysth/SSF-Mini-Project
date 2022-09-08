package nus.iss.StockTracker.MiniProject.Model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component ("user")
public class User implements Serializable {

    private String username;
    private Watchlist watchlist;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }
    
    
}
