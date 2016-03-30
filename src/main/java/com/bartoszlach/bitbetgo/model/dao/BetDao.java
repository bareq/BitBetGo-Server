/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.model.dao;

import com.bartoszlach.bitbetgo.model.Bet;
import com.bartoszlach.bitbetgo.model.Match;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author bartoszlach
 */
@Transactional
public interface BetDao extends CrudRepository<Bet, Long> {

    public List<Bet> findBySystemAddress(String systemAddress);

}
