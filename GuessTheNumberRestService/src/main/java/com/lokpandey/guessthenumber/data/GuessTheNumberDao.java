/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Interface for GuessTheNumberDao
 */
package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;

/**
 *
 * @author lokpandey
 */
public interface GuessTheNumberDao {
    Game add(Game game);
}
