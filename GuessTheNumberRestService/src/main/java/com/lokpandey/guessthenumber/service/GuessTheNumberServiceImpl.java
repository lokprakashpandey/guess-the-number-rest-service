/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Service layer implementation
 */

package com.lokpandey.guessthenumber.service;

import com.lokpandey.guessthenumber.data.GuessTheNumberDao;
import com.lokpandey.guessthenumber.models.Game;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuessTheNumberServiceImpl implements GuessTheNumberService {

    @Autowired
    private final GuessTheNumberDao guessDao;
    
    public GuessTheNumberServiceImpl(GuessTheNumberDao guessDao) {
        this.guessDao = guessDao;
    }
    
    @Override
    public int beginGame() {
        Game game = new Game();
        
        game.setAnswer(generateFourDigitNumber());
        game.setStatus("In progress");
        
        //now save this game object to database using auto increment id
        // and get that id of the saved object from database and return it
        return guessDao.add(game).getId();
    } 
    
    
    //generate a 4-digit number in String form where every digit is different
    private String generateFourDigitNumber() {
        Random randomObj = new Random();
        String randomString = "";
        for(int i=0; i<4; i++) {
            int randomNumber = randomObj.nextInt(10);
            while(randomString.contains(randomNumber+"")) {
                randomNumber = randomObj.nextInt(10);
            }
            randomString += randomNumber;
        }
        return randomString;
    }
}
