package com.nttdata.ta.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Test
    void testSayHello() {
        String result = userController.sayHello();
        assertThat(result).isEqualTo("Hello World");
    }
}