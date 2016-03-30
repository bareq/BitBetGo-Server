/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.model.dao;

import com.bartoszlach.bitbetgo.model.Match;
import java.sql.Timestamp;
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
public interface MatchDao extends CrudRepository<Match, Long> {

    @Query(value = "SELECT * FROM match WHERE league = :league AND match_date > NOW()", nativeQuery = true)
    public List<Match> selectUpcomingByLeague(@Param("league") String league);

    @Query(value = "SELECT * FROM match WHERE match_date > NOW()", nativeQuery = true)
    public List<Match> selectUpcomingAll();

}
