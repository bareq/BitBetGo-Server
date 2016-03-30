/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.api;

import com.bartoszlach.bitbetgo.bitcoin.RpcClient;
import com.bartoszlach.bitbetgo.model.Match;
import com.bartoszlach.bitbetgo.model.dao.MatchDao;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author bartoszlach
 */
@Controller
public class GetMatchesList {

    @Autowired
    private MatchDao matchDao;

    @RequestMapping(value = "/api/getMatchesList/{league}", method = RequestMethod.GET)
    @ResponseBody
    public String responseIndex(@PathVariable String league) {
        List<Match> matchesList = new ArrayList();
        if (league.equals("all")) {
            matchesList = Lists.newArrayList(matchDao.selectUpcomingAll());
        } else {
            matchesList = Lists.newArrayList(matchDao.selectUpcomingByLeague(league));
        }
        
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        return g.toJson(matchesList);
    }
}
