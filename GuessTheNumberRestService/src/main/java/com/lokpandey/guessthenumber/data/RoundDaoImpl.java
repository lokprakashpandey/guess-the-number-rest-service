/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 20, 2023
 * purpose: Implementation of RoundDao
 */

package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RoundDaoImpl implements RoundDao {
    
    private final GameDao gameDao;
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public RoundDaoImpl(JdbcTemplate jdbcTemplate, GameDao gameDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.gameDao = gameDao;
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

    @Override
    public List<Round> getRounds(int gameId) {
        final String sql = "SELECT id, gameId, guess, guessTime, result "
                            + "FROM Rounds "
                            + "WHERE gameId = ? "
                            + "ORDER BY guessTime desc;";
        return jdbcTemplate.query(sql, new RoundMapper(), gameId);
    }

    @Override
    public List<Round> getAllRounds() {
        final String sql = "SELECT id, gameId, guess, guessTime, result FROM Rounds;";
        return jdbcTemplate.query(sql, new RoundMapper());
    }

    @Override
    public boolean deleteRoundById(int roundId) {
        final String sql = "DELETE FROM Rounds WHERE id = ?;";
        return jdbcTemplate.update(sql, roundId) > 0;
    }
    
    private final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            
            //find game object to set to round
            Game game = gameDao.get(rs.getInt("gameId"));
            round.setGame(game);
            
            round.setGuess(rs.getString("guess"));
            round.setGuessTime(rs.getTimestamp("guessTime"));
            round.setResult(rs.getString("result"));
            return round;
        }
    }
}
