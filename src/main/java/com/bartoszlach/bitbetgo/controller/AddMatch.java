/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.controller;

import com.bartoszlach.bitbetgo.model.Match;
import com.bartoszlach.bitbetgo.model.dao.MatchDao;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author bartoszlach
 */
@Controller
public class AddMatch {

    @Autowired
    private MatchDao matchDao;

    private Logger logger = LogManager.getLogger(AddMatch.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(
            @RequestParam("match_date") String matchDate,
            @RequestParam("match_time") String time,
            @RequestParam("league") String league,
            @RequestParam("team1") String team1Name,
            @RequestParam("team2") String team2Name,
            @RequestParam("image1") MultipartFile team1Image,
            @RequestParam("image2") MultipartFile team2Image) {
        if (!team1Image.isEmpty() && !team2Image.isEmpty() && !team1Name.isEmpty() && !team2Name.isEmpty()) {
            try {
                saveFile(team1Image);
                saveFile(team2Image);
                String stringDate = time + " " + matchDate;
                SimpleDateFormat f = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date d = f.parse(stringDate);
                long milliseconds = d.getTime();
                Timestamp data = new Timestamp(milliseconds);
                Match match = new Match(data, league, team1Name, team2Name, team1Image.getOriginalFilename(), team2Image.getOriginalFilename(), 0);
                matchDao.save(match);
                return "redirect:/addMatch?success"; //success
            } catch (Exception e) {
                logger.error(e.getMessage());
                return "redirect:/addMatch?fail"; //fail
            }
        } else {
            return "redirect:/addMatch?fill"; //empty
        }
    }

    private void saveFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream
                = new BufferedOutputStream(new FileOutputStream(new File("logos/" + file.getOriginalFilename())));
        stream.write(bytes);
        stream.close();
    }

}
