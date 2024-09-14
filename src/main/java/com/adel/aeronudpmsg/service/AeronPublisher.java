package com.adel.aeronudpmsg.service;

import io.aeron.Aeron;
import io.aeron.Publication;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Adel.Albediwy
 */
@Service
public class AeronPublisher implements CommandLineRunner {

    private final Publication publication;

    public AeronPublisher(final Aeron aeron) {
        publication = aeron.addPublication("aeron:udp?endpoint=localhost:40123", 10);
    }

    public void sendMessage(String message) {
        final UnsafeBuffer unsafeBuffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64));
        unsafeBuffer.putStringWithoutLengthUtf8(0, message);
        final long res = publication.offer(unsafeBuffer, 0, message.length());
    }

    @Override
    public void run(final String... args) throws Exception {
        final AtomicInteger count = new AtomicInteger(0);
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                sendMessage("Hello from Aeron UDP! count: "+ count.incrementAndGet());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {

                }
            }
        });
    }
}
