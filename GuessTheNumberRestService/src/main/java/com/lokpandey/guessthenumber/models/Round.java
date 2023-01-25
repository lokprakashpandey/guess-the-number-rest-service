/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 11, 2023
 * purpose: Model for Rounds table
 */

package com.lokpandey.guessthenumber.models;

import java.sql.Timestamp;
import java.util.Objects;


public class Round {

    private int id;
    private Game game;
    private String guess;
    private Timestamp guessTime;
    private String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public Timestamp getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(Timestamp guessTime) {
        this.guessTime = guessTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.game.getId());
        hash = 37 * hash + Objects.hashCode(this.guess);
        hash = 37 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        return Objects.equals(this.game.getId(), other.game.getId());
    }

    
    
}
