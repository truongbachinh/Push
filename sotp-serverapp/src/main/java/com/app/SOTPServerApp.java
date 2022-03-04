package com.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan({"com.app","com.controller","com.service"})
@EntityScan("com.db.model")
//@EnableJpaRepositories("com.db.repository")
@ImportResource({"classpath*:SpringXMLContext.xml"})
public class SOTPServerApp implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(SOTPServerApp.class);

    @Autowired
    Environment env;

    public static void main(String[] args) {
        logger.info("Starting " + SOTPServerApp.class.getSimpleName());

        SpringApplication.run(SOTPServerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


    }

}
