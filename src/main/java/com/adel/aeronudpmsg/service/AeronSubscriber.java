package com.adel.aeronudpmsg.service;

import io.aeron.Aeron;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

/**
 * @author Adel.Albediwy
 */
@Service
@Slf4j
public class AeronSubscriber implements CommandLineRunner {
    private final Subscription subscription;

    public AeronSubscriber(final Aeron aeron) {
        this.subscription = aeron.addSubscription("aeron:udp?endpoint=localhost:40123", 10);
    }

    public void receiveMessages() {
        final FragmentHandler fragmentHandler = (buffer, offset, length, header) -> {
            final String data = buffer.getStringWithoutLengthUtf8(offset, length);
            log.info("Received message: {} by sessionId: {} ", data, header.sessionId());
        };
        subscription.poll(fragmentHandler, 10);
    }

    @Override
    public void run(final String... args) throws Exception {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                receiveMessages();
            }
        });
    }
}
