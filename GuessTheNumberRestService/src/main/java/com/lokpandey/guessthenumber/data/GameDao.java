/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Interface for GameDao
 */
package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;

/**
 *
 * @author lokpandey
 */
public interface GameDao {
    Game add(Game game);
    Game findById(int id);
    boolean update(Game game);
}