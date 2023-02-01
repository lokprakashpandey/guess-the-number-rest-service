/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 23, 2023
 * purpose: Test class for RoundDaoImpl
 */
package com.lokpandey.guessthenumber.data;

import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author lokpandey
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundDaoImplTest {
    
    //Here we need both daos because of foreign key relationship
    @Autowired
    private RoundDao roundDao;
    
    @Autowired
    private GameDao gameDao;
    
    public RoundDaoImplTest() {
    }
    
    
    @Before
    public void setUp() {
        List<Round> rounds = roundDao.getAllRounds();
        for(Round round: rounds) roundDao.deleteRoundById(round.getId());
        List<Game> games = gameDao.getAllGames();
        for(Game game: games) gameDao.deleteById(game.getId());
    }
    
    
    //for cleanup
//    private static RoundDao staticRoundDao;
//    @Autowired
//    public void setStaticGameDao(RoundDao roundDao) {
//        RoundDaoImplTest.staticRoundDao = roundDao;
//    }
//    
//    private static GameDao staticGameDao;
//    @Autowired
//    public void setStaticGameDao(GameDao gameDao) {
//        RoundDaoImplTest.staticGameDao = gameDao;
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//        List<Round> rounds = staticRoundDao.getAllRounds();
//        for(Round round: rounds) staticRoundDao.deleteRoundById(round.getId());
//        
//        List<Game> games = staticGameDao.getAllGames();
//        for(Game game: games) staticGameDao.deleteById(game.getId());
//    }

    /**
     * Test of add and getRounds method, of class RoundDaoImpl.
     */
    @Test
    public void testAddGetRounds() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In progress");
        game = gameDao.add(game); // game object saved and retrieved with id
        
        Round round = new Round();
        round.setGame(game);
        round.setGuess("5678");
        round.setGuessTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round.setResult("e:0:p:0");
        round = roundDao.add(round);
        
        Round round2 = new Round();
        round2.setGame(game);
        round2.setGuess("1234");
        round2.setGuessTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round2.setResult("e:4:p:0");
        round2 = roundDao.add(round2);
        
        List<Round> rounds = roundDao.getRounds(game.getId());
        
//        System.out.println(rounds.get(0).getGuessTime());
//        System.out.println(rounds.get(1).getGuessTime());
//        System.out.println(round.getGuessTime());
//        System.out.println(round2.getGuessTime());
//        
        assertEquals(rounds.size(), 2);
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));        
    }

}
