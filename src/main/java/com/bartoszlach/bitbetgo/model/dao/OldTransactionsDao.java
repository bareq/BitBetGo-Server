/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.model.dao;

import com.bartoszlach.bitbetgo.model.OldTransactions;
import java.io.Serializable;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author bartoszlach
 */
@Transactional
public interface OldTransactionsDao extends CrudRepository<OldTransactions, String> {

}
