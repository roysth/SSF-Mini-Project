package nus.iss.StockTracker.MiniProject.Model;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component ("watchlist")
public class Watchlist {

    // @Autowired
    // StockTrackerAPISvc apiSvc;

    //USE LINKEDLIST
    private List<Quotes> listOfQuotes = new LinkedList<>();

    public List<Quotes> getListOfQuotes() {
        return listOfQuotes;
    }

    public void setListOfQuotes(List<Quotes> listOfQuotes) {
        this.listOfQuotes = listOfQuotes;
    }

    public void addQuotes (Quotes quotes) {
        //prevent repetitive addd
        listOfQuotes.add(quotes);
    }

    public void deleteQuotes (String quotes) {
        for (int i = 0; i < listOfQuotes.size(); i++) {
            //Quotes is an object
            //listOfQuotes is a list of objects
            //this is the object Quotes q = listOfQuotes.get(i), then getSymbol() to make it into a string
            //compare string to string
            if (listOfQuotes.get(i).getSymbol().equals(quotes)) {
                listOfQuotes.remove(i);
            }
        }
        /* 
        for (Quotes q :listOfQuotes) {

            if (q.getSymbol()==quotes) {
                listOfQuotes.remove()
                remove only can do index. so must find another way of writing
            }
        }
        */ 
    }
}

/*
 * For this case, dont need to use Map. Unless you have numerous watchlist, then can use Map<String, Quotes>.
 * We only have 1 watchlist per user.
 */
//Idea is to store the the data taken from the api temporary as qutoesC