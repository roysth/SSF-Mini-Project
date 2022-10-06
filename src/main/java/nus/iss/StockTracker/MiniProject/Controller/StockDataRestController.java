package nus.iss.StockTracker.MiniProject.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.StockTracker.MiniProject.Model.User;
import nus.iss.StockTracker.MiniProject.Service.StockTrackerRedis;

//RestController returns a JSON file, Controller returns a html file

@RestController
public class StockDataRestController {

    @Autowired
    StockTrackerRedis stockRedis;

    private static final Logger logger = LoggerFactory.getLogger(StockDataRestController.class);

    @GetMapping (path="/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restJsonResponse (@PathVariable String username){
        logger.info("RESTCONT START");
        Optional<User> response =stockRedis.restConResponse(username);
        return ResponseEntity.ok(response.get());
    }
    
}
