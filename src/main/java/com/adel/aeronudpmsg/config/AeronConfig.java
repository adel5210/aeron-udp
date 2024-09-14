package com.adel.aeronudpmsg.config;

import io.aeron.Aeron;
import io.aeron.driver.MediaDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Adel.Albediwy
 */
@Configuration
public class AeronConfig {

    @Bean
    public MediaDriver mediaDriver() {
        return MediaDriver.launchEmbedded();
    }

    @Bean
    public Aeron aeron(final MediaDriver mediaDriver) {
        return Aeron.connect(new Aeron.Context().aeronDirectoryName(mediaDriver.aeronDirectoryName()));
    }

}
