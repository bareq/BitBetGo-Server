package com.bartoszlach.bitbetgo;

import com.bartoszlach.bitbetgo.bitcoin.RpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BitbetgoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext config = SpringApplication.run(BitbetgoApplication.class, args);
        RpcClient r = config.getBean(RpcClient.class);
        r.authorize();
    }

}
