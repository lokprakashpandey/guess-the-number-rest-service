/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Interface for GameDao
 */
package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;
import java.util.List;

/**
 *
 * @author lokpandey
 */
public interface GameDao {
    Game add(Game game);
    Game get(int id);
    boolean update(Game game);
    List<Game> getAllGames();
    boolean deleteById(int id);
    
}
