/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 20, 2023
 * purpose: Implementation of RoundDao
 */

package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RoundDaoImpl implements RoundDao {
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public RoundDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Round add(Round round) {
        
        final String sql = "INSERT INTO Rounds (gameId, guess, guessTime, result) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGame().getId());
            statement.setString(2, round.getGuess());
            statement.setTimestamp(3, round.getGuessTime());
            statement.setString(4, round.getResult());
            return statement;

        }, keyHolder);

        //get the id of the newly inserted game object and set it to our object
        round.setId(keyHolder.getKey().intValue());

        return round;
        
    }
}
