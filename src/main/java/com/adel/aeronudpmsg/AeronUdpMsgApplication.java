package com.adel.aeronudpmsg;

import io.aeron.Aeron;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AeronUdpMsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(AeronUdpMsgApplication.class, args);
    }

}
