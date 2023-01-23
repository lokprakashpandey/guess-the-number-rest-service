/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Interface for service layer
 */
package com.lokpandey.guessthenumber.service;

import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author lokpandey
 */
public interface GuessTheNumberService {
    
    int beginGame();
    int beginGame(Game game);
    Round testGuess(String guess, int gameId);
    List<Game> getAllGames();
    
}
