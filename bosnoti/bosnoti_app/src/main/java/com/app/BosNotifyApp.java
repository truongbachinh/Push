package com.app;

import com.db.model.BosNotificationEntity;
import com.db.service.BosNotifyService;
import com.kafka.KafkaProducerService;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

import static java.lang.System.exit;

@SpringBootApplication
@ComponentScan({"com.app","com.kafka","com.db.model","com.db.repository","com.db.service","com.service"})
@EntityScan("com.db.model")
@EnableJpaRepositories("com.db.repository")
@ImportResource({"classpath*:SpringXMLBosNotifyContext.xml"})
public class BosNotifyApp implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(BosNotifyApp.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    Environment env;

    @Autowired
    private BosNotifyService bosNotifyService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public static void main(String[] args) {
        logger.info("Starting " + BosNotifyApp.class.getSimpleName());

        SpringApplication.run(BosNotifyApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);

        /*
        System.out.println("\n1.getBosNotification()...");
        for (BosNotificationEntity customer : bosNotifyService.getBosNotification("%","%","%","%",1,100)) {
            System.out.println(customer);

        }

        System.out.println("Done!");

        exit(0);
        */

    }

}
