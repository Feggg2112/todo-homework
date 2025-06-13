package com.nttdata.ta.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {

    @Test
    void testGettersAndSetters() {
        TodoItem item = new TodoItem();
        item.setId(1L);
        item.setName("Test Description");

        assertEquals(1L, item.getId());
        assertEquals("Test Description", item.getName());
    }
}