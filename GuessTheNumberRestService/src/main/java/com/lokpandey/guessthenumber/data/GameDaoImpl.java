/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Database implementation of GameDao interface
 */

package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;
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
public class GameDaoImpl implements GameDao {

    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public GameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game add(Game game) {
        
        final String sql = "INSERT INTO Games(answer, status) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            statement.setString(2, game.getStatus());
            return statement;

        }, keyHolder);

        //get the id of the newly inserted game object and set it to our object
        game.setId(keyHolder.getKey().intValue());

        return game;
        
    }
    
    @Override
    public Game findById(int id) {

        final String sql = "SELECT id, answer, status "
                + "FROM Games WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setAnswer(rs.getString("answer"));
            game.setStatus(rs.getString("status"));
            return game;
        }
    }
    
    @Override
    public boolean update(Game game) {

        final String sql = "UPDATE Games SET "
                + "answer = ?, "
                + "status = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                game.getAnswer(),
                game.getStatus(),
                game.getId()) > 0;
    }
    
    @Override
    public List<Game> getAll() {
        final String sql = "SELECT id, answer, status FROM Games;";
        return jdbcTemplate.query(sql, new GameMapper());
    }
    
    
}
