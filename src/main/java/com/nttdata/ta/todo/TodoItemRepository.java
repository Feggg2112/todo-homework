package com.nttdata.ta.todo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    List<TodoItem> findByUser_Id(Long userId);
}