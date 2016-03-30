/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author bartoszlach
 */
@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private long id;
    
    private String playerAddress;
    private String systemAddress;
    private long matchId;
    private int side;
    private long amount;

    public Bet() {
    }

    public Bet(String playerAddress, String systemAddress, long matchId, int side, long amount) {
        this.playerAddress = playerAddress;
        this.systemAddress = systemAddress;
        this.matchId = matchId;
        this.side = side;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerAddress() {
        return playerAddress;
    }

    public void setPlayerAddress(String playerAddress) {
        this.playerAddress = playerAddress;
    }

    public String getSystemAddress() {
        return systemAddress;
    }

    public void setSystemAddress(String systemAddress) {
        this.systemAddress = systemAddress;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
   
    
    
}
