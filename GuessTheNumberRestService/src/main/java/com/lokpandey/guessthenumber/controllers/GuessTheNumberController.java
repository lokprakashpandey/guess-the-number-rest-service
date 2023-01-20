/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 18, 2023
 * purpose: Rest Controller for GuessTheNumber API
 */

package com.lokpandey.guessthenumber.controllers;

import com.lokpandey.guessthenumber.models.Game;
import com.lokpandey.guessthenumber.service.GuessTheNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int create() {
        return service.beginGame();
    }
    
    @PostMapping(path = "/begin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public int create(@RequestBody Game game) {
        return service.beginGame(game);
    }
    
    
}
