/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 23, 2023
 * purpose: Test class for GameDaoImpl
 */
package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.models.Round;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
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
    private GameDao gameDao;
    
    @Autowired 
    private RoundDao roundDao;
        
    public GameDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    //clear up the Games table for each test
    @Before
    public void setUp() {    
        
        List<Round> rounds = roundDao.getAllRounds();
        for(Round round: rounds) roundDao.deleteRoundById(round.getId());
        List<Game> games = gameDao.getAllGames();
        for(Game game: games) gameDao.deleteById(game.getId());
    }
    
    
    //*** Workaround for static AfterClass method ***
    //This is for clearing up the games table so that it contains no data
    //The Rounds table has not been modified so no need to change it
    private static GameDao staticGameDao;
    @Autowired
    public void setStaticGameDao(GameDao gameDao) {
        GameDaoImplTest.staticGameDao = gameDao;
    }
    
    @AfterClass
    public static void tearDownClass() {
        List<Game> games = staticGameDao.getAllGames();
        for(Game game: games) staticGameDao.deleteById(game.getId());
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
        
        List<Game> games = gameDao.getAllGames();
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
        
        List<Game> games = gameDao.getAllGames();
        assertEquals(games.size(), 0);
    }
    
}
