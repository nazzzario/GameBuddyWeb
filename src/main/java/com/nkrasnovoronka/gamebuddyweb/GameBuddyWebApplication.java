package com.nkrasnovoronka.gamebuddyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class GameBuddyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameBuddyWebApplication.class, args);
    }

}
