package com.francesca;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**

 *
 * @author francesca
 *
 * 2025-05-15
 *
 */

@Slf4j
@EnableScheduling
@SpringBootApplication
public class SecpIotConnApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF)
                .sources(SecpIotConnApplication.class)
                .run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, Environment env) {
        return args -> {
            log.info("swagger本地测试:{}", "http://127.0.0.1:" + env.getProperty("server.port") + "/doc.html");
            log.info("active :{}", env.getProperty("spring.profiles.active") );

        };
    }

}
