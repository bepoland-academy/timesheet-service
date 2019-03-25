package pl.betse.beontime.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@LiquibaseDataSource
@EnableEurekaClient
@ComponentScan("pl.betse.beontime")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
