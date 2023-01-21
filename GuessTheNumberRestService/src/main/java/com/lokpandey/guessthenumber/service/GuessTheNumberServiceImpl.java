/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Service layer implementation
 */

package com.lokpandey.guessthenumber.service;

import com.lokpandey.guessthenumber.data.GuessTheNumberDao;
import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.models.Round;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuessTheNumberServiceImpl implements GuessTheNumberService {

    @Autowired
    private final GuessTheNumberDao dao;
    
    public GuessTheNumberServiceImpl(GuessTheNumberDao dao) {
        this.dao = dao;
    }
    
    @Override
    public int beginGame() {
        Game game = new Game();
        
        game.setAnswer(generateFourDigitNumber());
        game.setStatus("In progress");
        
        //now save this game object to database using auto increment id
        // and get that id of the saved object from database and return it
        return dao.add(game).getId();
    } 
    
    @Override
    public int beginGame(Game game) {
        
        //just save this game object on the database and get the id of the saved object
        return dao.add(game).getId();
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

    @Override
    public Round testGuess(String guess, int gameId) {
        //Get the game object with the gameId
        Game game = dao.findById(gameId);
        
        //If Game does not exist, show error message and return
        
        
        //If the game's answer and guess are same, 
        //then fill in the round object and save information in Rounds table
        
        
        //Return the round object
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
