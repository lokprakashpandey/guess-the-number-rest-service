/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 20 , 2023
 * purpose: For managing Round object
 */
package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author lokpandey
 */
public interface RoundDao {
    Round add(Round round);
    Round getRound(int roundId);
    List<Round> getRounds(int gameId);
    List<Round> getAllRounds();
    boolean deleteRoundById(int roundId);
}
