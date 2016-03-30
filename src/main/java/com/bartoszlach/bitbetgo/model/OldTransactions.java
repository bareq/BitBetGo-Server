/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author bartoszlach
 */
@Entity
public class OldTransactions {

    @Id
    private String txId;

    public OldTransactions() {
    }

    public OldTransactions(String txId) {
        this.txId = txId;
    }

    public String getTxId() {
        return txId;
    }

    @Override
    public boolean equals(Object object) {
        return (this.getTxId().equals(((OldTransactions) object).getTxId()));
    }

}
