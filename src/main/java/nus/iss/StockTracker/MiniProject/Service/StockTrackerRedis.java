package nus.iss.StockTracker.MiniProject.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import nus.iss.StockTracker.MiniProject.Model.User;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StockTrackerRedis {

    private static final Logger logger = LoggerFactory.getLogger(StockTrackerRedis.class);

    @Autowired
    RedisTemplate<String, User> redisTemplate;

    //using optional to prevent crashing if user not in
    public Optional<User> findByUsername (final String username) {
        logger.info (">>>> Find user details: " + username);
        try {
            User userDetails = (User) redisTemplate.opsForValue().get(username);
            return Optional.of(userDetails);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void save (final User user) {
        logger.info (">>>> Save user details: " + user.getUsername());
        redisTemplate.opsForValue().set(user.getUsername(), user);
    } 

}

/*
 * For Redis service, only need findByUser and save. The delete can be done at the controller.
 * So you find the user's watchlist, display it out at the browser. Then from there, delete the quotes, then save to redis
 * Similar to adding. Add the stock on the browser then save to redis
 */