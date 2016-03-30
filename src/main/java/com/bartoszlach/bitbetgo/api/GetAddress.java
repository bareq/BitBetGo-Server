/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.api;

import com.bartoszlach.bitbetgo.bitcoin.RpcClient;
import com.bartoszlach.bitbetgo.model.Bet;
import com.bartoszlach.bitbetgo.model.dao.BetDao;
import com.bartoszlach.bitbetgo.model.dao.MatchDao;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author bartoszlach
 */
@Controller
public class GetAddress {

    @Autowired
    private MatchDao matchDao;
    @Autowired
    private BetDao betDao;

    @RequestMapping(value = "/api/getAddress", method = RequestMethod.POST)
    @ResponseBody
    public String responseIndex(
            @RequestParam(name = "match_id") long matchId,
            @RequestParam(name = "bet") int bet,
            @RequestParam(name = "player_address") String playerAddress) {
        if (matchDao.findOne(matchId) != null) {
            String systemAddress = RpcClient.getNewAddress();
            Bet betObject = new Bet(playerAddress, systemAddress, matchId, bet, 0);
            betDao.save(betObject);
            return systemAddress;
        }
        return "Error - no match found";
    }

}
