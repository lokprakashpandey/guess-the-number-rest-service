/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Rest Controller for GuessTheNumber API
 */

package com.lokpandey.guessthenumber.controllers;

import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.models.Round;
import com.lokpandey.guessthenumber.service.GuessTheNumberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bullsandcows")
public class GuessTheNumberController {

    @Autowired
    private final GuessTheNumberService service;
    
    public GuessTheNumberController(GuessTheNumberService service) {
        this.service = service;
    }
    
    //use Body-none in Postman
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int create() {
        return service.beginGame();
    }
    
    //use Body-raw-JSON in Postman
    @PostMapping(path = "/begin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public int create(@RequestBody Game game) {
        return service.beginGame(game);
    }
    
    //use params in Postman and give guess and gameId key and values
    @PostMapping("/guess")
    public Round guess(@RequestParam String guess, @RequestParam int gameId) {
        return service.testGuess(guess, gameId);
    }
    
    @GetMapping("/game")
    public List<Game> allGames() {
        return service.getAllGames();
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> findGameById(@PathVariable int gameId) {
        Game result = service.findGameById(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/rounds/{gameId}")
    public List<Round> findRoundsByGameId(@PathVariable int gameId) {
        return service.findRoundsByGameId(gameId);
    }
    
}
