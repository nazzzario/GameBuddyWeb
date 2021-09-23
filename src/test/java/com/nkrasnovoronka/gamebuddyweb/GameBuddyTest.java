package com.nkrasnovoronka.gamebuddyweb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles({"h2db", "debug"})
@EntityScan(basePackages = {"com.nkrasnovoronka"})
class GameBuddyTest {


    @Test
    void testContextLoads() {
        assertTrue(true);
    }
}
