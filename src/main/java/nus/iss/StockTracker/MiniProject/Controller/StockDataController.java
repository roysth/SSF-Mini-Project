package nus.iss.StockTracker.MiniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nus.iss.StockTracker.MiniProject.Model.Quotes;
import nus.iss.StockTracker.MiniProject.Model.User;
import nus.iss.StockTracker.MiniProject.Model.MarketMovers;
import nus.iss.StockTracker.MiniProject.Service.StockTrackerAPISvc;
import nus.iss.StockTracker.MiniProject.Service.StockTrackerRedis;

@Controller
public class StockDataController {

    @Autowired
    StockTrackerAPISvc apiSvc;

    @Autowired
    StockTrackerRedis redisSvc;

    private static final Logger logger = LoggerFactory.getLogger(StockDataController.class);

    @RequestMapping ("/")
    public String loginPage (Model model){
        //TODO BE USED LATER TO SHOW THE USER NAME
        //model.addAttribute ("user", userC);
        return "index";
    }

    //create the username
    @PostMapping ("/login")
    public String login (@RequestParam (required=true) String username, Model model){

        Optional<User> optionalUser = redisSvc.findByUsername(username);
        User userC = new User();

        if (optionalUser.isEmpty()) {
            userC.setUsername(username);
            redisSvc.save(userC);
            logger.info(">>>>NEW USER CREATED" + username);
        }
        else {
            userC = optionalUser.get();
        }
        model.addAttribute("user", userC);
        model.addAttribute("username", username); 
        return "mainpage";
    }
    //href always a Get
    @GetMapping ("/watchlist/{username}")
    public String watchlist (@PathVariable(name="username", required=true) String username, Model model) {
        User user = redisSvc.findByUsername(username).get();
        model.addAttribute("watchlist", user.getWatchlist());
        model.addAttribute("username", username); 
        return "watchlist"; 
    }

    /* TO CREATE LINK TO PAGES*/

    //Add the quotes to watchlist using the "add" button 
    //change the path
    @PostMapping ("/add/{username}")
    public String add (@PathVariable(name="username", required=true) String username, @ModelAttribute Quotes quotes, Model model) {
        logger.info(">>> TEST" + quotes.getSymbol());
        logger.info("TESTTESTTESTTEST");
        //Watchlist watchlist = new Watchlist();
        logger.info(">>>TEST222222222222222222");
        User user = redisSvc.findByUsername(username).get();
        logger.info(">>>TEST33333333333333333333333");
        /*if (user.getWatchlist() != null) {
            watchlist = user.getWatchlist();  
        }
                watchlist.addQuotes(quotes);
        user.setWatchlist(watchlist);
        */
        user.getWatchlist().addQuotes(quotes);
        redisSvc.save(user);
        model.addAttribute("watchlist", user.getWatchlist());
        model.addAttribute("username", username); 
        return "watchlist"; 
    }
    //change the path
    @GetMapping ("/delete/{username}/{ticker}")
    public String delete(@PathVariable(name="username", required=true) String username, @PathVariable String ticker, Model model) {
        User user = redisSvc.findByUsername(username).get();
        user.getWatchlist().deleteQuotes(ticker);
        redisSvc.save(user);
        model.addAttribute("watchlist", user.getWatchlist());
        model.addAttribute("username", username); 
        return "watchlist";
    }

    //link to enter ticker page
    //TODO - AUTOWIRE QUOTES
    @GetMapping ("/enterticker/{username}")
    public String getquotesdata (@PathVariable(name="username", required=true) String username, Model model) {
        Quotes q = new Quotes();
        model.addAttribute ("quotes", q);
        model.addAttribute("username", username); 
        return "enterticker";
    }   

    /* TO GET DATA FROM API */

    @GetMapping ("/quotes/{username}")
    public String quotes (@PathVariable(name="username", required=true) String username, @RequestParam String ticker, Model model) {
        Optional <Quotes> w = apiSvc.getQuotes(ticker);
        if (w.isEmpty()) {
            return "quotesdata";
        }
        logger.info(">>> Test totalVolume = " + w.get().getTotalVolume());
        model.addAttribute ("quotes", w.get());
        model.addAttribute("username", username);
        return "quotesdata";
    }


    //for market movers
    @GetMapping ("/marketmovers/{username}")
    public String marketMovers (@PathVariable(name="username", required=true) String username, Model model) {

        String marketUp = apiSvc.getMarketMovers("up");
        String marketDown = apiSvc.getMarketMovers("down");
        
        ObjectMapper map = new ObjectMapper(); 

        try {
            List<MarketMovers> listUp = map.readValue(marketUp, new TypeReference<List<MarketMovers>>(){});
            model.addAttribute("UpMovers", listUp);
            logger.info(">>> Get market movers (up) : " + listUp.get(0).getDescription());

            List<MarketMovers> listDown = map.readValue(marketDown, new TypeReference<List<MarketMovers>>(){});
            model.addAttribute("DownMovers", listDown);
            logger.info(">>> Get market movers (down) : " + listDown.get(1).getDescription());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        model.addAttribute("username", username);
        return "marketmovers";
    }
}
