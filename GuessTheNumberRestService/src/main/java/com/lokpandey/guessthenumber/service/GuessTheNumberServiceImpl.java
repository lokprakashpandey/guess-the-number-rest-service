/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Service layer implementation
 */

package com.lokpandey.guessthenumber.service;

import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lokpandey.guessthenumber.data.GameDao;
import com.lokpandey.guessthenumber.data.RoundDao;
import java.sql.Timestamp;
import java.util.List;

@Component
public class GuessTheNumberServiceImpl implements GuessTheNumberService {

    @Autowired
    private final GameDao gameDao;
    
    @Autowired
    private final RoundDao roundDao;
    
    public GuessTheNumberServiceImpl(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }
    
    @Override
    public int beginGame() {
        Game game = new Game();
        
        game.setAnswer(generateFourDigitNumber());
        game.setStatus("In progress");
        
        //now save this game object to database using auto increment id
        // and get that id of the saved object from database and return it
        return gameDao.add(game).getId();
    } 
    
    @Override
    public int beginGame(Game game) {
        
        //just save this game object on the database and get the id of the saved object
        return gameDao.add(game).getId();
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
        //If Game does not exist, shows error message with timestamp
        //The exception handler does this
        
        Game game = gameDao.get(gameId);
        
        //Fill in the round object and save information in Rounds table
        Round round = new Round();
        round.setGame(game);
        round.setGuess(guess);
        round.setGuessTime(Timestamp.valueOf(LocalDateTime.now()));
        
        //e for exact match, p for partial match
        int e = 0, p = 0;
        String answer = game.getAnswer();
        if(answer.equalsIgnoreCase(guess)) {
            //Update game's status if guess is correct
            game.setStatus("finished");
            gameDao.update(game);
            //set result
            e=4; p=0;
        }
        else {
            for(int i=0; i<4; i++) {
                for(int j=0; j<4; j++) {
                    if(answer.charAt(i) == guess.charAt(j)) {
                        if(i == j) e++; // if position of matching character is the same => exact match
                        else p++;       // if position is not same, then it is partial match
                    }
                }
            }
            //for unfinished games, hide the answer
            game.setAnswer("****");
        }
        round.setResult("e:"+e+":p:"+p);
        
        //Now, save round object to database
        roundDao.add(round);
        
        //Return the round object
        return round;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAllGames();
//        for(Game  game: games) {
//            if(game.getStatus().equalsIgnoreCase("In progress")) {
//                game.setAnswer("****");
//            }
//        }
        games.stream()
                .filter(game -> game.getStatus().equalsIgnoreCase("In progress"))
                .forEach(game -> game.setAnswer("****"));
        return games;
    }

    @Override
    public Game findGameById(int gameId) {
        Game game = gameDao.get(gameId);
        if(game.getStatus().equalsIgnoreCase("In progress")) game.setAnswer("****");
        return game;
    }

    @Override
    public List<Round> findRoundsByGameId(int gameId) {
        List<Round> rounds = roundDao.getRounds(gameId);
        return rounds;
    }
    
}
