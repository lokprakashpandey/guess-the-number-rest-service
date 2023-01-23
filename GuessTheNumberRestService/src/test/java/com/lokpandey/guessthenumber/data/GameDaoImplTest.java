/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 23, 2023
 * purpose: Test class for GameDaoImpl
 */
package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author lokpandey
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameDaoImplTest {
    
    @Autowired
    GameDao gameDao;
    
    public GameDaoImplTest() {
    }
    
    //clear up the Games table for each test
    @Before
    public void setUp() {
        
        List<Game> games = gameDao.getAll();
        for(Game game: games) gameDao.deleteById(game.getId());
    }
    
    /**
     * Test of add and get method, of class GameDaoImpl.
     */
    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        
        game = gameDao.add(game);
        
        Game fromDao = gameDao.get(game.getId());
        assertEquals(game, fromDao); //implement hashCode and equals for comparing objects
    }

    /**
     * Test of getAll method, of class GameDaoImpl.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        gameDao.add(game);
        
        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setStatus("In Progress");
        gameDao.add(game2);
        
        List<Game> games = gameDao.getAll();
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));        
    }

    /**
     * Test of update method, of class GameDaoImpl.
     */
    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        game = gameDao.add(game);
        
        Game fromDao = gameDao.get(game.getId());
        assertEquals(game, fromDao);
        
        game.setStatus("Finished"); // game object changed here
        gameDao.update(game);       // game in DB updated
        
        assertNotEquals(game, fromDao);
        
        fromDao = gameDao.get(game.getId()); // game object from DB accessed here
        assertEquals(game, fromDao);
        
    } 
    
    /**
     * Test of delete method, of class GameDaoImpl.
     */
    @Test
    public void testDeleteGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        game = gameDao.add(game);
        
        gameDao.deleteById(game.getId());
        
        try {
            Game fromDao = gameDao.get(game.getId());
            fail("EmptyResultDataAccessException must have occurred");
        }
        catch(EmptyResultDataAccessException e) {
        }
        
        List<Game> games = gameDao.getAll();
        assertEquals(games.size(), 0);
    }
    
}
