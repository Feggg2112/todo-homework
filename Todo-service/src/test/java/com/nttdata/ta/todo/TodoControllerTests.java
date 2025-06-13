package com.nttdata.ta.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TodoControllerTests {

    @Autowired
    private TodoController todoController;

    @Test
    void testSayHello() {
        String result = todoController.sayHello();
        assertThat(result).isEqualTo("Hello World");
    }
}