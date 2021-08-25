package com.scribassu.tracto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
public class TractoApplicationTests {

    @Test
    public void contextLoads() {
    }

}
