/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.bitcoin;

import com.bartoszlach.bitbetgo.model.Bet;
import com.bartoszlach.bitbetgo.model.Match;
import com.bartoszlach.bitbetgo.model.OldTransactions;
import com.bartoszlach.bitbetgo.model.dao.BetDao;
import com.bartoszlach.bitbetgo.model.dao.MatchDao;
import com.bartoszlach.bitbetgo.model.dao.OldTransactionsDao;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import wf.bitcoin.javabitcoindrpcclient.BitcoinAcceptor;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Transaction;
import wf.bitcoin.javabitcoindrpcclient.ConfirmedPaymentListener;

/**
 *
 * @author bartoszlach
 */
@Component
public class RpcClient {

    private static Logger logger = LogManager.getLogger(RpcClient.class);

    @Autowired
    private OldTransactionsDao oldTransactionDao;

    @Autowired
    private BetDao betDao;

    @Autowired
    private MatchDao matchDao;

    private static List<OldTransactions> oldTxList;
    private static BitcoinJSONRPCClient client;

    public void authorize() {
        try {
            client = new BitcoinJSONRPCClient("http://master:elko@localhost:8332");
            receiveCoins();
        } catch (MalformedURLException ex) {
            logger.fatal("Error while connecting to bitcoin client");
        }
    }

    public static String getNewAddress() {
        return client.getNewAddress();
    }

    public static void sendBitcoinsToAddress(String address, double amount) {
        client.sendToAddress(address, amount);
    }

    public void receiveCoins() {
        final BitcoinAcceptor acceptor = new BitcoinAcceptor(client);
        oldTxList = new ArrayList();
        try {
            Iterable<OldTransactions> it = oldTransactionDao.findAll();
            oldTxList = Lists.newArrayList(it);
        } catch (NullPointerException e) {
            logger.info("Old transactions list empty");
        }

        acceptor.addListener(new ConfirmedPaymentListener() {

            @Override
            public void confirmed(Transaction transaction) {
                OldTransactions oldTxObject = new OldTransactions(transaction.txId());
                if (!oldTxList.contains(oldTxObject)) {
                    try {
                        Bet betObject = betDao.findBySystemAddress(transaction.address()).get(0);
                        betObject.setAmount(doubleToSatoshi(transaction.amount()) + betObject.getAmount());
                        betDao.save(betObject);
                        Match matchObject = matchDao.findOne(betObject.getMatchId());
                        matchObject.setBank(doubleToSatoshi(transaction.amount()) + matchObject.getBank());
                        matchDao.save(matchObject);
                    } catch (Exception e) {
                        logger.info("txid: " + transaction.txId() + " is out of system");
                    }
                    oldTxList.add(oldTxObject);
                    oldTransactionDao.save(oldTxObject);
                }
            }
        });
        acceptor.run();
    }

    public static long doubleToSatoshi(double amount) {
        return (long) (amount * Math.pow(10, 8));
    }

}
